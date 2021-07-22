package juice.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author Ricky Fung
 */
public abstract class AbstractDistributedLock implements DistributedLock {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * UUID
     */
    final String id;

    /**
     * 加锁的资源名
     */
    final String name;

    public AbstractDistributedLock(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected String getLockName(long threadId) {
        return id + ":" + threadId;
    }

    public String getLockId() {
        return getLockName(Thread.currentThread().getId());
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return isHeldByThread(Thread.currentThread().getId());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void lock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return tryLock(-1, time, unit);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
