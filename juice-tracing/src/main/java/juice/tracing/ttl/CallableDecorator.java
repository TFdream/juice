package juice.tracing.ttl;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Ricky Fung
 */
public class CallableDecorator<T> implements Callable<T> {
    private final Callable<T> command;
    private final Map<String, String> context;

    public CallableDecorator(Callable<T> command, Map<String, String> fixedContext) {
        this.command = command;
        this.context = fixedContext;
    }

    @Override
    public T call() throws Exception {
        Map previous = MDC.getCopyOfContextMap();
        if (context == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(context);
        }
        try {
            return command.call();
        } finally {
            if (previous == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(previous);
            }
        }
    }
}
