package juice.ratelimiter;

import juice.commons.Assertions;

import java.time.Duration;

/**
 * @author Ricky Fung
 */
public class RateLimiterConfig {
    private final Duration timeoutDuration;
    private final Duration limitRefreshPeriod;
    private final int limitForPeriod;

    private RateLimiterConfig(Duration timeoutDuration,
                              Duration limitRefreshPeriod,
                              int limitForPeriod) {
        this.timeoutDuration = timeoutDuration;
        this.limitRefreshPeriod = limitRefreshPeriod;
        this.limitForPeriod = limitForPeriod;
    }

    public Duration getTimeoutDuration() {
        return timeoutDuration;
    }

    public Duration getLimitRefreshPeriod() {
        return limitRefreshPeriod;
    }

    public int getLimitForPeriod() {
        return limitForPeriod;
    }

    //===========
    public static Builder custom() {
        return new Builder();
    }

    /**
     * Returns a builder to create a custom RateLimiterConfig using specified config as prototype
     *
     * @param prototype A {@link RateLimiterConfig} prototype.
     * @return a {@link RateLimiterConfig.Builder}
     */
    public static Builder from(RateLimiterConfig prototype) {
        return new Builder(prototype);
    }

    public static RateLimiterConfig ofDefaults() {
        return new Builder().build();
    }

    public static class Builder {
        private Duration timeoutDuration = Duration.ofSeconds(5);
        private Duration limitRefreshPeriod = Duration.ofNanos(500);
        private int limitForPeriod = 50;

        public Builder() {
        }

        public Builder(RateLimiterConfig prototype) {
            this.timeoutDuration = prototype.timeoutDuration;
            this.limitRefreshPeriod = prototype.limitRefreshPeriod;
            this.limitForPeriod = prototype.limitForPeriod;
        }

        public Builder timeoutDuration(final Duration timeoutDuration) {
            Assertions.notNull(timeoutDuration, "timeoutDuration is NULL");
            this.timeoutDuration = timeoutDuration;
            return this;
        }

        public Builder limitRefreshPeriod(final Duration limitRefreshPeriod) {
            Assertions.notNull(limitRefreshPeriod, "limitRefreshPeriod is NULL");
            this.limitRefreshPeriod = limitRefreshPeriod;
            return this;
        }

        public Builder limitForPeriod(final int limitForPeriod) {
            Assertions.checkNonNegative(limitForPeriod, "limitForPeriod");
            this.limitForPeriod = limitForPeriod;
            return this;
        }

        /**
         * Builds a RateLimiterConfig
         *
         * @return the RateLimiterConfig
         */
        public RateLimiterConfig build() {
            return new RateLimiterConfig(timeoutDuration, limitRefreshPeriod, limitForPeriod);
        }

    }
}