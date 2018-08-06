package juice.core.util.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Ricky Fung
 */
public abstract class CalculatorUtils {

    /**
     * 加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 加法运算
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double add(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2, new MathContext(scale, RoundingMode.HALF_UP)).doubleValue();
    }

    /**
     * 减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 减法运算
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double subtract(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2, new MathContext(scale, RoundingMode.HALF_UP)).doubleValue();
    }

    /**
     * 乘法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 乘法运算
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double multiply(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        MathContext mc = new MathContext(scale, RoundingMode.HALF_UP);
        return b1.multiply(b2, mc).doubleValue();
    }

    /**
     * 除法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double divide(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2).doubleValue();
    }

    /**
     * 除法运算
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double divide(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
