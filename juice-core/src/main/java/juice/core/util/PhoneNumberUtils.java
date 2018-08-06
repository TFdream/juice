package juice.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ricky Fung
 */
public abstract class PhoneNumberUtils {

    /** 身份证号正则 **/
    private static final Pattern PHONE_REGEX = Pattern.compile("(^1[3-9]\\d{9}$)");

    /**
     * 判断手机号是否合法
     * @param phone
     * @return true: 合法, false: 非法
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        Matcher m = PHONE_REGEX.matcher(phone);
        if (m.matches()) {
            return true;
        }
        return false;
    }
}
