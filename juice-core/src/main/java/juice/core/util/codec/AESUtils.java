package juice.core.util.codec;

import juice.core.util.StringUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author Ricky Fung
 */
public abstract class AESUtils {
    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static byte[] encrypt(String data, String key) throws Exception{
        return encrypt(StringUtils.stringToByteArray(data), StringUtils.stringToByteArray(key));
    }
    public static byte[] encrypt(String data, byte[] key) throws Exception{
        return encrypt(StringUtils.stringToByteArray(data), key);
    }
    public static byte[] encrypt(byte[] data, String key) throws Exception{
        return encrypt(data, StringUtils.stringToByteArray(key));
    }
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
        Key k = genSecretKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
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
        Key k = genSecretKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**AES only supports key sizes of 16, 24 or 32 bytes*/
    public static Key genSecretKey(byte[] key) throws Exception{
        if(key.length==16 || key.length==24 || key.length==32){
            return new SecretKeySpec(key, KEY_ALGORITHM);
        }
        throw new IllegalArgumentException("AES only supports key sizes of 16, 24 or 32 bytes");
    }
}
