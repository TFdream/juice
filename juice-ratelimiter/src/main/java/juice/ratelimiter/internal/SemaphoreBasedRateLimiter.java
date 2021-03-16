package juice.ratelimiter.internal;

import juice.ratelimiter.RateLimiter;
import juice.ratelimiter.RateLimiterConfig;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ricky Fung
 */
public class SemaphoreBasedRateLimiter implements RateLimiter {
    private final String name;
    private final AtomicReference<RateLimiterConfig> rateLimiterConfig;

    private final ScheduledExecutorService scheduler;
    private final Semaphore semaphore;

    public SemaphoreBasedRateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        this(name, rateLimiterConfig, null);
    }

    public SemaphoreBasedRateLimiter(String name, RateLimiterConfig rateLimiterConfig, ScheduledExecutorService scheduler) {
        this.name = name;
        this.rateLimiterConfig = new AtomicReference<>(rateLimiterConfig);

        this.semaphore = new Semaphore(this.rateLimiterConfig.get().getLimitForPeriod(), true);
        this.scheduler = Optional.ofNullable(scheduler).orElseGet(this::configureScheduler);
        //启动定时器
        scheduleLimitRefresh();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public RateLimiterConfig getRateLimiterConfig() {
        return this.rateLimiterConfig.get();
    }

    @Override
    public boolean tryAcquire(int permits) {
        return semaphore.tryAcquire(permits);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeTimeoutDuration(Duration timeoutDuration) {
        RateLimiterConfig newConfig = RateLimiterConfig.from(rateLimiterConfig.get())
                .timeoutDuration(timeoutDuration)
                .build();
        rateLimiterConfig.set(newConfig);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeLimitForPeriod(int limitForPeriod) {
        RateLimiterConfig newConfig = RateLimiterConfig.from(rateLimiterConfig.get())
                .limitForPeriod(limitForPeriod)
                .build();
        rateLimiterConfig.set(newConfig);
    }

    //=========
    private ScheduledExecutorService configureScheduler() {
        ThreadFactory threadFactory = target -> {
            Thread thread = new Thread(target, "SchedulerForSemaphoreBasedRateLimiterImpl-" + name);
            thread.setDaemon(true);
            return thread;
        };
        return new ScheduledThreadPoolExecutor(1, threadFactory);
    }

    private void scheduleLimitRefresh() {
        scheduler.scheduleAtFixedRate(
                this::refreshLimit,
                this.rateLimiterConfig.get().getLimitRefreshPeriod().toNanos(),
                this.rateLimiterConfig.get().getLimitRefreshPeriod().toNanos(),
                TimeUnit.NANOSECONDS
        );
    }

    void refreshLimit() {
        int permissionsToRelease =
                this.rateLimiterConfig.get().getLimitForPeriod() - semaphore.availablePermits();
        semaphore.release(permissionsToRelease);
    }

}
