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
public class RedisRateLimiter implements RateLimiter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final String name;
    private final AtomicReference<RateLimiterConfig> rateLimiterConfig;

    private final StringRedisTemplate stringRedisTemplate;
    // 限流脚本
    private final DefaultRedisScript<List> limitRedisScript;

    public RedisRateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        this(name, rateLimiterConfig, null);
    }

    public RedisRateLimiter(String name, RateLimiterConfig rateLimiterConfig, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.rateLimiterConfig = new AtomicReference<>(rateLimiterConfig);
        //
        this.stringRedisTemplate = stringRedisTemplate;
        this.limitRedisScript = new DefaultRedisScript<>();
        this.limitRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/lua/juice/request_rate_limiter.lua")));
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
        String tokensKey = String.format("%s.tokens", getName());
        String timestampKey = String.format("%s.timestamp", getName());
        List<String> keys = Lists.asList(tokensKey, timestampKey);

        //# how many tokens per second in token-bucket algorithm.
        int replenishRate = this.getRateLimiterConfig().getLimitForPeriod();
        //# how many tokens the bucket can hold in token-bucket
        int burstCapacity = replenishRate;
        //unixtime in seconds.
        long now = Instant.now().getEpochSecond();

        List<Long> list = stringRedisTemplate.execute(limitRedisScript,
                keys, String.valueOf(replenishRate), String.valueOf(burstCapacity), String.valueOf(now), String.valueOf(1));
        if (LOG.isDebugEnabled()) {
            LOG.debug("分布式限流-申请资源, name={}, replenishRate={}, burstCapacity={} 申请资源操作执行结果={}",
                    getName(), replenishRate, burstCapacity, list);
        }
        return list.get(0) > 0;
    }

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
}
