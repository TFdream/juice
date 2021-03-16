package juice.ratelimiter.internal;

import juice.commons.collection.Lists;
import juice.ratelimiter.RateLimiter;
import juice.ratelimiter.RateLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 参考Stripe: https://stripe.com/blog/rate-limiters
 * 限流脚本: https://gist.github.com/ptarjan/e38f45f2dfe601419ca3af937fff574d
 * @author Ricky Fung
 */
public class RedisConcurrentRateLimiter implements RateLimiter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final String name;
    private final AtomicReference<RateLimiterConfig> rateLimiterConfig;

    private final StringRedisTemplate stringRedisTemplate;
    // 限流脚本
    private final DefaultRedisScript<List> limitRedisScript;

    public RedisConcurrentRateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        this(name, rateLimiterConfig, null);
    }

    public RedisConcurrentRateLimiter(String name, RateLimiterConfig rateLimiterConfig, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.rateLimiterConfig = new AtomicReference<>(rateLimiterConfig);
        //
        this.stringRedisTemplate = stringRedisTemplate;
        this.limitRedisScript = new DefaultRedisScript<>();
        this.limitRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/lua/juice/concurrent_requests_limiter.lua")));
        this.limitRedisScript.setResultType(List.class);
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
        throw new UnsupportedOperationException("不支持的API");
    }

    public boolean tryAcquire(String id) {
        String key = getRedisKey();

        int capacity = this.getRateLimiterConfig().getLimitForPeriod();
        int ttl = this.getRateLimiterConfig().getLimitForPeriod();

        //unixtime in seconds.
        long timestamp = Instant.now().getEpochSecond();
        //# Clear out old requests that probably got lost
        double maxScore = timestamp - ttl;

        List<String> keys = Lists.asList(key);
        List<Long> list = stringRedisTemplate.execute(limitRedisScript,
                keys, String.valueOf(capacity), String.valueOf(timestamp), String.valueOf(id), String.valueOf(maxScore));
        if (LOG.isDebugEnabled()) {
            LOG.debug("分布式限流-并发数限流器, name={}, capacity={}, id={} 操作执行结果={}",
                    getName(), capacity, id, list);
        }
        return list.get(0) > 0;
    }

    public Long release(String id) {
        String key = getRedisKey();
        return stringRedisTemplate.opsForZSet().remove(key, id);
    }

    //==========

    @Override
    public void changeTimeoutDuration(Duration timeoutDuration) {
        RateLimiterConfig newConfig = RateLimiterConfig.from(rateLimiterConfig.get())
                .timeoutDuration(timeoutDuration)
                .build();
        rateLimiterConfig.set(newConfig);
    }

    @Override
    public void changeLimitForPeriod(int limitForPeriod) {
        RateLimiterConfig newConfig = RateLimiterConfig.from(rateLimiterConfig.get())
                .limitForPeriod(limitForPeriod)
                .build();
        rateLimiterConfig.set(newConfig);
    }

    //=========
    private String getRedisKey() {
        return String.format("%s.concurrent", getName());
    }
}
