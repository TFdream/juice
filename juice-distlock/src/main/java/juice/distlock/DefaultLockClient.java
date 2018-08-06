package juice.distlock;

import juice.distlock.internal.redis.RedisDistLock;
import juice.redis.RedisTemplate;
import juice.core.util.UUIDUtils;

/**
 * @author Ricky Fung
 */
public class DefaultLockClient implements LockClient {
    private final RedisTemplate redisTemplate;

    public DefaultLockClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public DLock getLock(String name) {
        return new RedisDistLock(UUIDUtils.getTrimId(), name, redisTemplate);
    }
}