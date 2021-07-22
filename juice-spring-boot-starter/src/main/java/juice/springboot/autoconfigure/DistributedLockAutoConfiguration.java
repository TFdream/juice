package juice.springboot.autoconfigure;

import juice.lock.DistributedLockManager;
import juice.lock.redis.RedisDistributedLockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Ricky Fung
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnClass({DistributedLockManager.class, StringRedisTemplate.class})
public class DistributedLockAutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnMissingBean(name = "distributedLockClient")
    public DistributedLockManager distributedLockClient(StringRedisTemplate stringRedisTemplate) {
        LOG.info("[Spring-Boot自动装配] 分布式锁模块初始化开始, redisTemplate={}", stringRedisTemplate);
        return new RedisDistributedLockManager(stringRedisTemplate);
    }

}
