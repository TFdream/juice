package juice.util;

import java.math.BigDecimal;

/**
 * @author Ricky Fung
 */
public final class DecimalUtils {
    private DecimalUtils() {
    }

    private static final int DEFAULT_SCALE = 2;
    private static final int INT_ZERO = 0;

    //==========
    public static final BigDecimal MINUS_ONE = new BigDecimal("-1");

    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ONE = BigDecimal.ONE;

    public static final BigDecimal TEN = BigDecimal.TEN;
    public static final BigDecimal TWENTY = new BigDecimal("20.00");
    public static final BigDecimal FIFTY = new BigDecimal("50.00");

    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");
    public static final BigDecimal TWO_HUNDRED = new BigDecimal("200.00");
    public static final BigDecimal FIVE_HUNDRED = new BigDecimal("500.00");

    public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000.00");
    public static final BigDecimal TWO_THOUSAND = new BigDecimal("2000.00");
    public static final BigDecimal FIVE_THOUSAND = new BigDecimal("5000.00");

    public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000.00");

    //=======基本类型
    public static BigDecimal valueOf(double num) {
        return new BigDecimal(Double.toString(num));
    }

    public static BigDecimal valueOf(float num) {
        return new BigDecimal(Float.toString(num));
    }

    public static BigDecimal valueOf(int num) {
        return new BigDecimal(Integer.toString(num));
    }

    public static BigDecimal valueOf(long num) {
        return new BigDecimal(Long.toString(num));
    }

    //=======包装类型
    public static BigDecimal valueOf(Double num) {
        return new BigDecimal(num.toString());
    }

    public static BigDecimal valueOf(Float num) {
        return new BigDecimal(num.toString());
    }

    public static BigDecimal valueOf(Integer num) {
        return new BigDecimal(num.toString());
    }

    public static BigDecimal valueOf(Long num) {
        return new BigDecimal(num.toString());
    }

    public static BigDecimal valueOf(String val) {
        return new BigDecimal(val);
    }

    //=======格式化为字符串
    public static String format(BigDecimal bd) {
        return bd.setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String format(BigDecimal bd, int scale) {
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String format(BigDecimal bd, int scale, int roundingMode) {
        return bd.setScale(scale, roundingMode).toString();
    }

    //=======设置精度
    public static BigDecimal setScale(BigDecimal bd, int scale) {
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * @param bd
     * @param scale
     * @param roundingMode 取值参考 BigDecimal.ROUND_HALF_UP 等
     * @return
     */
    public static BigDecimal setScale(BigDecimal bd, int scale, int roundingMode) {
        return bd.setScale(scale, roundingMode);
    }

    //=======求最小值
    public static int min(int num1, int num2) {
        return num1 < num2 ? num1 : num2;
    }
    public static int min(int num1, int num2, int num3) {
        int min = num1 < num2 ? num1 : num2;
        return min < num3 ? min : num3;
    }

    public static long min(long num1, long num2) {
        return num1 < num2 ? num1 : num2;
    }
    public static long min(long num1, long num2, long num3) {
        long min = num1 < num2 ? num1 : num2;
        return min < num3 ? min : num3;
    }

    public static BigDecimal min(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) < INT_ZERO ? num1 : num2;
    }
    public static BigDecimal min(BigDecimal num1, BigDecimal num2, BigDecimal num3) {
        BigDecimal min = num1.compareTo(num2) < INT_ZERO ? num1 : num2;
        return min.compareTo(num3) < INT_ZERO ? min : num3;
    }

    //=======求最大值
    public static int max(int num1, int num2) {
        return num1 > num2 ? num1 : num2;
    }
    public static int max(int num1, int num2, int num3) {
        int max = num1 > num2 ? num1 : num2;
        return max > num3 ? max : num3;
    }

    public static long max(long num1, long num2) {
        return num1 > num2 ? num1 : num2;
    }
    public static long max(long num1, long num2, long num3) {
        long max = num1 > num2 ? num1 : num2;
        return max > num3 ? max : num3;
    }

    public static BigDecimal max(BigDecimal num1, BigDecimal num2) {
        return num1.compareTo(num2) > INT_ZERO ? num1 : num2;
    }
    public static BigDecimal max(BigDecimal num1, BigDecimal num2, BigDecimal num3) {
        BigDecimal max = num1.compareTo(num2) > INT_ZERO ? num1 : num2;
        return max.compareTo(num3) > INT_ZERO ? max : num3;
    }

    //=======加法
    public static BigDecimal add(int v1, int v2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.add(b2);
    }
    public static BigDecimal add(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.add(b2);
    }
    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal b1, BigDecimal b2, int scale) {
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal add(BigDecimal b1, BigDecimal b2, int scale, int roundingMode) {
        return b1.add(b2).setScale(scale, roundingMode);
    }

    //=======减法
    public static BigDecimal sub(int v1, int v2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal sub(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }

    public static BigDecimal sub(BigDecimal b1, BigDecimal b2, int scale) {
        return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal sub(BigDecimal b1, BigDecimal b2, int scale, int roundingMode) {
        return b1.subtract(b2).setScale(scale, roundingMode);
    }

    //=======乘法
    public static BigDecimal mul(int v1, int v2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal mul(long v1, long v2) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
        return b1.multiply(b2);
    }

    public static BigDecimal mul(BigDecimal b1, BigDecimal b2, int scale) {
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal mul(BigDecimal b1, BigDecimal b2, int scale, int roundingMode) {
        return b1.multiply(b2).setScale(scale, roundingMode);
    }

    //=======除法
    public static BigDecimal div(int v1, int v2) {
        return div(v1, v2, DEFAULT_SCALE);
    }
    public static BigDecimal div(int v1, int v2, int scale) {
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    public static BigDecimal div(long v1, long v2) {
        return div(v1, v2, DEFAULT_SCALE);
    }

    public static BigDecimal div(long v1, long v2, int scale) {
        BigDecimal b1 = new BigDecimal(Long.toString(v1));
        BigDecimal b2 = new BigDecimal(Long.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入,保留两位小数
    }

    public static BigDecimal div(double v1, double v2) {
        return div(v1, v2, DEFAULT_SCALE);
    }
    public static BigDecimal div(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }
    public static BigDecimal div(double v1, double v2, int scale, int roundingMode) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, roundingMode);
    }

    //=============
    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        return div(v1, v2, DEFAULT_SCALE);
    }

    public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale, int roundingMode) {
        return b1.divide(b2, scale, roundingMode);
    }
}
