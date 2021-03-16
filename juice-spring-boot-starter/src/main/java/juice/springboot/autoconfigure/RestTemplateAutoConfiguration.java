package juice.springboot.autoconfigure;

import juice.springboot.properties.RestTemplateConfigProperties;
import juice.tracing.annotation.DistributedTracing;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnClass({OkHttpClient.class, RestTemplate.class})
@EnableConfigurationProperties(RestTemplateConfigProperties.class)
public class RestTemplateAutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplateConfigProperties restTemplateConfigProperties;

    @DistributedTracing
    @Bean
    @ConditionalOnMissingBean(name = "restTemplate")
    public RestTemplate restTemplate() {
        LOG.info("[Spring-Boot自动装配] RestTemplate模块初始化开始");
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory(okHttpClient(restTemplateConfigProperties)));
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        //1.解决中文乱码
        messageConverters.set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    private OkHttpClient okHttpClient(RestTemplateConfigProperties properties) {
        Integer readTimeoutMillis = properties.getReadTimeout();
        Integer writeTimeoutMillis = properties.getWriteTimeout();
        Integer connectTimeoutMillis = properties.getConnectTimeout();

        LOG.info("[Spring-Boot自动装配] OkHttp模块初始化开始, readTimeoutMillis:{}, writeTimeoutMillis:{},connectTimeoutMillis:{}",
                readTimeoutMillis, writeTimeoutMillis, connectTimeoutMillis);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeoutMillis, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS)
                .build();
        return client;
    }

    private OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory(OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        return factory;
    }

}
