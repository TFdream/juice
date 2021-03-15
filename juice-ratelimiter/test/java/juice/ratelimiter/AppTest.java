package juice.ratelimiter;

import juice.ratelimiter.internal.AtomicRateLimiter;
import juice.ratelimiter.internal.SemaphoreBasedRateLimiter;
import org.junit.Test;

import java.time.Duration;

/**
 * @author Ricky Fung
 */
public class AppTest {

    private RateLimiter semaphoreBasedRateLimiter;
    private AtomicRateLimiter atomicRateLimiter;

    @Test
    public void setUp() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(Integer.MAX_VALUE)
                .limitRefreshPeriod(Duration.ofNanos(10))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
        semaphoreBasedRateLimiter = new SemaphoreBasedRateLimiter("semaphoreBased",
                rateLimiterConfig);
        atomicRateLimiter = new AtomicRateLimiter("atomicBased", rateLimiterConfig);
    }
}
