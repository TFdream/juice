package juice.ratelimit.internal;

import juice.ratelimit.RateLimiter;
import juice.ratelimit.Response;
import juice.redis.RedisTemplate;
import juice.core.util.TextUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class RedisRateLimiter implements RateLimiter {
    private static final String PREFIX = "META-INF/scripts/";

    private RedisTemplate redisTemplate;
    private final String redisKeyPrefix;

    /** How many requests per second do you want a user to be allowed to do? **/
    private final int replenishRate;
    /** How much bursting do you want to allow **/
    private final int burstCapacity;
    /** Lua script**/
    private final String script;

    public RedisRateLimiter(RedisTemplate redisTemplate, String redisKeyPrefix,
                            int replenishRate) {
        this(redisTemplate, redisKeyPrefix, replenishRate, replenishRate);
    }

    public RedisRateLimiter(RedisTemplate redisTemplate, String redisKeyPrefix,
                            int replenishRate, int burstCapacity) {
        this.redisTemplate = redisTemplate;
        this.redisKeyPrefix = redisKeyPrefix;
        this.replenishRate = replenishRate;
        this.burstCapacity = burstCapacity;

        try {
            this.script = TextUtils.readClassPath(PREFIX+"request_rate_limiter.lua");
        } catch (IOException e) {
            throw new RuntimeException("load Redis lua scripts error!", e);
        }
    }

    @Override
    public Response acquire(String id) {
        return acquire(id, 1);
    }

    @Override
    public Response acquire(String id, int permits) {
        // Make a unique key per user.
        String prefix = redisKeyPrefix+".{" + id+"}";

        // You need two Redis keys for Token Bucket.
        List<String> keys = Arrays.asList(prefix + ".tokens", prefix + ".timestamp");
        // The arguments to the LUA script. time() returns unixtime in seconds.
        List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "", System.currentTimeMillis()/1000+ "", permits+"");

        List<Long> results;
        try {
            //执行 Redis Lua 脚本，获取令牌。返回结果为 [是否获取令牌成功, 剩余令牌数] ，其中，1 代表获取令牌成功，0 代表令牌获取失败。
            results = (List<Long>) redisTemplate.eval(script, keys, scriptArgs);
        } catch (Exception e) {
            //当 Redis Lua 脚本过程中发生异常，忽略异常，返回 Arrays.asList(1L, -1L) ，即认为获取令牌成功。
            // 为什么？在 Redis 发生故障时，我们不希望限流器对 Reids 是强依赖，并且 Redis 发生故障的概率本身就很低。
            results = Arrays.asList(1L, -1L);
        }
        boolean allowed = results.get(0) == 1L;
        Long tokensLeft = results.get(1);
        Response response = new Response(allowed, tokensLeft);
        return response;
    }
}
