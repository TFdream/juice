package juice.distlock;

import java.util.concurrent.TimeUnit;

/**
 * Distributed Lock
 * @author Ricky Fung
 */
public interface DLock {

    void lock();

    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean isLocked();

    boolean isHeldByCurrentThread();

    void forceUnlock();

    void unlock();
}
