package juice.core.util.codec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Ricky Fung
 */
public abstract class RSAUtils {

    private static final String RSA_ALGORITHM = "RSA";

    //--------------------------------------------
    /** 公钥加密 **/
    public static byte[] encryptWithPublicKey(final byte[] data, RSAPublicKey publicKey) {
        try {
            // 对数据加密
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return doRSA(cipher, Cipher.ENCRYPT_MODE, data, publicKey.getModulus().bitLength());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /** 公钥解密 **/
    public static byte[] decryptByPublicKey(final byte[] data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return doRSA(cipher, Cipher.DECRYPT_MODE, data, publicKey.getModulus().bitLength());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    //---------------------------------------------------

    /** 私钥解密 **/
    public static byte[] decryptWithPrivateKey(byte[] data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey );

            return doRSA(cipher, Cipher.DECRYPT_MODE, data, privateKey.getModulus().bitLength());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** 私钥加密 **/
    public static byte[] encryptWithPrivateKey(byte[] data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return doRSA(cipher, Cipher.ENCRYPT_MODE, data, privateKey.getModulus().bitLength());
        }catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] doRSA(Cipher cipher, int mode, byte[] data, int keySize) throws IOException, BadPaddingException, IllegalBlockSizeException {
        int maxBlock;
        if(mode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        int length = data.length;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int offset = 0;
            byte[] buf;
            int i = 0;
            // 对数据分段加密
            while (length - offset > 0) {
                if (length - offset > maxBlock) {
                    buf = cipher.doFinal(data, offset, maxBlock);
                } else {
                    buf = cipher.doFinal(data, offset, length - offset);
                }
                baos.write(buf, 0, buf.length);
                i++;
                offset = i * maxBlock;
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            baos.close();
        }
    }

    //------------------------------
    /**
     * generate publicKey
     * @param publicKey
     * @return
     */
    public static RSAPublicKey generatePublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decode(publicKey));
            RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
            return key;
        } catch (Exception e) {
            throw new IllegalArgumentException("generate publicKey caught error", e);
        }
    }

    /**
     * generate privateKey
     * @param privateKey
     * @return
     */
    public static RSAPrivateKey generatePrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            return key;
        } catch (Exception e) {
            throw new IllegalArgumentException("generate privateKey caught error", e);
        }
    }

    //------------------------------

    public static String getPublicKey(PublicKey publicKey) {
        return Base64Utils.encodeString(publicKey.getEncoded());
    }

    public static String getPrivateKey(PrivateKey privateKey) {
        return Base64Utils.encodeString(privateKey.getEncoded());
    }

    //------------------------------
    /**
     * generate KeyPair
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }
}
