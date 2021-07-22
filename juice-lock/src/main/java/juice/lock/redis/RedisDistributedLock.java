package juice.lock.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class RedisDistributedLock extends AbstractRedisLock {

    RedisDistributedLock(StringRedisTemplate redisTemplate, String name) {
        super(redisTemplate, name);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        long time = unit.toMillis(waitTime);
        long start = System.currentTimeMillis();
        boolean success = tryLockInner(leaseTime, unit, Thread.currentThread().getId());
        // lock acquired
        if (success) {
            return true;
        }
        time -= (System.currentTimeMillis() - start);
        if (time <= 0) {
            return false;
        }
        //自旋
        while (true) {
            long currentTime = System.currentTimeMillis();
            success = tryLockInner(leaseTime, unit, Thread.currentThread().getId());
            // lock acquired
            if (success) {
                return true;
            }
            time -= (System.currentTimeMillis() - currentTime);
            if (time <= 0) {
                return false;
            }
            //
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    @Override
    public void unlock() {
        unlockInner(Thread.currentThread().getId());
    }

    boolean tryLockInner(long leaseTime, TimeUnit unit, long threadId) {
        long lockLeaseTimeInMillis = unit.toMillis(leaseTime);
        Long returnVal = (Long) redisTemplate.execute(RedisScriptHolder.getInstance().getLockScript(),
                Collections.singletonList(getName()), String.valueOf(lockLeaseTimeInMillis), getLockName(threadId));
        if (LOG.isDebugEnabled()) {
            LOG.debug("分布式锁-加锁操作, name={}, lockName={} 加锁操作执行结果={}", getName(), getLockName(threadId), returnVal);
        }
        if (returnVal > 0) {
            return true;
        }
        return false;
    }

    protected boolean unlockInner(long threadId) {
        Long returnVal = (Long) redisTemplate.execute(RedisScriptHolder.getInstance().getUnlockScript(),
                Collections.singletonList(getName()), String.valueOf(internalLockLeaseTime), getLockName(threadId));
        if (LOG.isDebugEnabled()) {
            LOG.debug("分布式锁-解锁操作, name={}, lockName={} 解锁操作执行结果={}", getName(), getLockName(threadId), returnVal);
        }
        if (returnVal > 0) {
            return true;
        }
        return false;
    }

}
