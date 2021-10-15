package juice.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * AES 加密/解密
 * @author Ricky Fung
 */
public class AESUtils {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static String ALGORITHM_AES = "AES";
    private static final int SECRET_KEY_LENGTH = 16;

    //密钥(上线后不要修改此属性值，否则后果不堪设想)
    private static final String DEFAULT_SECRET_KEY = "juice_2021";

    private AESUtils() {}
    
    public static String encrypt(String plainText) {
        return encrypt(plainText, DEFAULT_SECRET_KEY);
    }

    /**
     * AES加密
     * @param plainText
     * @param secret
     * @return
     */
    public static String encrypt(String plainText, String secret) {
        try {
            //1.产生key
            SecretKey secretKey = genSecretKey(secret);
            //2.加密
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] data = plainText.getBytes(UTF_8);
            byte[] result = cipher.doFinal(data);

            return base64EncodeStr(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String cipherText) {
        return decrypt(cipherText, DEFAULT_SECRET_KEY);
    }

    /**
     * AES解密
     * @param cipherText
     * @param secret
     * @return
     */
    public static String decrypt(String cipherText, String secret) {
        try {
            //1.产生key
            SecretKey secretKey = genSecretKey(secret);
            //2.加密
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] c = base64Decode(cipherText);
            byte[] buf = cipher.doFinal(c);

            return new String(buf, UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //---------

    private static SecretKey genSecretKey(String myKey) {
        try {
            byte[] key = myKey.getBytes(UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, SECRET_KEY_LENGTH);
            return new SecretKeySpec(key, ALGORITHM_AES);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("不支持的算法");
        }
    }

    private static byte[] base64Decode(String cipherText) {
        return Base64.decodeBase64(cipherText);
    }

    //----

    private static String base64EncodeStr(byte[] buf) {
        byte[] data = Base64.encodeBase64(buf);
        return new String(data, UTF_8);
    }

    private static byte[] base64Encode(byte[] buf) {
        return Base64.encodeBase64(buf);
    }

}
