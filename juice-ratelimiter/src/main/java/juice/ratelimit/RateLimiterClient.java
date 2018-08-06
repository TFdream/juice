package juice.ratelimit;

import juice.ratelimit.internal.RedisRateLimiter;
import juice.redis.RedisTemplate;

/**
 * @author Ricky Fung
 */
public class RateLimiterClient {
    private final RedisTemplate redisTemplate;

    public RateLimiterClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RateLimiter buildRateLimiter(String redisKeyPrefix, int replenishRate) {
        return new RedisRateLimiter(redisTemplate, redisKeyPrefix, replenishRate);
    }
    public RateLimiter buildRateLimiter(String redisKeyPrefix, int replenishRate, int burstCapacity) {
        return new RedisRateLimiter(redisTemplate, redisKeyPrefix, replenishRate, burstCapacity);
    }
}
