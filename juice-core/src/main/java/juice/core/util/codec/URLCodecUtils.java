package juice.core.util.codec;

import juice.core.util.CharsetUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Ricky Fung
 */
public abstract class URLCodecUtils {

    /**
     * URL encode
     * @param data
     * @return
     */
    public static String encode(String data) {
        try {
            return URLEncoder.encode(data, CharsetUtils.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * URL decode
     * @param data
     * @return
     */
    public static String decode(String data) {
        try {
            return URLDecoder.decode(data, CharsetUtils.UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
