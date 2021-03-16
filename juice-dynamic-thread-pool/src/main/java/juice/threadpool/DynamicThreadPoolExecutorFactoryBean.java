package juice.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Ricky Fung
 */
public class DynamicThreadPoolExecutorFactoryBean implements FactoryBean<DynamicThreadPoolExecutor>, InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * 配置中心namespace
     */
    private String namespace;
    /**
     * 配置中心key前缀
     */
    private String keyPrefix;

    private DynamicThreadPoolExecutor threadPoolExecutor;

    @Override
    public DynamicThreadPoolExecutor getObject() throws Exception {
        if (this.threadPoolExecutor == null) {
            afterPropertiesSet();
        }
        return this.threadPoolExecutor;
    }

    @Override
    public Class<?> getObjectType() {
        return DynamicThreadPoolExecutor.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("动态线程池-自动装配开始, namespace:{}, keyPrefix:{}", namespace, keyPrefix);

        Assert.notNull(namespace, "namespace为空");
        Assert.notNull(keyPrefix, "keyPrefix为空");

        this.threadPoolExecutor = new DynamicThreadPoolExecutor(namespace, keyPrefix);
    }

    //==========

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
