package juice.commons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Splitter instances are thread-safe immutable, and are therefore safe to store as
 *  {@code static final} constants.
 * @author Ricky Fung
 */
public class Splitter {
    private final String separator;
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final int limit;

    Splitter(Builder builder) {
        this.separator = builder.separator;
        this.trimmer = builder.trimmer;
        this.omitEmptyStrings = builder.omitEmptyStrings;
        this.limit = Integer.MAX_VALUE;
    }

    public List<String> splitToList(CharSequence sequence) {
        Assertions.notNull(sequence, "sequence must not be null");

        Iterator<String> iterator = splittingIterator(sequence);
        List<String> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }

    /**
     * Splits {@code sequence} into string components and makes them available through an
     * {@link Iterator}, which may be lazily evaluated. If you want an eagerly computed {@link List},
     * use {@link #splitToList(CharSequence)}.
     *
     * @param sequence the sequence of characters to split
     * @return an iteration over the segments split from the parameter
     */
    public Iterable<String> split(final CharSequence sequence) {
        Assertions.notNull(sequence, "sequence must not be null");

        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return splittingIterator(sequence);
            }
        };
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    //========
    public static class Builder {
        String separator;
        CharMatcher trimmer;
        boolean omitEmptyStrings;

        public Builder() {
            trimmer = CharMatcher.none();
            omitEmptyStrings = false;
        }

        /**
         * Returns a splitter that uses the given fixed string as a separator. For example,
         * {@code Splitter.on(", ").split("foo, bar,baz")} returns an iterable containing
         * {@code ["foo", "bar,baz"]}.
         *
         * @param separator the literal, nonempty string to recognize as a separator
         * @return a splitter, with default settings, that recognizes that separator
         */
        public Builder on(final String separator) {
            Assertions.notNull(separator, "separator must be not null");
            this.separator = separator;
            return this;
        }

        public Builder on(final char separator) {
            this.separator = String.valueOf(separator);
            return this;
        }

        /**
         * Returns a splitter that behaves equivalently to {@code this} splitter, but automatically
         * removes leading and trailing {@linkplain CharMatcher#whitespace whitespace} from each returned
         * substring; equivalent to {@code trimResults(CharMatcher.whitespace())}. For example, {@code
         * Splitter.on(',').trimResults().split(" a, b ,c ")} returns an iterable containing
         * {@code ["a", "b", "c"]}.
         *
         * @return a splitter with the desired configuration
         */
        public Builder trimResults() {
            this.trimmer = CharMatcher.whitespace();
            return this;
        }

        /**
         * Returns a splitter that behaves equivalently to {@code this} splitter, but automatically omits
         * empty strings from the results. For example, {@code
         * Splitter.on(',').omitEmptyStrings().split(",a,,,b,c,,")} returns an iterable containing only
         * {@code ["a", "b", "c"]}.
         *
         * <p>If either {@code trimResults} option is also specified when creating a splitter, that
         * splitter always trims results first before checking for emptiness. So, for example, {@code
         * Splitter.on(':').omitEmptyStrings().trimResults().split(": : : ")} returns an empty iterable.
         *
         * <p>Note that it is ordinarily not possible for {@link #split(CharSequence)} to return an empty
         * iterable, but when using this option, it can (if the input sequence consists of nothing but
         * separators).
         *
         * @return a splitter with the desired configuration
         */
        public Builder omitEmptyStrings() {
            this.omitEmptyStrings = true;
            return this;
        }

        public Splitter build() {
            Assertions.notNull(separator, "separator must be not null");
            return new Splitter(this);
        }
    }

    //==========

    private Iterator<String> splittingIterator(CharSequence sequence) {
        return iterator(this, sequence);
    }

    public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
        return new SplittingIterator(splitter, toSplit) {
            @Override
            public int separatorStart(int start) {
                int separatorLength = separator.length();

                positions:
                for (int p = start, last = toSplit.length() - separatorLength; p <= last; p++) {
                    for (int i = 0; i < separatorLength; i++) {
                        if (toSplit.charAt(i + p) != separator.charAt(i)) {
                            continue positions;
                        }
                    }
                    return p;
                }
                return -1;
            }

            @Override
            public int separatorEnd(int separatorPosition) {
                return separatorPosition + separator.length();
            }
        };
    }

    private abstract static class SplittingIterator extends AbstractIterator<String> {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;

        /**
         * Returns the first index in {@code toSplit} at or after {@code start} that contains the
         * separator.
         */
        abstract int separatorStart(int start);

        /**
         * Returns the first index in {@code toSplit} after {@code
         * separatorPosition} that does not contain a separator. This method is only invoked after a
         * call to {@code separatorStart}.
         */
        abstract int separatorEnd(int separatorPosition);

        int offset = 0;
        int limit;

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        @Override
        protected String computeNext() {
            /*
             * The returned string will be from the end of the last match to the beginning of the next
             * one. nextStart is the start position of the returned substring, while offset is the place
             * to start looking for a separator.
             */
            int nextStart = offset;
            while (offset != -1) {
                int start = nextStart;
                int end;

                int separatorPosition = separatorStart(offset);
                if (separatorPosition == -1) {
                    end = toSplit.length();
                    offset = -1;
                } else {
                    end = separatorPosition;
                    offset = separatorEnd(separatorPosition);
                }
                if (offset == nextStart) {
                    /*
                     * This occurs when some pattern has an empty match, even if it doesn't match the empty
                     * string -- for example, if it requires lookahead or the like. The offset must be
                     * increased to look for separators beyond this point, without changing the start position
                     * of the next returned substring -- so nextStart stays the same.
                     */
                    offset++;
                    if (offset > toSplit.length()) {
                        offset = -1;
                    }
                    continue;
                }

                while (start < end && trimmer.matches(toSplit.charAt(start))) {
                    start++;
                }
                while (end > start && trimmer.matches(toSplit.charAt(end - 1))) {
                    end--;
                }

                if (omitEmptyStrings && start == end) {
                    // Don't include the (unused) separator in next split string.
                    nextStart = offset;
                    continue;
                }

                if (limit == 1) {
                    // The limit has been reached, return the rest of the string as the
                    // final item. This is tested after empty string removal so that
                    // empty strings do not count towards the limit.
                    end = toSplit.length();
                    offset = -1;
                    // Since we may have changed the end, we need to trim it again.
                    while (end > start && trimmer.matches(toSplit.charAt(end - 1))) {
                        end--;
                    }
                } else {
                    limit--;
                }

                return toSplit.subSequence(start, end).toString();
            }
            return endOfData();
        }
    }
}
