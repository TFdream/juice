package juice.core.util.codec;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ricky Fung
 */
public abstract class CodecUtils {

    /** MD5 **/
    public static byte[] md5(final String data) {
        return DigestUtils.md5(data);
    }

    public static byte[] md5(final byte[] data) {
        return DigestUtils.md5(data);
    }

    public static byte[] md5(final InputStream in) throws IOException {
        return DigestUtils.md5(in);
    }

    public static String md5Hex(final byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    public static String md5Hex(final String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String md5Hex(final InputStream in) throws IOException {
        return DigestUtils.md5Hex(in);
    }

    /** SHA-1 **/
    public static byte[] sha1(final String data) {
        return DigestUtils.sha1(data);
    }

    public static byte[] sha1(final byte[] data) {
        return DigestUtils.sha1(data);
    }

    public static byte[] sha1(final InputStream in) throws IOException {
        return DigestUtils.sha1(in);
    }

    public static String sha1Hex(final String data) {
        return DigestUtils.sha1Hex(data);
    }

    public static String sha1Hex(final byte[] data) {
        return DigestUtils.sha1Hex(data);
    }

    public static String sha1Hex(final InputStream in) throws IOException {
        return DigestUtils.sha1Hex(in);
    }

    /** SHA-256 **/
    public static byte[] sha256(final String data) {
        return DigestUtils.sha256(data);
    }

    public static byte[] sha256(final byte[] data) {
        return DigestUtils.sha256(data);
    }

    public static byte[] sha256(final InputStream in) throws IOException {
        return DigestUtils.sha256(in);
    }

    public static String sha256Hex(final String data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String sha256Hex(final byte[] data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String sha256Hex(final InputStream in) throws IOException {
        return DigestUtils.sha256Hex(in);
    }

    /** SHA-512 **/
    public static byte[] sha512(final String data) {
        return DigestUtils.sha512(data);
    }

    public static byte[] sha512(final byte[] data) {
        return DigestUtils.sha512(data);
    }

    public static byte[] sha512(final InputStream in) throws IOException {
        return DigestUtils.sha512(in);
    }

    public static String sha512Hex(final String data) {
        return DigestUtils.sha512Hex(data);
    }

    public static String sha512Hex(final byte[] data) {
        return DigestUtils.sha512Hex(data);
    }

    public static String sha512Hex(final InputStream in) throws IOException {
        return DigestUtils.sha512Hex(in);
    }

}