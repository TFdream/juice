package juice.lock;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 读写锁：https://zh.wikipedia.org/zh-hans/%E8%AF%BB%E5%86%99%E9%94%81
 * @author Ricky Fung
 */
public interface DistributedReadWriteLock extends ReadWriteLock {

    /**
     * Returns the lock used for reading.
     *
     * @return the lock used for reading
     */
    @Override
    DistributedLock readLock();

    /**
     * Returns the lock used for writing.
     *
     * @return the lock used for writing
     */
    @Override
    DistributedLock writeLock();
}
