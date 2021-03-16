package juice.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ricky Fung
 */
@ConfigurationProperties(prefix = "juice.ratelimiter")
public class RateLimiterConfigProperties {

    private String enable = "true";

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
