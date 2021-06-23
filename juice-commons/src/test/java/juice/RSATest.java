package juice;

import juice.util.Base64Utils;
import juice.util.RSAUtils;
import juice.util.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Ricky Fung
 */
public class RSATest {

    @Test
    @Ignore
    public void testRSA() throws GeneralSecurityException {
        // 明文:
        String plain = "Hello, encrypt use RSA";

        // 创建公钥／私钥对:
        KeyPair kp = RSAUtils.genKeyPair();

        // 私钥:
        PrivateKey sk = kp.getPrivate();
        // 公钥:
        PublicKey pk = kp.getPublic();

        System.out.println(String.format("public key: %s", Base64Utils.encode(pk.getEncoded())));

        // 用Alice的公钥加密:
        byte[] encrypted = RSAUtils.encrypt(plain, pk);
        System.out.println("用Alice的公钥加密:"+String.format("encrypted: %s", Base64Utils.encode(encrypted)));

        // 用Alice的私钥解密:
        System.out.println(String.format("private key: %s", Base64Utils.encode(sk.getEncoded())));
        byte[] decrypted = RSAUtils.decrypt(encrypted, sk);

        System.out.println("用Alice的私钥解密:"+StringUtils.getStringUtf8(decrypted));
    }
}
