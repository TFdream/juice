package juice.springboot.autoconfigure;

import juice.commons.Splitter;
import juice.core.util.JsonUtils;
import juice.springboot.properties.TracingInterceptProperties;
import juice.tracing.TracingContext;
import juice.tracing.annotation.DistributedTracing;
import juice.tracing.filter.TracingFilter;
import juice.tracing.spring.RestTemplateTracingInterceptor;
import juice.util.CollectionUtils;
import juice.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ricky Fung
 */
@Configuration
@ConditionalOnClass({RestTemplate.class, TracingFilter.class, TracingContext.class})
@EnableConfigurationProperties(TracingInterceptProperties.class)
public class TracingAutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TracingInterceptProperties tracingInterceptProperties;

    @DistributedTracing
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    @ConditionalOnMissingBean(name = "tracingRestTemplateInitializer")
    public SmartInitializingSingleton tracingRestTemplateInitializer(
            final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers) {
        LOG.info("[Spring-Boot自动装配] Tracing模块-注入拦截器开始");
        return () -> restTemplateCustomizers.ifAvailable(customizers -> {
            for (RestTemplate restTemplate : TracingAutoConfiguration.this.restTemplates) {
                for (RestTemplateCustomizer customizer : customizers) {
                    customizer.customize(restTemplate);
                }
            }
        });
    }

    @Bean
    @ConditionalOnMissingBean(name = "_restTemplateCustomizer")
    public RestTemplateCustomizer _restTemplateCustomizer(
            final RestTemplateTracingInterceptor restTemplateTracingInterceptor) {
        LOG.info("[Spring-Boot自动装配] Tracing模块-拦截器初始化开始, restTemplateTracingInterceptor:{}", restTemplateTracingInterceptor);
        return restTemplate -> {
            List<ClientHttpRequestInterceptor> list = new ArrayList<>(
                    restTemplate.getInterceptors());
            list.add(restTemplateTracingInterceptor);
            restTemplate.setInterceptors(list);
        };
    }

    @Bean
    @ConditionalOnMissingBean(name = "restTemplateTracingInterceptor")
    public RestTemplateTracingInterceptor restTemplateTracingInterceptor() {
        return new RestTemplateTracingInterceptor();
    }

    //===========
    @Bean
    @ConditionalOnMissingBean(name = "_tracingFilterRegistration")
    @ConditionalOnProperty(name = "juice.tracing.enable", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean _tracingFilterRegistration() {
        LOG.info("[Spring-Boot自动装配] Tracing模块初始化开始, 配置参数:{}", JsonUtils.toJson(tracingInterceptProperties));

        String pathPatterns = tracingInterceptProperties.getPathPatterns();
        Integer order = tracingInterceptProperties.getOrder();

        //默认拦截所有请求
        List<String> urlPatterns = Collections.EMPTY_LIST;
        if (StringUtils.isNotEmpty(pathPatterns)) {
            Splitter splitter = new Splitter.Builder().on(",").trimResults().omitEmptyStrings().build();
            urlPatterns = splitter.splitToList(pathPatterns);
        }
        if (CollectionUtils.isEmpty(urlPatterns)) {
            urlPatterns = Arrays.asList("/*");
        }

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TracingFilter());
        registrationBean.setUrlPatterns(urlPatterns);
        //增加参数
        if (StringUtils.isNotEmpty(tracingInterceptProperties.getExcludePathPatterns())) {
            registrationBean.addInitParameter(TracingFilter.EXCLUDE_PATTERNS_NAME, tracingInterceptProperties.getExcludePathPatterns());
        }
        if (StringUtils.isNotEmpty(tracingInterceptProperties.getSlowApiEnable())) {
            registrationBean.addInitParameter(TracingFilter.SLOW_API_NAME, tracingInterceptProperties.getSlowApiEnable());
        }
        if (StringUtils.isNotEmpty(tracingInterceptProperties.getCostTimeThreshold())) {
            registrationBean.addInitParameter(TracingFilter.TIME_THRESHOLD_NAME, tracingInterceptProperties.getCostTimeThreshold());
        }
        registrationBean.setOrder(order); // 过滤器的优先级

        LOG.info("[Spring-Boot自动装配] Tracing模块初始化结束, urlPatterns:{}, order:{}", urlPatterns, order);
        return registrationBean;
    }

}
