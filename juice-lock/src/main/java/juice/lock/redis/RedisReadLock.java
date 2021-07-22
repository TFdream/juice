package juice.lock.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class RedisReadLock extends AbstractRedisLock {

    RedisReadLock(StringRedisTemplate redisTemplate, String name) {
        super(redisTemplate, name);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        return false;
    }

    @Override
    public void unlock() {

    }
}
