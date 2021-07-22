package juice.lock.redis;

import juice.lock.AbstractDistributedLock;
import juice.util.UUIDUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Ricky Fung
 */
public abstract class AbstractRedisLock extends AbstractDistributedLock {
    final StringRedisTemplate redisTemplate;
    final long internalLockLeaseTime;

    AbstractRedisLock(StringRedisTemplate redisTemplate, String name) {
        super(UUIDUtils.getCompactId(), name);
        this.redisTemplate = redisTemplate;
        this.internalLockLeaseTime = 30 * 1000;
    }

    AbstractRedisLock(StringRedisTemplate redisTemplate, String name, long internalLockLeaseTime) {
        super(UUIDUtils.getCompactId(), name);
        this.redisTemplate = redisTemplate;
        this.internalLockLeaseTime = internalLockLeaseTime;
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
    public boolean isHeldByThread(long threadId) {
        return redisTemplate.opsForHash().hasKey(getName(), getLockName(threadId));
    }

    @Override
    public int getHoldCount() {
        Object obj = redisTemplate.opsForHash().get(getName(), getLockId());
        return obj != null ? Integer.valueOf(obj.toString()) : 0;
    }

    @Override
    public long remainTimeToLive() {
        return 0;
    }

    @Override
    public boolean forceUnlock() {
        //强制解锁
        return redisTemplate.delete(getName());
    }

}
