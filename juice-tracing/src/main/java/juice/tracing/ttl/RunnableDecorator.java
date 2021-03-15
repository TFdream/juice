package juice.tracing.ttl;

import org.slf4j.MDC;

import java.util.Map;

/**
 * @author Ricky Fung
 */
public class RunnableDecorator implements Runnable {
    private final Runnable command;
    private final Map<String, String> context;

    public RunnableDecorator(Runnable command, Map<String, String> fixedContext) {
        this.command = command;
        this.context = fixedContext;
    }

    @Override
    public void run() {
        Map previous = MDC.getCopyOfContextMap();
        if (context == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(context);
        }
        try {
            command.run();
        } finally {
            if (previous == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(previous);
            }
        }
    }
}
