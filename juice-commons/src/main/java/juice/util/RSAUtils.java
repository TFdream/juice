package juice.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * RSA非对称加密算法
 *
 * 以RSA算法为例，它的密钥有256/512/1024/2048/4096等不同的长度。长度越长，密码强度越大，当然计算速度也越慢。
 * 使用512bit的RSA加密时，明文长度不能超过53字节，使用1024bit的RSA加密时，明文长度不能超过117字节，这也是为什么使用RSA的时候，总是配合AES一起使用，即用AES加密任意长度的明文，用RSA加密AES口令。
 * @author Ricky Fung
 */
public class RSAUtils {
    private static final String ALG_NAME = "RSA";

    private RSAUtils() {}
    
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

    //=========
    /**
     * 恢复私钥
     * @param input
     * @return
     * @throws InvalidKeySpecException
     */
    public static PrivateKey parsePrivateKey(byte[] input) throws InvalidKeySpecException {
        try {
            KeyFactory kf = KeyFactory.getInstance(ALG_NAME);
            PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(input);
            return kf.generatePrivate(skSpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("不支持RSA算法", e);
        }
    }

    // 把私钥导出为字节
    public static byte[] getPrivateKey(PrivateKey sk) {
        return sk.getEncoded();
    }

    /**
     * 恢复公钥
     * @param input
     * @return
     * @throws InvalidKeySpecException
     */
    public static PublicKey parsePublicKey(byte[] input) throws InvalidKeySpecException {
        try {
            KeyFactory kf = KeyFactory.getInstance(ALG_NAME);
            PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(input);
            return kf.generatePublic(skSpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("不支持RSA算法", e);
        }
    }

    // 把公钥导出为字节
    public static byte[] getPublicKey(PublicKey pk) {
        return pk.getEncoded();
    }

    public static KeyPair genKeyPair() {
        return genKeyPair(1024);
    }

    public static KeyPair genKeyPair(int keySize) {
        try {
            // 生成公钥／私钥对:
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(ALG_NAME);
            kpGen.initialize(keySize);
            KeyPair kp = kpGen.generateKeyPair();
            return kp;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("不支持RSA算法", e);
        }
    }

}
