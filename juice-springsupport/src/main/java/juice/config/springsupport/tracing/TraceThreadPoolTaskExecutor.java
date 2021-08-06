package juice.config.springsupport.tracing;

import juice.tracing.ttl.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 增强ThreadPoolTaskExecutor 适配MDC
 * @author Ricky Fung
 */
public class TraceThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(TaskDecorator.decorate(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(TaskDecorator.decorate(task), startTimeout);
    }

    //=========
    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(TaskDecorator.decorate(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TaskDecorator.decorate(task));
    }

    //=========

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(TaskDecorator.decorate(task));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(TaskDecorator.decorate(task));
    }
}
