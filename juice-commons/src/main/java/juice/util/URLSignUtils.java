package juice.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * URL防篡改工具类
 * @author Ricky Fung
 */
public class URLSignUtils {
    private static final String SEPARATOR = "&";
    private static final String EQ = "=";

    private static final int DEFAULT_CAPACITY = 256;

    private URLSignUtils() {}
    
    /**
     * 根据参数计算签名
     * @param paramMap
     * @param secret
     * @return
     */
    public static String genSign(Map<String, String> paramMap, String secret) {
        return genSign(paramMap, secret, DEFAULT_CAPACITY);
    }

    /**
     * 根据参数计算签名
     * @param paramMap
     * @param secret
     * @param capacity
     * @return
     */
    public static String genSign(Map<String, String> paramMap, String secret, int capacity) {
        Map<String, String> map = new TreeMap<>(paramMap);

        StringBuilder sb = new StringBuilder(capacity);
        for (Map.Entry<String, String> me : map.entrySet()) {
            //key=value
            sb.append(me.getKey()).append(EQ).append(me.getValue()).append(SEPARATOR);
        }
        //添加secret
        sb.append(secret);
        String str = sb.toString();
        return CodecUtils.sha1Hex(str);
    }

    /**
     * 校验签名
     * @param param
     * @param secret
     * @param sign
     * @return
     */
    public static boolean checkSign(Map<String, String> param, String secret, String sign) {
        String origin = genSign(param, secret);
        return origin.equals(sign);
    }
}
