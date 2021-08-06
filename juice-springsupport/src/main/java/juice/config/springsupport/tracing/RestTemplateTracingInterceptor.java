package juice.config.springsupport.tracing;

import juice.tracing.TracingConstants;
import juice.tracing.TracingContext;
import juice.util.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 发起请求时自动携带TraceId
 * @author Ricky Fung
 */
public class RestTemplateTracingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = TracingContext.getTraceId();
        String spanId = TracingContext.getSpanId();
        if (StringUtils.isNotEmpty(traceId) && !request.getHeaders().containsKey(TracingConstants.TRACE_ID_HEADER)) {
            request.getHeaders().add(TracingConstants.TRACE_ID_HEADER, traceId);
        }
        if (StringUtils.isNotEmpty(spanId) &&
                !request.getHeaders().containsKey(TracingConstants.SPAN_ID_HEADER)) {
            request.getHeaders().add(TracingConstants.SPAN_ID_HEADER, spanId);
        }
        return execution.execute(request, body);
    }
}
