package juice.core.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public abstract class ThreadPoolExecutors {

    /**
     * Create Thread pool with default name.
     * @param minPoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @return
     */
    public static ThreadPoolExecutor newThreadPoolExecutor(int minPoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue) {
        return new ThreadPoolExecutor(minPoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * Create Thread pool with given name.
     * @param minPoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param poolName
     * @return
     */
    public static ThreadPoolExecutor newThreadPoolExecutor(int minPoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue, String poolName) {
        return new ThreadPoolExecutor(minPoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new DefaultThreadFactory(poolName));
    }
}
