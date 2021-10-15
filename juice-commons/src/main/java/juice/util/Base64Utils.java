package juice.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Ricky Fung
 */
public class Base64Utils {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private Base64Utils() {}
    
    //==========加密
    public static String encode(String data) {
        return bytesToString(Base64.encodeBase64(data.getBytes(UTF_8)));
    }
    public static String encode(byte[] buf) {
        return bytesToString(Base64.encodeBase64(buf));
    }

    //==========解密
    public static String decode(String data) {
        return bytesToString(Base64.decodeBase64(data.getBytes(UTF_8)));
    }
    public static String decode(byte[] buf) {
        return bytesToString(Base64.decodeBase64(buf));
    }

    private static String bytesToString(byte[] buf) {
        return new String(buf, UTF_8);
    }
}
