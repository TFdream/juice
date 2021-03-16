package juice.springboot.autoconfigure;

import juice.core.util.JsonUtils;
import juice.ratelimiter.RateLimiter;
import juice.ratelimiter.RateLimiterConfig;
import juice.ratelimiter.internal.RedisRateLimiter;
import juice.springboot.properties.RateLimiterConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author Ricky Fung
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnClass({RateLimiter.class, StringRedisTemplate.class})
public class RateLimiterAutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RateLimiterConfigProperties properties;

    @Bean
    @ConditionalOnProperty(name = "juice.ratelimiter.enable", havingValue = "true")
    @ConditionalOnMissingBean(name = "redisRateLimiter")
    public RedisRateLimiter redisRateLimiter(StringRedisTemplate stringRedisTemplate) {
        LOG.info("[Spring-Boot自动装配] 限流模块初始化开始, properties={}", JsonUtils.toJson(properties));
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.ofDefaults();
        return new RedisRateLimiter("redis", rateLimiterConfig, stringRedisTemplate);
    }

}
