package juice.lock.redis;

import juice.lock.DistributedLock;
import juice.lock.DistributedLockManager;
import juice.lock.DistributedReadWriteLock;
import juice.lock.MultiLock;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 参考资料：
 * Distributed locks with Redis: https://redis.io/topics/distlock
 * Redisson: https://github.com/redisson/redisson
 * @author Ricky Fung
 */
public class RedisDistributedLockManager implements DistributedLockManager {
    private final StringRedisTemplate redisTemplate;

    /**
     * 注意：必须是StringRedisTemplate，否则会因为 JdkSerializationRedisSerializer序列化导致lua执行失败
     * @param redisTemplate
     */
    public RedisDistributedLockManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public DistributedLock getLock(String name) {
        return new RedisDistributedLock(redisTemplate, name);
    }

    @Override
    public DistributedReadWriteLock getReadWriteLock(String name) {
        return new RedisReadWriteLock(redisTemplate, name);
    }

    @Override
    public DistributedLock getMultiLock(DistributedLock... locks) {
        return new MultiLock(locks);
    }

}
