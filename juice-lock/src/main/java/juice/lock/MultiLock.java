package juice.lock;

import org.springframework.dao.QueryTimeoutException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author Ricky Fung
 */
public class MultiLock implements DistributedLock {
    final List<DistributedLock> locks = new ArrayList();

    public MultiLock(DistributedLock... locks) {
        if (locks.length == 0) {
            throw new IllegalArgumentException("Lock objects are not defined");
        } else {
            this.locks.addAll(Arrays.asList(locks));
        }
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        //允许加锁失败节点个数限制
        int failedLocksLimit = failedLocksLimit();

        int failedOpCnt = 0;
        List<DistributedLock> acquiredLocks = new ArrayList<>(locks.size());
        for (DistributedLock lock : locks) {
            boolean lockAcquired;
            try {
                lockAcquired = lock.tryLock(waitTime, leaseTime, unit);
            } catch (QueryTimeoutException e) {
                lock.unlock();
                lockAcquired = false;
            } catch (Exception e) {
                lockAcquired = false;
            }

            if (lockAcquired) {
                acquiredLocks.add(lock);
            } else {
                failedOpCnt++;
                if (failedOpCnt > failedLocksLimit) {
                    break;
                }
            }
        }

        if (acquiredLocks.size() != locks.size()) { //加锁失败
            unlockInner(acquiredLocks);
            return false;
        }
        return true;
    }

    @Override
    public boolean isLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHeldByThread(long threadId) {
        return false;
    }

    @Override
    public boolean isHeldByCurrentThread() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHoldCount() {
        return 0;
    }

    @Override
    public long remainTimeToLive() {
        return 0;
    }

    @Override
    public boolean forceUnlock() {
        BitSet bs = new BitSet();
        int index = 0;
        for (DistributedLock lock : locks) {
            bs.set(index++, lock.forceUnlock());
        }
        return bs.cardinality() == locks.size();
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
    public void unlock() {
        for (DistributedLock lock : locks) {
            lock.unlock();
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    //========
    /**
     * 允许加锁失败节点个数限制
     * @return
     */
    protected int failedLocksLimit() {
        return 0;
    }

    protected void unlockInner(Collection<DistributedLock> locks) {
        locks.stream().forEach(DistributedLock::unlock);
    }
}
