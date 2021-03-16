package juice.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ricky Fung
 */
@ConfigurationProperties(prefix = "juice.s3")
public class AmazonS3ConfigProperties {
    private String enable;

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String protocol = "HTTP";

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
