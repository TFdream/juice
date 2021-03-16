package juice.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ricky Fung
 */
@ConfigurationProperties(prefix = "juice.tracing")
public class TracingInterceptProperties {
    /**
     * 开启追踪
     */
    private String enable = "true";

    /**
     * 拦截请求URI规则, 多个用逗号分隔
     * 例如: /*  拦截所有请求
     *      /api/*  拦截以/api/打头的请求
     */
    private String pathPatterns = "/*";

    /**
     * 排除
     */
    private String excludePathPatterns;

    /**
     * 过滤器的优先级
     */
    private Integer order = 1;

    //=======慢查询
    private String slowApiEnable;
    private String costTimeThreshold;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(String pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public String getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(String excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSlowApiEnable() {
        return slowApiEnable;
    }

    public void setSlowApiEnable(String slowApiEnable) {
        this.slowApiEnable = slowApiEnable;
    }

    public String getCostTimeThreshold() {
        return costTimeThreshold;
    }

    public void setCostTimeThreshold(String costTimeThreshold) {
        this.costTimeThreshold = costTimeThreshold;
    }
}
