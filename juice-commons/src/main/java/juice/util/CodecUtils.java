package juice.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ricky Fung
 */
public abstract class CodecUtils {

    //=========MD5
    public static byte[] md5(String data) {
        return DigestUtils.md5(StringUtils.getBytesUtf8(data));
    }
    public static byte[] md5(byte[] buf) {
        return DigestUtils.md5(buf);
    }
    public static byte[] md5(final InputStream in) throws IOException {
        return DigestUtils.md5(in);
    }

    public static String md5Hex(byte[] buf) {
        return DigestUtils.md5Hex(buf);
    }
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(StringUtils.getBytesUtf8(data));
    }

    public static String md5Hex(final InputStream in) throws IOException {
        return DigestUtils.md5Hex(in);
    }

    //=========SHA1
    public static String sha1Hex(byte[] buf) {
        return DigestUtils.sha1Hex(buf);
    }
    public static String sha1Hex(String data) {
        return DigestUtils.sha1Hex(StringUtils.getBytesUtf8(data));
    }

    public static String sha1Hex(final InputStream in) throws IOException {
        return DigestUtils.sha1Hex(in);
    }

    public static byte[] sha1(byte[] buf) {
        return DigestUtils.sha1(buf);
    }
    public static byte[] sha1(String data) {
        return DigestUtils.sha1(StringUtils.getBytesUtf8(data));
    }

    public static byte[] sha1(final InputStream in) throws IOException {
        return DigestUtils.sha1(in);
    }

    //===========SHA256
    public static byte[] sha256(final byte[] buf) {
        return DigestUtils.sha256(buf);
    }
    public static byte[] sha256(final String data) {
        return DigestUtils.sha256(StringUtils.getBytesUtf8(data));
    }

    public static byte[] sha256(final InputStream in) throws IOException {
        return DigestUtils.sha256(in);
    }

    public static String sha256Hex(final byte[] buf) {
        return DigestUtils.sha256Hex(buf);
    }
    public static String sha256Hex(final String data) {
        return DigestUtils.sha256Hex(StringUtils.getBytesUtf8(data));
    }

    public static String sha256Hex(final InputStream in) throws IOException {
        return DigestUtils.sha256Hex(in);
    }
}
