package juice.ratelimiter;

import juice.contracts.ResultDTO;
import juice.ratelimiter.internal.RedisRateLimiter;
import juice.ratelimiter.internal.SemaphoreBasedRateLimiter;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

/**
 * @author Ricky Fung
 */
public class AppTest {

    private RateLimiter semaphoreBasedRateLimiter;
    private RedisRateLimiter redisRateLimiter;

    @Before
    public void setUp() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(Integer.MAX_VALUE)
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
        semaphoreBasedRateLimiter = new SemaphoreBasedRateLimiter("semaphoreBased",
                rateLimiterConfig);
        redisRateLimiter = new RedisRateLimiter("redis", rateLimiterConfig);
    }

    @Test
    public ResultDTO secKill() {
        boolean success = redisRateLimiter.tryAcquire();
        if (!success) {
            return ResultDTO.invalidParam("业务繁忙");
        }
        //正常业务处理

        return ResultDTO.ok();
    }
}
