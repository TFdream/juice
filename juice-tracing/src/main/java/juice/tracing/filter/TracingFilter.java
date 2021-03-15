package juice.tracing.filter;

import juice.commons.Splitter;
import juice.commons.TimerContext;
import juice.tracing.TracingConstants;
import juice.tracing.TracingContext;
import juice.tracing.internal.TracingHttpServletResponseWrapper;
import juice.util.CollectionUtils;
import juice.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class TracingFilter implements Filter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final List<String> excludedUris = new ArrayList<>();
    private boolean slowApiEnable = true;
    private int costTimeThreshold = 5000;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig config) throws ServletException {
        String excludePatterns = config.getInitParameter("excludePathPatterns");
        String sle = config.getInitParameter("slowApiEnable");
        String threshold = config.getInitParameter("costTimeThreshold");
        LOG.info("分布式Tracing-过滤器-初始化参数开始, excludePatterns:{}, slowApiEnable:{}, costTimeThreshold:{}",
                excludePatterns, sle, threshold);

        if (StringUtils.isNotEmpty(excludePatterns)) {
            Splitter splitter = Splitter.newBuilder().on(",").trimResults().build();
            excludedUris.addAll(splitter.splitToList(excludePatterns));
        }
        if (StringUtils.isNotEmpty(sle)) {
            slowApiEnable = Boolean.valueOf(sle);
        }
        if (StringUtils.isNotEmpty(threshold)) {
            costTimeThreshold = Integer.parseInt(threshold);
        }

        LOG.info("分布式Tracing-过滤器-初始化参数完成, excludedUris:{}, slowApiEnable:{}, costTimeThreshold:{}", excludedUris, slowApiEnable, costTimeThreshold);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {    //http请求
            try {
                HttpServletRequest req = (HttpServletRequest) request;
                String requestURI = req.getRequestURI();
                if(isExcludedUri(requestURI)){
                    //执行
                    chain.doFilter(request, response);
                    return;
                }

                //开始计时
                TimerContext.start();

                //设置tracing
                String traceId = req.getHeader(TracingConstants.TRACE_ID_HEADER);
                String spanId = req.getHeader(TracingConstants.SPAN_ID_HEADER);
                if (StringUtils.isEmpty(traceId)) { //兼容处理
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

                if (LOG.isDebugEnabled()) {
                    LOG.debug("分布式Tracing-过滤器-链路追踪开始, 接口URI:{}, 请求方式:{}, traceId:{}, spanId:{}", requestURI, req.getMethod(), traceId, spanId);
                }

                //追加响应头
                TracingHttpServletResponseWrapper myResponse = new TracingHttpServletResponseWrapper((HttpServletResponse) response);
                myResponse.addHeader(TracingConstants.TRACE_ID_HEADER, traceId);
                //执行
                chain.doFilter(request, myResponse);

                //计算接口执行耗时
                long costTime = TimerContext.stopAndGet();

                if (LOG.isDebugEnabled()) {
                    LOG.debug("分布式Tracing-过滤器-链路追踪结束, 接口URI:{}, 请求方式:{}, 耗时:{} ms, traceId:{}",
                            requestURI, req.getMethod(), costTime, traceId);
                }
                if (slowApiEnable && costTime >= costTimeThreshold) {
                    LOG.info("分布式Tracing-过滤器-链路追踪【慢查询】, 接口URI:{}, 请求方式:{}, 耗时:{} ms",
                            requestURI, req.getMethod(), costTime);
                }
            } finally {
                //删除tracing
                TracingContext.clear();
            }
        } else {
            //执行
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOG.info("分布式Tracing-过滤器-关闭");
    }

    private boolean isExcludedUri(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return false;
        }
        if (CollectionUtils.isEmpty(excludedUris)) {
            return false;
        }
        for (String ex : excludedUris) {
            if (pathMatcher.match(ex, uri))
                return true;
        }
        return false;
    }

}
