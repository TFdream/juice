package juice.core.util.codec;

import juice.core.util.StringUtils;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Ricky Fung
 */
public abstract class Base64Utils {

    //encode
    public static String encodeString(final String data) {
        return encodeString(StringUtils.stringToByteArray(data));
    }

    public static String encodeString(final byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static byte[] encode(final String data) {
        return encode(StringUtils.stringToByteArray(data));
    }

    public static byte[] encode(final byte[] data) {
        return Base64.encodeBase64(data);
    }

    //decode
    public static String decodeString(final String data) {
        return decodeString(StringUtils.stringToByteArray(data));
    }

    public static String decodeString(final byte[] data) {
        return StringUtils.byteArrayToString(Base64.decodeBase64(data));
    }

    public static byte[] decode(final String data) {
        return decode(StringUtils.stringToByteArray(data));
    }

    public static byte[] decode(final byte[] data) {
        return Base64.decodeBase64(data);
    }

}
