package juice.commons;

/**
 * @author Ricky Fung
 */
public abstract class CharMatcher {

    /**
     * Matches any character.
     *
     * @since 19.0 (since 1.0 as constant {@code ANY})
     */
    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    /**
     * Matches no characters.
     *
     * @since 19.0 (since 1.0 as constant {@code NONE})
     */
    public static CharMatcher none() {
        return None.INSTANCE;
    }

    /**
     * Determines whether a character is whitespace according to the latest Unicode standard, as
     * illustrated
     * <a href="http://unicode.org/cldr/utility/list-unicodeset.jsp?a=%5Cp%7Bwhitespace%7D">here</a>.
     * This is not the same definition used by other Java APIs. (See a
     * <a href="https://goo.gl/Y6SLWx">comparison of several definitions of
     * "whitespace"</a>.)
     *
     * <p><b>Note:</b> as the Unicode definition evolves, we will modify this matcher to keep it up to
     * date.
     *
     * @since 19.0 (since 1.0 as constant {@code WHITESPACE})
     */
    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }

    //===============
    // Constructors

    /**
     * Constructor for use by subclasses. When subclassing, you may want to override
     * {@code toString()} to provide a useful description.
     */
    protected CharMatcher() {}

    // Abstract methods

    /** Determines a true or false value for the given character. */
    public abstract boolean matches(char c);


    //============

    // Static constant implementation classes

    /** Implementation of {@link #any()}. */
    private static final class Any extends CharMatcher {

        static final Any INSTANCE = new Any();

        private Any() {}

        @Override
        public boolean matches(char c) {
            return true;
        }
    }

    /** Implementation of {@link #none()}. */
    private static final class None extends CharMatcher {

        static final None INSTANCE = new None();

        private None() {}

        @Override
        public boolean matches(char c) {
            return false;
        }

    }

    /** Implementation of {@link #whitespace()}. */
    static final class Whitespace extends CharMatcher {

        static final String TABLE =
                "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000"
                        + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680"
                        + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009"
                        + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
        static final int MULTIPLIER = 1682554634;
        static final int SHIFT = Integer.numberOfLeadingZeros(TABLE.length() - 1);

        static final Whitespace INSTANCE = new Whitespace();

        Whitespace() {}

        @Override
        public boolean matches(char c) {
            return TABLE.charAt((MULTIPLIER * c) >>> SHIFT) == c;
        }
    }
}