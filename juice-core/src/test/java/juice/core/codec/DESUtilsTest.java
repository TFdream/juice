package juice.core.codec;

import juice.core.util.CharsetUtils;
import juice.core.util.codec.AESUtils;
import juice.core.util.codec.Base64Utils;
import juice.core.util.codec.DESUtils;
import org.junit.Test;

/**
 * @author Ricky Fung
 */
public class DESUtilsTest {

    private String secret = "abcefg0123456789";

    @Test
    public void testDES() throws Exception {

        String content = "站在大明门前守卫的禁卫军，事先没有接到有关的命令。hello world!";
        byte[] data = content.getBytes(CharsetUtils.CHARSET_UTF_8);

        System.out.println("明文：" + content);
        System.out.println("明文大小：" + data.length);

        byte[] encodeBuf = DESUtils.encrypt(data, secret);
        System.out.println("密文：" + Base64Utils.encodeString(encodeBuf));
        System.out.println("密文大小：" + encodeBuf.length);

        byte[] decodeBuf = DESUtils.decrypt(encodeBuf, secret);
        System.out.println("解密后文字：" + new String(decodeBuf, CharsetUtils.CHARSET_UTF_8));
    }
}
