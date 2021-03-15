package juice.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL 编码&解码工具类
 * @author Ricky Fung
 */
public abstract class URLCodec {

    /**
     * URL编码 参考：URLEncoder.encode方法
     * @param data
     * @return
     */
    public static String encode(String data) {
        try {
            return URLEncoder.encode(data, StringUtils.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            //ignore
        }
        return data;
    }

    /**
     * URL解码 参考：URLDecoder.decode方法
     * @param data
     * @return
     */
    public static String decode(String data) {
        try {
            return URLDecoder.decode(data, StringUtils.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            //ignore
        }
        return data;
    }
}
