package juice.tracing;

import juice.util.StringUtils;
import juice.util.UUIDUtils;
import org.slf4j.MDC;

/**
 * @author Ricky Fung
 */
public abstract class TracingContext {

    private static ThreadLocal<Integer> spanSeqTL = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    //============
    //-----get
    public static String get(String key) {
        return MDC.get(key);
    }
    public static String getTraceId() {
        return MDC.get(TracingConstants.SLF4J_TRACE_ID);
    }
    public static String getSpanId() {
        return MDC.get(TracingConstants.SLF4j_SPAN_ID);
    }

    //-----put
    public static void put(String key, String value) {
        MDC.put(key, value);
    }
    public static void putTraceId(String traceId) {
        MDC.put(TracingConstants.SLF4J_TRACE_ID, traceId);
    }
    public static void putSpanId(String spanId) {
        MDC.put(TracingConstants.SLF4j_SPAN_ID, spanId);
    }

    //-----remove
    public static void remove(String key) {
        MDC.remove(key);
    }
    public static void removeTraceId() {
        MDC.remove(TracingConstants.SLF4J_TRACE_ID);
    }
    public static void removeSpanId() {
        MDC.remove(TracingConstants.SLF4j_SPAN_ID);
    }

    /**
     * 便捷方法
     */
    public static void clear() {
        spanSeqTL.remove();
        removeTraceId();
        removeSpanId();
    }

    //============

    /**
     * 获取traceId，如果不存在则生成一个
     * @return
     */
    public static String computeTraceIdIfAbsent() {
        String traceId = getTraceId();
        if (StringUtils.isEmpty(traceId)) {
            traceId = newTraceId();
            //设置到MDC中
            putTraceId(traceId);
        }
        return traceId;
    }

    /**
     * 生成新的traceId
     * @return
     */
    public static String newTraceId() {
        return UUIDUtils.getCompactId().toUpperCase();
    }

    //==========

    public static String genNextSpanId() {
        String currentSpanId = getSpanId();
        return genNextSpanId(currentSpanId);
    }

    /**
     * 生成新的spanId
     * 示例：
     * 0 root span
     * 0.1
     * 0.2
     * 0.1.1
     * 0.1.2
     * 0.1.3
     * @param currentSpanId
     * @return
     */
    public static String genNextSpanId(String currentSpanId) {
        if (StringUtils.isEmpty(currentSpanId)) {
            return TracingConstants.ROOT_SPAN_ID;
        }
        int seq = spanSeqTL.get();
        spanSeqTL.set(spanSeqTL.get() + 1);

        StringBuilder sb = new StringBuilder(currentSpanId.length() + 3);
        sb.append(currentSpanId).append('.').append(spanSeqTL.get());
        String nextSpanId = sb.toString();
        return nextSpanId;
    }

    public static String rootSpanId() {
        return TracingConstants.ROOT_SPAN_ID;
    }

}
