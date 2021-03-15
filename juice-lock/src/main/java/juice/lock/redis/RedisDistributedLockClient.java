package juice.lock.redis;

import juice.lock.DistributedLock;
import juice.lock.DistributedLockClient;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Ricky Fung
 */
public class RedisDistributedLockClient implements DistributedLockClient {
    private final StringRedisTemplate redisTemplate;

    /**
     * 注意：必须是StringRedisTemplate，否则会因为 JdkSerializationRedisSerializer序列化导致lua执行失败
     * @param redisTemplate
     */
    public RedisDistributedLockClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public DistributedLock getLock(String name) {
        return new RedisDistributedLock(redisTemplate, name);
    }
}
