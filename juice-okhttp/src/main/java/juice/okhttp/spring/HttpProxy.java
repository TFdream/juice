package juice.okhttp.spring;

/**
 * @author Ricky Fung
 * @version 1.0
 * @since 2018-06-28 00:01
 */
public class HttpProxy {
    private String hostname;
    private Integer port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
