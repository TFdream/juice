package juice.commons;

import java.util.concurrent.TimeUnit;

/**
 * 计时器
 * @author Ricky Fung
 */
public class TimerContext {
    private static final ThreadLocal<Long> TIME_HOLDER = new ThreadLocal();

    /**
     * 开始计时
     * 注：目前暂不支持跨线程
     * @return
     */
    public static long start() {
        long startTime = System.nanoTime();
        TIME_HOLDER.set(startTime);
        return startTime;
    }

    public static void clear() {
        //移除
        TIME_HOLDER.remove();
    }

    /**
     * 获取接口耗时，单位: 毫秒
     * @return
     */
    public static long stopAndGet() {
        long endTime = System.nanoTime();
        Long startTime = TIME_HOLDER.get();

        Assertions.notNull(startTime, "startTime不存在【请检查是否已调用过start方法】");

        //移除
        TIME_HOLDER.remove();
        long costTime = endTime - startTime;
        return nanosToMillis(costTime);
    }

    //=========
    /**
     * converting a big nanoseconds value to milliseconds.
     * @param time
     * @return
     */
    public static long nanosToMillis(long time) {
        return TimeUnit.NANOSECONDS.toMillis(time);
    }

    /**
     * 转换为毫秒
     * @param time
     * @param unit
     * @return
     */
    public static long toMillis(long time, TimeUnit unit) {
        return TimeUnit.MILLISECONDS.convert(time, unit);
    }

}
