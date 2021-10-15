package juice.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Ricky Fung
 */
public class StringUtils {

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String CHARSET_GBK = "GBK";

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * A String for a space character.
     */
    public static final String SPACE = " ";

    /**
     * The empty String {@code ""}.
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     */
    public static final String CR = "\r";

    private StringUtils() {}
    
    //=========
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }


    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css){
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneEmpty(final CharSequence... css) {
        return !isAnyEmpty(css);
    }

    public static boolean isAllEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotEmpty(cs)) {
                return false;
            }
        }
        return true;
    }

    //=========

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css){
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    public static boolean isAllBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotBlank(cs)) {
                return false;
            }
        }
        return true;
    }

    //=========
    public static byte[] getBytesUtf8(final String string) {
        return getBytes(string, UTF_8);
    }

    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    //==========
    public static String getStringUtf8(final byte[] buf) {
        return getString(buf, UTF_8);
    }

    private static String getString(final byte[] buf, final Charset charset) {
        return new String(buf, charset);
    }
}
