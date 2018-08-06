package juice.core.util.codec;

import juice.core.util.StringUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Ricky Fung
 */
public abstract class DESUtils {
    public static final String DES_ALGORITHM = "DES";

    public static byte[] encrypt(String data, String key) throws Exception {
        return encrypt(StringUtils.stringToByteArray(data), StringUtils.stringToByteArray(key));
    }
    public static byte[] encrypt(String data, byte[] key) throws Exception {
        return encrypt(StringUtils.stringToByteArray(data), key);
    }
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        return encrypt(data, StringUtils.stringToByteArray(key));
    }
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = generateSecretKey(key);
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(String data, String key) throws Exception {
        return decrypt(StringUtils.stringToByteArray(data), StringUtils.stringToByteArray(key));
    }
    public static byte[] decrypt(String data, byte[] key) throws Exception {
        return decrypt(StringUtils.stringToByteArray(data), key);
    }
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        return decrypt(data, StringUtils.stringToByteArray(key));
    }
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = generateSecretKey(key);
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private static SecretKey generateSecretKey(byte[] key) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key);
        KeyGenerator kg = KeyGenerator.getInstance(DES_ALGORITHM);
        kg.init(secureRandom);
        return kg.generateKey();
    }
}
