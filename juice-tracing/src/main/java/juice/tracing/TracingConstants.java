package juice.tracing;

/**
 * @author Ricky Fung
 */
public abstract class TracingConstants {

    //HTTP请求头字段
    public static final String TRACE_ID_HEADER = "X-TraceId";
    public static final String SPAN_ID_HEADER = "X-SpanId";

    //Slf4j MDC参数
    public static final String SLF4J_TRACE_ID = "X-TraceId";
    public static final String SLF4j_SPAN_ID = "X-SpanId";

    public static final String ROOT_SPAN_ID = "0";
}
