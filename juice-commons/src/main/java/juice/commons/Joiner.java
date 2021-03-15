package juice.commons;

import java.util.Arrays;
import java.util.Iterator;

/**
 * joiner is thread-safe, and safe to store as {@code static final} constants.
 * @author Ricky Fung
 */
public class Joiner {
    private final String separator;
    private final boolean skipNulls;

    Joiner(Builder builder) {
        this.separator = builder.separator;
        this.skipNulls = builder.skipNulls;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String join(Object[] parts) {
        return join(Arrays.asList(parts));
    }

    public final String join(Iterable<?> parts) {
        return join(parts.iterator());
    }

    /**
     * Returns a string containing the string representation of each of {@code parts}, using the
     * previously configured separator between each.
     */
    public final String join(Iterator<?> parts) {
        return appendTo(new StringBuilder(64), parts).toString();
    }

    //=========
    private final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
        if (parts.hasNext()) {
            builder.append(toString(parts.next()));
            while (parts.hasNext()) {
                Object part = parts.next();
                if (part == null && skipNulls) {
                    continue;
                }
                builder.append(separator);
                builder.append(toString(part));
            }
        }
        return builder;
    }

    private CharSequence toString(Object part) {
        Assertions.notNull(part, "not null");
        return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
    }

    //============
    public static class Builder {
        String separator;
        boolean skipNulls;

        public Builder() {
            this.skipNulls = false;
        }

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
         * Returns a joiner with the same behavior as this joiner, except automatically skipping over any
         * provided null elements.
         */
        public Builder skipNulls() {
            this.skipNulls = true;
            return this;
        }

        public Joiner build() {
            Assertions.notNull(separator, "separator must be not null");
            return new Joiner(this);
        }
    }
}
