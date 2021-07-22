package juice.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 * @author Ricky Fung
 */
public interface DistributedLock extends Lock {

    /**
     * Returns name of lock
     *
     * @return name
     */
    String getName();

    /**
     * Wait for 100 seconds and automatically unlock it after 10 seconds
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);

    /**
     * Unlocks the lock independently of its state
     *
     * @return <code>true</code> if lock existed and now unlocked
     *          otherwise <code>false</code>
     */
    boolean forceUnlock();

    /**
     * Checks if the lock locked by any thread
     *
     * @return <code>true</code> if locked otherwise <code>false</code>
     */
    boolean isLocked();

    /**
     * Checks if the lock is held by thread with defined <code>threadId</code>
     *
     * @param threadId Thread ID of locking thread
     * @return <code>true</code> if held by thread with given id
     *          otherwise <code>false</code>
     */
    boolean isHeldByThread(long threadId);

    boolean isHeldByCurrentThread();

    /**
     * Number of holds on this lock by the current thread
     *
     * @return holds or <code>0</code> if this lock is not held by current thread
     */
    int getHoldCount();

    /**
     * Remaining time to live of the lock
     *
     * @return time in milliseconds
     *          -2 if the lock does not exist.
     *          -1 if the lock exists but has no associated expire.
     */
    long remainTimeToLive();
}
