package juice.ratelimiter;

import java.time.Duration;

/**
 * @author Ricky Fung
 */
public interface RateLimiter {

    /**
     * Acquires a permit from this rate limiter, blocking until one is available, or the thread
     * is interrupted. Maximum wait time is {@link RateLimiterConfig#getTimeoutDuration()}
     *
     * <p>If the current thread is {@linkplain Thread#interrupt interrupted}
     * while waiting for a permit then it won't throw {@linkplain InterruptedException}, but its
     * interrupt status will be set.
     *
     * @return {@code true} if a permit was acquired and {@code false} if waiting timeoutDuration
     * elapsed before a permit was acquired
     */
    default boolean tryAcquire() {
        return tryAcquire(1);
    }

    boolean tryAcquire(int permits);

    /**
     * Dynamic rate limiter configuration change. This method allows to change timeout duration of
     * current limiter. NOTE! New timeout duration won't affect threads that are currently waiting
     * for permission.
     *
     * @param timeoutDuration new timeout duration
     */
    void changeTimeoutDuration(Duration timeoutDuration);

    /**
     * Dynamic rate limiter configuration change. This method allows to change count of permissions
     * available during refresh period. NOTE! New limit won't affect current period permissions and
     * will apply only from next one.
     *
     * @param limitForPeriod new permissions limit
     */
    void changeLimitForPeriod(int limitForPeriod);

    String getName();

    RateLimiterConfig getRateLimiterConfig();
}
