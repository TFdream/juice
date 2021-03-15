package juice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ricky Fung
 */
public abstract class MobileUtils {

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^1[3-9]\\d{9}$";
    private static final Pattern MOBILE_PATTERN = Pattern.compile(REGEX_MOBILE, Pattern.CASE_INSENSITIVE);

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    private static final Pattern ID_CARD_PATTERN = Pattern.compile(REGEX_ID_CARD, Pattern.CASE_INSENSITIVE);

    /**
     * 是否为有效手机号
     * @param mobile
     * @return
     */
    public static boolean isValidMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 是否为有效身份证号
     * @param idNo
     * @return
     */
    public static boolean isValidIdNo(String idNo) {
        if (StringUtils.isEmpty(idNo)) {
            return false;
        }
        Matcher m = ID_CARD_PATTERN.matcher(idNo);
        return m.matches();
    }

}
