package juice.lock.redis;

import juice.lock.DistributedLock;
import juice.lock.DistributedReadWriteLock;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Ricky Fung
 */
public class RedisReadWriteLock implements DistributedReadWriteLock {
    final StringRedisTemplate redisTemplate;
    final String name;

    public RedisReadWriteLock(StringRedisTemplate redisTemplate, String name) {
        this.redisTemplate = redisTemplate;
        this.name = name;
    }

    @Override
    public DistributedLock readLock() {
        return new RedisReadLock(redisTemplate, name);
    }

    @Override
    public DistributedLock writeLock() {
        return new RedisWriteLock(redisTemplate, name);
    }
}
