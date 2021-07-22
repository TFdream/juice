package juice.lock;

import org.springframework.dao.QueryTimeoutException;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public boolean lock(long leaseTime, TimeUnit unit) {
        return tryLock(-1, leaseTime, unit);
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
                lockAcquired = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
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
    public boolean isHeldByCurrentThread() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forceUnlock() {
        for (DistributedLock lock : locks) {
            lock.forceUnlock();
        }
    }

    @Override
    public boolean unlock() {
        BitSet bs = new BitSet();
        int index = 0;
        for (DistributedLock lock : locks) {
            bs.set(index++, lock.unlock());
        }
        return bs.cardinality() == locks.size();
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
