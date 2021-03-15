package juice.lock.redis;

import juice.lock.DistributedLock;
import juice.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class RedisDistributedLock implements DistributedLock {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final String id;
    final String name;
    private StringRedisTemplate redisTemplate;
    final long internalLockLeaseTime;
    RedisDistributedLock(StringRedisTemplate redisTemplate, String name) {
        this.id = UUIDUtils.getId();
        this.redisTemplate = redisTemplate;
        this.name = name;
        this.internalLockLeaseTime = 30 * 1000;
    }

    @Override
    public boolean lock(long leaseTime, TimeUnit unit) {
        return tryLock(-1, leaseTime, unit);
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
    public boolean isLocked() {
        return redisTemplate.hasKey(getName());
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return isHeldByThread(Thread.currentThread().getId());
    }

    @Override
    public void forceUnlock() {
        //强制解锁
        redisTemplate.delete(getName());
    }

    @Override
    public boolean unlock() {
        return unlockInnerAsync(Thread.currentThread().getId());
    }

    boolean tryLockInner(long leaseTime, TimeUnit unit, long threadId) {
        long lockLeaseTimeInMillis = unit.toMillis(leaseTime);
        Long returnVal = (Long) redisTemplate.execute(RedisScriptHolder.getInstance().getLockScript(),
                Collections.singletonList(getName()), String.valueOf(lockLeaseTimeInMillis), getLockName(threadId));
        if (logger.isDebugEnabled()) {
            logger.debug("分布式锁-加锁操作, name:{}, lockName:{} 加锁操作执行结果:{}", getName(), getLockName(threadId), returnVal);
        }
        if (returnVal > 0) {
            return true;
        }
        return false;
    }

    public boolean isHeldByThread(long threadId) {
        return redisTemplate.opsForHash().hasKey(getName(), getLockName(threadId));
    }

    protected boolean unlockInnerAsync(long threadId) {
        Long returnVal = (Long) redisTemplate.execute(RedisScriptHolder.getInstance().getUnlockScript(),
                Collections.singletonList(getName()), String.valueOf(internalLockLeaseTime), getLockName(threadId));
        if (logger.isDebugEnabled()) {
            logger.debug("分布式锁-解锁操作, name:{}, lockName:{} 解锁操作执行结果:{}", getName(), getLockName(threadId), returnVal);
        }
        if (returnVal > 0) {
            return true;
        }
        return false;
    }

    protected String getName() {
        return name;
    }

    protected String getLockName(long threadId) {
        return String.format("%s:%s", id, threadId);
    }
}
