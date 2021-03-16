package juice.springboot.properties;

import juice.util.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ricky Fung
 */
@ConfigurationProperties(prefix = "juice.ftp")
public class FtpClientConfigProperties {

    private String enable;

    private String host = StringUtils.EMPTY;

    private int port = 21;

    private String username = StringUtils.EMPTY;

    private String password = StringUtils.EMPTY;

    private int connectTimeout = 3000;
    private int socketTimeout = 10 * 1000;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
