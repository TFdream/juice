package juice.ratelimiter.internal;

import juice.ratelimiter.RateLimiter;
import juice.ratelimiter.RateLimiterConfig;

/**
 * @author Ricky Fung
 */
public abstract class AbstractRateLimiter implements RateLimiter {
    private final long nanoTimeStart;
    private final String name;
    private final RateLimiterConfig rateLimiterConfig;

    public AbstractRateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        this.name = name;
        this.rateLimiterConfig = rateLimiterConfig;
        this.nanoTimeStart = nanoTime();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RateLimiterConfig getRateLimiterConfig() {
        return rateLimiterConfig;
    }

    public long getNanoTimeStart() {
        return nanoTimeStart;
    }

    /**
     * Calculates time elapsed from the class loading.
     */
    protected long currentNanoTime() {
        return nanoTime() - nanoTimeStart;
    }

    protected long nanoTime() {
        return System.nanoTime();
    }
}
