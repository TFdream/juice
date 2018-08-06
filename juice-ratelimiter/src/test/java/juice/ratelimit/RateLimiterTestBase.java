package juice.ratelimit;

import juice.redis.RedisTemplate;
import org.junit.After;
import org.junit.Before;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * @author Ricky Fung
 */
public class RateLimiterTestBase {
    protected RedisTemplate redisTemplate;
    protected RateLimiterClient client;

    @Before
    public void init() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 5000, "root");
//        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//        jedisClusterNodes.add(new HostAndPort("127.0.0.1", 7379));
//        JedisCluster jc = new JedisCluster(jedisClusterNodes);
        redisTemplate = new RedisTemplate(pool);
        client = new RateLimiterClient(redisTemplate);
    }

    @After
    public void destroy() throws IOException {
        redisTemplate.close();
    }
}
