package juice.util;

/**
 * 版本号工具
 * 版本号格式: major.minor.patch 例如: 7.4.2 7.5.10
 * @author Ricky Fung
 */
public abstract class VersionUtils {

    /**
     * 版本号转整数<br>
     * 例如:
     * 7.4.2 -> 742
     * 7.4 - > 74
     * @param version
     * @return
     */
    public static int getInt(String version) {
        if (StringUtils.isEmpty(version)) {
            return 0;
        }
        StringBuilder sb = new StringBuilder(8);
        char[] chars = version.toCharArray();
        for (int i=0; i<chars.length; i++) {
            if (chars[i] == '.') {
                continue;
            }
            sb.append(chars[i]);
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * 比较两个指定版本号的大小
     * @param v1
     * @param v2
     * @return v1 < v2 则返回负整数, v1 = v2 则返回0, v1 > v2 则返回正整数
     */
    public static int compare(String v1, String v2) {
        if (v1.equals(v2)) {
            return 0;
        }
        String[] arr1 = v1.split("\\.");
        String[] arr2 = v2.split("\\.");

        int len1 = arr1.length;
        int len2 = arr2.length;
        int lim = Math.min(len1, len2);

        int k = 0;
        while (k < lim) {
            int c1 = Integer.parseInt(arr1[k]);
            int c2 = Integer.parseInt(arr2[k]);
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;
    }

}
