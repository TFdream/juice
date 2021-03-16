package juice.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ricky Fung
 */
@ConfigurationProperties(prefix = "juice.http")
public class RestTemplateConfigProperties {
    /**
     * 单位: 毫秒
     */
    private Integer readTimeout = 3000;
    private Integer writeTimeout = 3000;
    private Integer connectTimeout = 3000;

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
