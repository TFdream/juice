package juice.distlock;

import juice.redis.RedisTemplate;
import org.junit.After;
import org.junit.Before;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class LockTestBase {

    protected RedisTemplate redisTemplate;

    protected DefaultLockClient client;

    @Before
    public void init() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6781, 5000, "test");
//        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
//        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        redisTemplate = new RedisTemplate(pool);
        client = new DefaultLockClient(redisTemplate);
    }

    protected void sleep(int timeout) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(timeout);
    }

    @After
    public void destroy() throws IOException {
        redisTemplate.close();
    }
}
