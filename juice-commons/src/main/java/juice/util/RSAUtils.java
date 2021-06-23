package juice.util;

import javax.crypto.Cipher;
import java.security.*;

/**
 * RSA非对称加密算法
 * @author Ricky Fung
 */
public abstract class RSAUtils {
    private static final String ALG_NAME = "RSA";

    // 把私钥导出为字节
    public static byte[] getPrivateKey(PrivateKey sk) {
        return sk.getEncoded();
    }

    // 把公钥导出为字节
    public static byte[] getPublicKey(PublicKey pk) {
        return pk.getEncoded();
    }

    // 用公钥加密:
    public static byte[] encrypt(String message, PublicKey pk) throws GeneralSecurityException {
        return encrypt(StringUtils.getBytesUtf8(message), pk);
    }

    public static byte[] encrypt(byte[] message, PublicKey pk) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALG_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        return cipher.doFinal(message);
    }

    // 用私钥解密:
    public static byte[] decrypt(byte[] input, PrivateKey sk) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALG_NAME);
        cipher.init(Cipher.DECRYPT_MODE, sk);
        return cipher.doFinal(input);
    }

    public static KeyPair genKeyPair() {
        try {
            // 生成公钥／私钥对:
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(ALG_NAME);
            kpGen.initialize(1024);
            KeyPair kp = kpGen.generateKeyPair();
            return kp;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("不支持RSA", e);
        }
    }

}
