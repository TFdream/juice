package juice.core.codec;

import juice.core.util.CharsetUtils;
import juice.core.util.codec.Base64Utils;
import juice.core.util.codec.RSAUtils;
import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Ricky Fung
 */
public class RSAUtilsTest {

    @Test
    public void testRSA() throws NoSuchAlgorithmException {

        KeyPair keyPair = RSAUtils.generateKeyPair(1024);
        PublicKey pubKey = keyPair.getPublic();
        PrivateKey priKey = keyPair.getPrivate();

        String  publicKey = RSAUtils.getPublicKey(pubKey);
        String  privateKey = RSAUtils.getPrivateKey(priKey);

        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);

        String content = "站在大明门前守卫的禁卫军，事先没有接到有关的命令。hello world!";
        byte[] data = content.getBytes(CharsetUtils.CHARSET_UTF_8);

        System.out.println("明文：" + content);
        System.out.println("明文大小：" + data.length);

        //公钥加密
        byte[] encodeBuf = RSAUtils.encryptWithPublicKey(data,  RSAUtils.generatePublicKey(publicKey));
        System.out.println("密文：" + Base64Utils.encodeString(encodeBuf));
        System.out.println("密文大小：" + encodeBuf.length);

        //私钥解密
        byte[] decodeBuf = RSAUtils.decryptWithPrivateKey(encodeBuf, RSAUtils.generatePrivateKey(privateKey));
        System.out.println("解密后文字：" + new String(decodeBuf, CharsetUtils.CHARSET_UTF_8));

    }

}
