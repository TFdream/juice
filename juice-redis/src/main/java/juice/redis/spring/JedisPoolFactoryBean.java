package juice.redis.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Ricky Fung
 */
public class JedisPoolFactoryBean implements FactoryBean<JedisPool>, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int DEFAULT_PORT = 6379;
    public static final int DEFAULT_TIMEOUT = 2000;

    private String host;
    private Integer port;
    private Integer timeout;
    private String password;

    /** pool config **/
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean testWhileIdle;

    private JedisPool pool;

    @Override
    public JedisPool getObject() throws Exception {
        logger.info("jedis init host:{} port:{} timeout:{} password:{}", host, port, timeout, password);
        if (port==null) {
            port = DEFAULT_PORT;
        }
        if (timeout==null) {
            timeout = DEFAULT_TIMEOUT;
        }
        logger.info("jedis pool init maxTotal:{} maxIdle:{} minIdle:{}", maxTotal, maxIdle, minIdle);
        //pool config
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        if (maxTotal!=null) {
            poolConfig.setMaxTotal(maxTotal);
        }
        if (maxIdle!=null) {
            poolConfig.setMaxIdle(maxIdle);
        }
        if (minIdle!=null) {
            poolConfig.setMinIdle(minIdle);
        }

        poolConfig.setTestOnBorrow(testOnBorrow == null ? Boolean.FALSE : testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn == null ? Boolean.FALSE : testOnReturn);
        poolConfig.setTestWhileIdle(testWhileIdle == null ? Boolean.TRUE : testWhileIdle);

        this.pool = new JedisPool(poolConfig, host, port, timeout, password);
        return pool;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisPool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("jedis pool destroy");
        pool.destroy();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
}
