package juice.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 封装ThreadLocalRandom
 * @author Ricky Fung
 */
public abstract class RandomUtils {

    /**
     * 产生 [min, max) 范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public static double nextDouble(double min, double max) {
        double bound = max - min;
        return min + ThreadLocalRandom.current().nextDouble(bound);
    }

    /**
     * 产生 [min, max) 范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public static int nextInt(int min, int max) {
        int bound = max - min;
        return min + ThreadLocalRandom.current().nextInt(bound);
    }

    /**
     * 产生 [min, max) 范围内的随机数
     * @param min
     * @param max
     * @return
     */
    public static long nextLong(long min, long max) {
        long bound = max - min;
        return min + ThreadLocalRandom.current().nextLong(bound);
    }

    //===========

    /**
     * 产生验证码
     * @return
     */
    public static String genRandomCode(int length) {
        return genRandomCode(length, false);
    }

    /**
     * 产生纯数字验证码
     * @param length 长度
     * @param firstPositive 第一位是否要求为正数
     * @return
     */
    public static String genRandomCode(int length, boolean firstPositive) {
        StringBuilder sb = new StringBuilder(length);
        for (int i=0; i<length; i++) {
            int num;
            if (i == 0) {
                int low = firstPositive ? 1 : 0;
                num = ThreadLocalRandom.current().nextInt(low,10);
            } else {
                num = ThreadLocalRandom.current().nextInt(10);
            }
            sb.append(num);
        }
        return sb.toString();
    }
}
