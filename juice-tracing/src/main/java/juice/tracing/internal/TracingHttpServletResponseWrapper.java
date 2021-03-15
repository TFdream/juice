package juice.tracing.internal;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Ricky Fung
 */
public class TracingHttpServletResponseWrapper extends HttpServletResponseWrapper {

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public TracingHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
