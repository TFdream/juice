package juice.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author Ricky Fung
 */
public interface DistributedLock {

    /**
     * Acquire lock and release it automatically after 10 seconds
     * if unlock method hasn't been invoked
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean lock(long leaseTime, TimeUnit unit);

    /**
     * Wait for 100 seconds and automatically unlock it after 10 seconds
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);

    boolean isLocked();

    boolean isHeldByCurrentThread();

    void forceUnlock();

    boolean unlock();
}
