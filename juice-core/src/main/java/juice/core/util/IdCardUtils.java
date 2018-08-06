package juice.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 18位身份证号码：前6位为省市区编码，7-10位为出生年份(四位数)，11-12位为出生月份，13-14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 * @author Ricky Fung
 */
public class IdCardUtils {

    /** 身份证号正则 **/
    private static final Pattern IDCARD_REGEX = Pattern.compile("^\\d{6}[1-2]\\d{10}[0-9Xx]$");

    private static final int LENGTH = 18;
    /** 未知 **/
    public static final int UNKNOWN = -1;
    /** 男性 **/
    public static final int MALE = 0;
    /** 女性 **/
    public static final int FEMALE = 1;

    /**
     * 判断身份证号是否合法
     * @param idNo
     * @return true: 合法 false: 非法
     */
    public static boolean isValidIdCard(String idNo) {
        if (StringUtils.isEmpty(idNo)) {
            return false;
        }
        Matcher m = IDCARD_REGEX.matcher(idNo);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 根据身份证号推断性别
     * @param idNo
     * @return
     */
    public static int getGender(String idNo) {
        if (!isValidIdCard(idNo)) {
            return UNKNOWN;
        }
        int flag = Integer.parseInt(String.valueOf(idNo.charAt(LENGTH-2)));
        if ((flag & 1) == 0) {
            return FEMALE;
        }
        return MALE;
    }

    /**
     * 根据身份证号推断出生日期
     * @param idNo
     * @return
     */
    public static String getBirthday(String idNo) {
        if (!isValidIdCard(idNo)) {
            return null;
        }
        return idNo.substring(6, 14);
    }

}
