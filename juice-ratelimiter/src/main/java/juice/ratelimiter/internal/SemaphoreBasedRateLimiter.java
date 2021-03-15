package juice.ratelimiter.internal;

import juice.ratelimiter.RateLimiterConfig;

import java.time.Duration;

/**
 * @author Ricky Fung
 */
public class SemaphoreBasedRateLimiter extends AbstractRateLimiter {

    public SemaphoreBasedRateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        super(name, rateLimiterConfig);
    }

    @Override
    public boolean tryAcquire(int permits) {
        return false;
    }

    @Override
    public void changeTimeoutDuration(Duration timeoutDuration) {

    }

    @Override
    public void changeLimitForPeriod(int limitForPeriod) {

    }
}
