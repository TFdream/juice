package juice.util;

import juice.enums.GenderEnum;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * @author Ricky Fung
 */
public class IdCardUtils {
    //旧一代身份证号
    private static final int OLD_ID_NO_LENGTH = 15;

    //新一代身份证号 18位
    private static final int NEW_ID_NO_LENGTH = 18;

    private IdCardUtils() {}
    
    /**
     * 根据身份证号获取性别
     * @param idNo 身份证号
     * @return 性别(1: 男，0: 女，-1: 未知)
     */
    public static GenderEnum getGender(String idNo) {
        if (!isValidIdNo(idNo)) {
            return GenderEnum.UNKNOWN;
        }
        if (idNo.length() == OLD_ID_NO_LENGTH) {
            //旧身份证号 规则：其中第15位男为单数,女为双数;
            int mod = Integer.valueOf(idNo.charAt(OLD_ID_NO_LENGTH - 1)) % 2;
            return GenderEnum.getByType(mod);
        }
        //新身份证号 规则：第十七位奇数分给男性，偶数分给女性。
        int mod = Integer.valueOf(idNo.charAt(16)) % 2;
        return GenderEnum.getByType(mod);
    }

    /**
     * 根据身份证号获取用户年龄
     * @param idNo
     * @return
     */
    public static int getAge(String idNo) {
        if (!isValidIdNo(idNo)) {
            return -1;
        }
        String yearStr;
        if (idNo.length() == OLD_ID_NO_LENGTH) {
            //旧身份证号 规则：7-12位出生年月日,比如670401代表1967年4月1日
            StringBuilder sb = new StringBuilder();
            sb.append("19").append(idNo.substring(6, 12));
            yearStr = sb.toString();
        } else {
            //新身份证号
            yearStr = idNo.substring(6, 14);
        }
        LocalDate birthDate = LocalDate.parse(yearStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate now = LocalDate.now();

        Period period = Period.between(birthDate, now);
        return period.getYears();
    }

    public static boolean isValidIdNo(String idNo) {
        if (StringUtils.isNotEmpty(idNo) && (idNo.length() == OLD_ID_NO_LENGTH || idNo.length() == NEW_ID_NO_LENGTH)) {
            char first = idNo.charAt(0);
            if (first > '0' && first < '7') {
                return true;
            }
        }
        return false;
    }
}
