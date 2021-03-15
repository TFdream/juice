package juice.tracing.ttl;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Ricky Fung
 */
public class TaskDecorator {
    private TaskDecorator() {}

    public static Runnable decorate(Runnable task) {
        return new RunnableDecorator(task, getContextForTask());
    }

    public static Callable decorate(Callable task) {
        return new CallableDecorator(task, getContextForTask());
    }

    private static Map<String, String> getContextForTask() {
        return MDC.getCopyOfContextMap();
    }
}
