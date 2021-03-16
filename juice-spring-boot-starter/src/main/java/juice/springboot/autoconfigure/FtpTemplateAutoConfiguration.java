package juice.springboot.autoconfigure;

import juice.core.util.JsonUtils;
import juice.ftp.FtpTemplate;
import juice.springboot.properties.FtpClientConfigProperties;
import org.apache.commons.net.ftp.FTPClient;
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
@ConditionalOnClass({FtpTemplate.class, FTPClient.class})
@EnableConfigurationProperties(FtpClientConfigProperties.class)
public class FtpTemplateAutoConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FtpClientConfigProperties properties;

    @Bean
    @ConditionalOnMissingBean(FtpTemplate.class)
    @ConditionalOnProperty(name = "juice.ftp.enable", havingValue = "true")
    public FtpTemplate ftpTemplate() {
        LOG.info("[Spring-Boot自动装配] - FtpTemplate 初始化开始, 参数:{}", JsonUtils.toJson(properties));
        FtpTemplate ftpTemplate = new FtpTemplate(properties.getHost(), properties.getPort(),
                properties.getUsername(), properties.getPassword(),
                properties.getConnectTimeout(), properties.getSocketTimeout());
        return ftpTemplate;
    }
}
