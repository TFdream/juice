package juice.springboot.autoconfigure;

import com.amazonaws.services.s3.AmazonS3;
import juice.amazonaws.s3.AmazonS3Builder;
import juice.amazonaws.s3.AmazonS3Template;
import juice.core.util.JsonUtils;
import juice.springboot.properties.AmazonS3ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ricky Fung
 */
@Configuration
@ConditionalOnClass({AmazonS3Template.class, AmazonS3.class})
@EnableConfigurationProperties(AmazonS3ConfigProperties.class)
public class AmazonS3AutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmazonS3ConfigProperties properties;

    @Bean
    @ConditionalOnMissingBean(name = "amazonS3Template")
    @ConditionalOnProperty(name = "juice.s3.enable", havingValue = "true")
    public AmazonS3Template amazonS3Template() {
        LOG.info("[Spring-Boot自动装配]- [AmazonS3], AmazonS3Template 注入开始, 参数={}", JsonUtils.toJson(properties));
        AmazonS3Template amazonS3Template = new AmazonS3Template(amazonS3());
        return amazonS3Template;
    }

    @Bean
    @ConditionalOnMissingBean(name = "amazonS3")
    @ConditionalOnProperty(name = "juice.s3.enable", havingValue = "true")
    public AmazonS3 amazonS3() {
        LOG.info("[Spring-Boot自动装配]- [AmazonS3], AmazonS3 注入开始, 参数={}", JsonUtils.toJson(properties));
        AmazonS3 amazonS3 = new AmazonS3Builder()
                .endpoint(properties.getEndpoint())
                .accessKey(properties.getAccessKey())
                .secretKey(properties.getSecretKey())
                .protocol(properties.getProtocol())
                .build();

        return amazonS3;
    }

}
