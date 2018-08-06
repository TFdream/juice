package juice.redis;

import juice.redis.exceptions.RedisStoreException;
import juice.redis.internal.RedisCommandExecutor;
import juice.redis.internal.impl.RedisClusterCommandExecutor;
import juice.redis.internal.impl.RedisSingleCommandExecutor;
import juice.redis.serialization.DefaultSerializer;
import juice.redis.serialization.Serializer;
import juice.core.util.StringUtils;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class RedisTemplate {
    private final RedisCommandExecutor redisCommandExecutor;
    private final Serializer serializer;

    public RedisTemplate(JedisPool jp) {
        this(new RedisSingleCommandExecutor(jp), new DefaultSerializer());
    }
    public RedisTemplate(JedisPool jp, Serializer serializer) {
        this(new RedisSingleCommandExecutor(jp), serializer);
    }

    public RedisTemplate(JedisCluster jc) {
        this(new RedisClusterCommandExecutor(jc), new DefaultSerializer());
    }
    public RedisTemplate(JedisCluster jc, Serializer serializer) {
        this(new RedisClusterCommandExecutor(jc), serializer);
    }

    public RedisTemplate(RedisCommandExecutor redisCommandExecutor, Serializer serializer) {
        this.redisCommandExecutor = redisCommandExecutor;
        this.serializer = serializer;
    }

    public Long set(String key, String value) {
        return redisCommandExecutor.set(key, value);
    }
    public Long set(final byte[] key, final byte[] value) {
        return redisCommandExecutor.set(key, value);
    }
    /** set object **/
    public Long set(String key, Object value) throws RedisStoreException {
        try {
            return redisCommandExecutor.set(StringUtils.stringToByteArray(key), serializer.serialize(value));
        } catch (IOException e) {
            throw new RedisStoreException(String.format("set object key: %s caught error", key), e);
        }
    }

    public Long setnx(String key, String value) {
        return redisCommandExecutor.setnx(key, value);
    }
    public Long setnx(final byte[] key, final byte[] value) {
        return redisCommandExecutor.setnx(key, value);
    }

    public Long setex(final String key, int seconds, String value) {
        return redisCommandExecutor.setex(key, seconds, value);
    }
    public Long setex(final byte[] key, int seconds, final byte[] value) {
        return redisCommandExecutor.setex(key, seconds, value);
    }

    public Long set(String key, String value, String nxxx, String expx, long time) {
        return redisCommandExecutor.set(key, value, nxxx, expx, time);
    }
    public Long set(final byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return redisCommandExecutor.set(key, value, nxxx, expx, time);
    }

    public String get(String key) {
        return redisCommandExecutor.get(key);
    }
    public byte[] get(final byte[] key) {
        return redisCommandExecutor.get(key);
    }

    /** get object **/
    public <T> T get(final String key, Class<T> classOfT) throws RedisStoreException {
        byte[] data = redisCommandExecutor.get(StringUtils.stringToByteArray(key));
        if (data!=null && data.length>0) {
            try {
                return serializer.deserialize(data, classOfT);
            } catch (IOException e) {
                throw new RedisStoreException(String.format("get object key: %s caught error", key), e);
            }
        }
        return null;
    }

    public boolean exists(String key) {
        return redisCommandExecutor.exists(key);
    }
    public boolean exists(final byte[] key) {
        return redisCommandExecutor.exists(key);
    }

    public Long exists(final String... keys) {
        return redisCommandExecutor.exists(keys);
    }
    public Long exists(final byte[]... keys) {
        return redisCommandExecutor.exists(keys);
    }

    public Long del(String key) {
        return redisCommandExecutor.del(key);
    }
    public Long del(byte[] key) {
        return redisCommandExecutor.del(key);
    }

    public Long del(final String... keys) {
        return redisCommandExecutor.del(keys);
    }
    public Long del(final byte[]... keys) {
        return redisCommandExecutor.del(keys);
    }

    public Long ttl(String key) {
        return redisCommandExecutor.ttl(key);
    }

    public Long ttl(byte[] key) {
        return redisCommandExecutor.ttl(key);
    }

    public Long pttl(String key) {
        return redisCommandExecutor.pttl(key);
    }

    public Long expire(final String key, int seconds) {
        return redisCommandExecutor.expire(key, seconds);
    }
    public Long expire(final byte[] key, int seconds) {
        return redisCommandExecutor.expire(key, seconds);
    }

    public Long expireAt(final String key, long unixTime) {
        return redisCommandExecutor.expireAt(key, unixTime);
    }
    public Long expireAt(final byte[] key, long unixTime) {
        return redisCommandExecutor.expireAt(key, unixTime);
    }

    public Long pexpire(final String key, final long milliseconds) {
        return redisCommandExecutor.pexpire(key, milliseconds);
    }
    public Long pexpire(final byte[] key, final long milliseconds) {
        return redisCommandExecutor.pexpire(key, milliseconds);
    }

    public Long incr(final String key) {
        return redisCommandExecutor.incr(key);
    }
    public Long incr(final byte[] key) {
        return redisCommandExecutor.incr(key);
    }

    public Long decr(final String key) {
        return redisCommandExecutor.decr(key);
    }
    public Long decr(final byte[] key) {
        return redisCommandExecutor.decr(key);
    }

    public Long incrBy(final String key, long delta) {
        return redisCommandExecutor.incrBy(key, delta);
    }
    public Long incrBy(final byte[] key, long delta) {
        return redisCommandExecutor.incrBy(key, delta);
    }

    public Long decrBy(final String key, long delta) {
        return redisCommandExecutor.decrBy(key, delta);
    }
    public Long decrBy(final byte[] key, long delta) {
        return redisCommandExecutor.decrBy(key, delta);
    }

    /***List**/
    public Long lpush(String key, String... values) {
        return redisCommandExecutor.lpush(key, values);
    }

    public Long rpush(final String key, final String... values) {
        return redisCommandExecutor.rpush(key, values);
    }

    public String lpop(String key) {
        return redisCommandExecutor.lpop(key);
    }

    public String rpop(String key) {
        return redisCommandExecutor.rpop(key);
    }

    public List<String> lrange(String key, long start, long end) {
        return redisCommandExecutor.lrange(key, start, end);
    }

    public String lindex(String key, long index) {
        return redisCommandExecutor.lindex(key, index);
    }

    public Long llen(String key) {
        return redisCommandExecutor.llen(key);
    }

    /**hashmap**/
    public Long hset(String key, String field, String value) {
        return redisCommandExecutor.hset(key, field, value);
    }

    public Long hsetnx(String key, String field, String value) {
        return redisCommandExecutor.hsetnx(key, field, value);
    }

    public String hmset(final String key, final Map<String, String> hash) {
        return redisCommandExecutor.hmset(key, hash);
    }

    public Set<String> hkeys(final String key) {
        return redisCommandExecutor.hkeys(key);
    }

    public List<String> hvals(final String key) {
        return redisCommandExecutor.hvals(key);
    }

    public Long hincrBy(String key, String field, long value) {
        return redisCommandExecutor.hincrBy(key, field, value);
    }

    public Long hdel(final String key, final String... fields) {
        return redisCommandExecutor.hdel(key, fields);
    }

    public Boolean hexists(String key, String field) {
        return redisCommandExecutor.hexists(key, field);
    }

    public Long hlen(final String key) {
        return redisCommandExecutor.hlen(key);
    }

    public String hget(final String key, final String field) {
        return redisCommandExecutor.hget(key, field);
    }

    public List<String> hmget(final String key, final String... fields) {
        return redisCommandExecutor.hmget(key, fields);
    }

    public Map<String, String> hgetAll(String key) {
        return redisCommandExecutor.hgetAll(key);
    }

    /**SET**/
    public Long sadd(String key, String... members) {
        return redisCommandExecutor.sadd(key, members);
    }

    public Set<String> smembers(final String key) {
        return redisCommandExecutor.smembers(key);
    }

    public Long srem(final String key, final String... members) {
        return redisCommandExecutor.srem(key, members);
    }

    public String spop(final String key) {
        return redisCommandExecutor.spop(key);
    }

    public Long scard(final String key) {
        return redisCommandExecutor.scard(key);
    }

    public Boolean sismember(final String key, final String member) {
        return redisCommandExecutor.sismember(key, member);
    }

    public Set<String> sinter(final String... keys) {
        return redisCommandExecutor.sinter(keys);
    }

    public Set<String> sunion(final String... keys) {
        return redisCommandExecutor.sunion(keys);
    }

    public Set<String> sdiff(final String... keys) {
        return redisCommandExecutor.sdiff(keys);
    }

    /** SortedSet **/
    public Long zadd(final String key, final double score, final String member) {
        return redisCommandExecutor.zadd(key, score, member);
    }
    public Long zadd(final byte[] key, final double score, final byte[] member) {
        return redisCommandExecutor.zadd(key, score, member);
    }

    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return redisCommandExecutor.zadd(key, scoreMembers);
    }
    public Long zadd(final byte[] key, final Map<byte[], Double> scoreMembers) {
        return redisCommandExecutor.zadd(key, scoreMembers);
    }

    public Set<String> zrange(final String key, final long start, final long end) {
        return redisCommandExecutor.zrange(key, start, end);
    }
    public Set<byte[]> zrange(final byte[] key, final long start, final long end) {
        return redisCommandExecutor.zrange(key, start, end);
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return redisCommandExecutor.zrangeByScore(key, min, max);
    }
    public Set<byte[]> zrangeByScore(final byte[] key, final double min, final double max) {
        return redisCommandExecutor.zrangeByScore(key, min, max);
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max,
                                     final int offset, final int count) {
        return redisCommandExecutor.zrangeByScore(key, min, max, offset, count);
    }
    public Set<byte[]> zrangeByScore(final byte[] key, final byte[] min, final byte[] max,
                                     final int offset, final int count) {
        return redisCommandExecutor.zrangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
        return redisCommandExecutor.zrangeWithScores(key, start, end);
    }
    public Set<Tuple> zrangeWithScores(final byte[] key, final long start, final long end) {
        return redisCommandExecutor.zrangeWithScores(key, start, end);
    }

    public Set<String> zrevrange(final String key, final long start, final long end) {
        return redisCommandExecutor.zrevrange(key, start, end);
    }
    public Set<byte[]> zrevrange(final byte[] key, final long start, final long end) {
        return redisCommandExecutor.zrevrange(key, start, end);
    }

    public Set<String> zrevrangeByScore(final String key, final double max, final double min,
                                        final int offset, final int count) {
        return redisCommandExecutor.zrevrangeByScore(key, max, min, offset, count);
    }
    public Set<byte[]> zrevrangeByScore(final byte[] key, final double max, final double min,
                                        final int offset, final int count) {
        return redisCommandExecutor.zrevrangeByScore(key, max, min, offset, count);
    }

    public ScanResult<Tuple> zscan(final String key, final String cursor) {
        return redisCommandExecutor.zscan(key, cursor);
    }
    public ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params) {
        return redisCommandExecutor.zscan(key, cursor, params);
    }

    public ScanResult<Tuple> zscan(final byte[] key, final byte[] cursor) {
        return redisCommandExecutor.zscan(key, cursor);
    }
    public ScanResult<Tuple> zscan(final byte[] key, final byte[] cursor, final ScanParams params) {
        return redisCommandExecutor.zscan(key, cursor, params);
    }

    public Set<String> zrangeByLex(final String key, final String min, final String max) {
        return redisCommandExecutor.zrangeByLex(key, min, max);
    }
    public Set<String> zrangeByLex(final String key, final String min, final String max,
                                   final int offset, final int count) {
        return redisCommandExecutor.zrangeByLex(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByLex(final String key, final String max, final String min) {
        return redisCommandExecutor.zrevrangeByLex(key, max, min);
    }
    public Set<String> zrevrangeByLex(final String key, final String max, final String min,
                                      final int offset, final int count) {
        return redisCommandExecutor.zrevrangeByLex(key, max, min, offset, count);
    }

    public Long zcard(final String key) {
        return redisCommandExecutor.zcard(key);
    }
    public Long zcard(final byte[] key) {
        return redisCommandExecutor.zcard(key);
    }

    public Long zrank(final String key, final String member) {
        return redisCommandExecutor.zrank(key, member);
    }
    public Long zrank(final byte[] key, final byte[] member) {
        return redisCommandExecutor.zrank(key, member);
    }

    public Long zrevrank(final String key, final String member) {
        return redisCommandExecutor.zrevrank(key, member);
    }
    public Long zrevrank(final byte[] key, final byte[] member) {
        return redisCommandExecutor.zrevrank(key, member);
    }

    public Double zscore(final String key, final String member) {
        return redisCommandExecutor.zscore(key, member);
    }
    public Double zscore(final byte[] key, final byte[] member) {
        return redisCommandExecutor.zscore(key, member);
    }

    public Long zcount(final String key, final double min, final double max) {
        return redisCommandExecutor.zcount(key, min, max);
    }
    public Long zcount(final byte[] key, final double min, final double max) {
        return redisCommandExecutor.zcount(key, min, max);
    }

    public Double zincrby(final String key, final double score, final String member) {
        return redisCommandExecutor.zincrby(key, score, member);
    }
    public Double zincrby(final byte[] key, final double score, final byte[] member) {
        return redisCommandExecutor.zincrby(key, score, member);
    }

    public Long zrem(final String key, final String... member) {
        return redisCommandExecutor.zrem(key, member);
    }
    public Long zrem(final byte[] key, final byte[]... member) {
        return redisCommandExecutor.zrem(key, member);
    }

    public Long zremrangeByRank(final String key, final long start, final long end) {
        return redisCommandExecutor.zremrangeByRank(key, start, end);
    }
    public Long zremrangeByRank(final byte[] key, final long start, final long end) {
        return redisCommandExecutor.zremrangeByRank(key, start, end);
    }

    public Long zremrangeByScore(final String key, final double start, final double end) {
        return redisCommandExecutor.zremrangeByScore(key, start, end);
    }
    public Long zremrangeByScore(final byte[] key, final double start, final double end) {
        return redisCommandExecutor.zremrangeByScore(key, start, end);
    }

    public Long zremrangeByLex(final String key, final String min, final String max) {
        return redisCommandExecutor.zremrangeByLex(key, min, max);
    }

    /** Lua scripts **/
    public Object eval(final String script, List<String> keys, List<String> args) {
        return redisCommandExecutor.eval(script, keys, args);
    }

    public Object eval(final byte[] script, final List<byte[]> keys, final List<byte[]> args) {
        return redisCommandExecutor.eval(script, keys, args);
    }

    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return redisCommandExecutor.evalsha(sha1, keys, args);
    }

    /**Pub-Sub**/
    public Long publish(byte[] channel, byte[] message) {
        return redisCommandExecutor.publish(channel, message);
    }
    public Long publish(String channel, String message) {
        return redisCommandExecutor.publish(channel, message);
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        redisCommandExecutor.subscribe(jedisPubSub, channels);
    }
    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
        redisCommandExecutor.psubscribe(jedisPubSub, patterns);
    }

    public void close() throws IOException {
        redisCommandExecutor.close();
    }

}
