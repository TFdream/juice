package juice.ratelimiter;

import juice.contracts.ResultDTO;
import juice.ratelimiter.internal.RedisRateLimiter;
import juice.ratelimiter.internal.SemaphoreBasedRateLimiter;
import org.junit.Before;
import org.junit.Ignore;
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
                .limitForPeriod(5)
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
        semaphoreBasedRateLimiter = new SemaphoreBasedRateLimiter("semaphoreBased",
                rateLimiterConfig);
        redisRateLimiter = new RedisRateLimiter("redis", rateLimiterConfig);
    }

    @Test
    public void testApp() {
        System.out.println("test");
    }

    public ResultDTO secKill() {
        boolean success = redisRateLimiter.tryAcquire();
        if (!success) {
            return ResultDTO.invalidParam("业务繁忙");
        }
        //正常业务处理

        return ResultDTO.ok();
    }
}
