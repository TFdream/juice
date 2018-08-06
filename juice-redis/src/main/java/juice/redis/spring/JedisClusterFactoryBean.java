package juice.redis.spring;

import juice.core.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class JedisClusterFactoryBean implements FactoryBean<JedisCluster>, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String address;
    private String password;
    private int timeout;
    private int maxAttempts;
    private JedisPoolConfig poolConfig;

    private JedisCluster jc;

    @Override
    public JedisCluster getObject() throws Exception {

        logger.info("jedis cluster init address:{}, password:{}, timeout:{}, maxAttempts:{}",
                address, password, timeout, maxAttempts);

        AssertUtils.notEmpty(address, "address为空");

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        List<String> list = Arrays.asList(address.split(","));
        logger.info("jedis cluster address list size:{}", list.size());

        for (String node : list) {
            logger.info("add jedis cluster node:{}", node);
            String[] arr = node.split("\\:");
            jedisClusterNodes.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
        }
        jc = new JedisCluster(jedisClusterNodes, timeout, timeout, maxAttempts, password, poolConfig);
        return jc;
    }

    @Override
    public Class<?> getObjectType() {
        return JedisCluster.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("jedis cluster close");
        if (jc!=null) {
            jc.close();
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
