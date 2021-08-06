package juice.config.springsupport.tracing.interceptor;

import juice.commons.TimerContext;
import juice.tracing.TracingConstants;
import juice.tracing.TracingContext;
import juice.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基于SLF4j日志追踪拦截器
 * @author Ricky Fung
 */
public class TracingInterceptor extends HandlerInterceptorAdapter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置tracing
        String traceId = request.getHeader(TracingConstants.TRACE_ID_HEADER);
        String spanId = request.getHeader(TracingConstants.SPAN_ID_HEADER);

        if (StringUtils.isEmpty(traceId)) {
            traceId = TracingContext.newTraceId();
        }
        if (StringUtils.isEmpty(spanId)) {
            spanId = TracingConstants.ROOT_SPAN_ID;
        } else {
            //计算当前的spanId
            spanId = TracingContext.genNextSpanId(spanId);
        }
        //init trace
        TracingContext.putTraceId(traceId);
        TracingContext.putSpanId(spanId);

        //调用开始
        TimerContext.start();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //计算接口执行耗时
        long costTime = TimerContext.stopAndGet();
        LOG.info("分布式Tracing-拦截器-链路追踪结束, 接口URI:{}, 耗时:{} ms", request.getRequestURI(), costTime);

        //删除tracing
        TracingContext.clear();
    }
}
