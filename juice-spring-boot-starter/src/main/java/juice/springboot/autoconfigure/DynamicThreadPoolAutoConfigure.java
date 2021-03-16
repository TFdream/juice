package juice.springboot.autoconfigure;

import com.ctrip.framework.apollo.ConfigService;
import juice.threadpool.DynamicThreadPoolExecutor;
import juice.threadpool.DynamicThreadPoolExecutorFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
@ConditionalOnClass({DynamicThreadPoolExecutor.class, ConfigService.class})
public class DynamicThreadPoolAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(name = "dynamicThreadPoolExecutor")
    @ConditionalOnProperty(name = "juice.thread-pool.enable", havingValue = "true")
    @ConfigurationProperties(prefix = "juice.thread-pool")
    public DynamicThreadPoolExecutorFactoryBean dynamicThreadPoolExecutor() {
        return new DynamicThreadPoolExecutorFactoryBean();
    }

}
