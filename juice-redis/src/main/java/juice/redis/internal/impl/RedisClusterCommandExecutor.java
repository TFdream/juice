package juice.redis.internal.impl;


import redis.clients.jedis.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class RedisClusterCommandExecutor extends AbstractRedisCommandExecutor {

    private final JedisCluster jc;

    public RedisClusterCommandExecutor(JedisCluster jc) {
        this.jc = jc;
    }

    @Override
    public Long set(String key, String value) {
        return parseReply(jc.set(key, value));
    }

    @Override
    public Long set(final byte[] key, final byte[] value) {
        return parseReply(jc.set(key, value));
    }

    @Override
    public Long setnx(String key, String value) {
        return jc.setnx(key, value);
    }

    @Override
    public Long setnx(final byte[] key, final byte[] value) {
        return jc.setnx(key, value);
    }

    @Override
    public Long setex(String key, int seconds, String value) {
        return parseReply(jc.setex(key, seconds, value));
    }
    @Override
    public Long setex(final byte[] key, int seconds, final byte[] value) {
        return parseReply(jc.setex(key, seconds, value));
    }

    @Override
    public Long set(String key, String value, String nxxx, String expx, long time) {
        return parseReply(jc.set(key, value, nxxx, expx, time));
    }
    @Override
    public Long set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return parseReply(jc.set(key, value, nxxx, expx, time));
    }

    @Override
    public String get(String key) {
        return jc.get(key);
    }

    @Override
    public byte[] get(byte[] key) {
        return jc.get(key);
    }

    @Override
    public Boolean exists(String key) {
        return jc.exists(key);
    }
    @Override
    public Boolean exists(final byte[] key) {
        return jc.exists(key);
    }

    @Override
    public Long exists(final String... keys) {
        return jc.exists(keys);
    }
    @Override
    public Long exists(final byte[]... keys) {
        return jc.exists(keys);
    }

    @Override
    public Long del(String key) {
        return jc.del(key);
    }
    @Override
    public Long del(final byte[] key) {
        return jc.del(key);
    }
    @Override
    public Long del(final String... keys) {
        return jc.del(keys);
    }
    @Override
    public Long del(final byte[]... keys) {
        return jc.del(keys);
    }

    @Override
    public Long ttl(String key) {
        return jc.ttl(key);
    }

    @Override
    public Long ttl(byte[] key) {
        return jc.ttl(key);
    }

    @Override
    public Long pttl(String key) {
        return jc.pttl(key);
    }

    @Override
    public Long expire(final String key, int seconds) {
        return jc.expire(key, seconds);
    }
    @Override
    public Long expire(final byte[] key, int seconds) {
        return jc.expire(key, seconds);
    }

    @Override
    public Long expireAt(final String key, long unixTime) {
        return jc.expireAt(key, unixTime);
    }
    @Override
    public Long expireAt(final byte[] key, long unixTime) {
        return jc.expireAt(key, unixTime);
    }

    @Override
    public Long pexpire(final String key, final long milliseconds) {
        return jc.pexpire(key, milliseconds);
    }
    @Override
    public Long pexpire(final byte[] key, final long milliseconds) {
        return jc.pexpire(key, milliseconds);
    }

    @Override
    public Long incr(final String key) {
        return jc.incr(key);
    }
    @Override
    public Long incr(final byte[] key) {
        return jc.incr(key);
    }

    @Override
    public Long decr(final String key) {
        return jc.decr(key);
    }
    @Override
    public Long decr(final byte[] key) {
        return jc.decr(key);
    }

    @Override
    public Long incrBy(final String key, long delta) {
        return jc.incrBy(key, delta);
    }
    @Override
    public Long incrBy(final byte[] key, long delta) {
        return jc.incrBy(key, delta);
    }

    @Override
    public Long decrBy(final String key, long delta) {
        return jc.decrBy(key, delta);
    }
    @Override
    public Long decrBy(final byte[] key, long delta) {
        return jc.decrBy(key, delta);
    }

    /***List**/
    @Override
    public Long lpush(String key, String... values) {
        return jc.lpush(key, values);
    }

    @Override
    public Long rpush(final String key, final String... values) {
        return jc.rpush(key, values);
    }

    @Override
    public String lpop(String key) {
        return jc.lpop(key);
    }

    @Override
    public String rpop(String key) {
        return jc.rpop(key);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return jc.lrange(key, start, end);
    }

    @Override
    public String lindex(String key, long index) {
        return jc.lindex(key, index);
    }

    @Override
    public Long llen(String key) {
        return jc.llen(key);
    }

    /**hashmap**/
    @Override
    public Long hset(String key, String field, String value) {
        return jc.hset(key, field, value);
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        return jc.hsetnx(key, field, value);
    }
    @Override
    public String hmset(final String key, final Map<String, String> hash) {
        return jc.hmset(key, hash);
    }

    @Override
    public Set<String> hkeys(final String key) {
        return jc.hkeys(key);
    }

    @Override
    public List<String> hvals(final String key) {
        return jc.hvals(key);
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        return jc.hincrBy(key, field, value);
    }

    @Override
    public Long hdel(final String key, final String... fields) {
        return jc.hdel(key, fields);
    }

    @Override
    public Boolean hexists(String key, String field) {
        return jc.hexists(key, field);
    }

    @Override
    public Long hlen(final String key) {
        return jc.hlen(key);
    }

    @Override
    public String hget(final String key, final String field) {
        return jc.hget(key, field);
    }

    @Override
    public List<String> hmget(final String key, final String... fields) {
        return jc.hmget(key, fields);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return jc.hgetAll(key);
    }

    /**SET**/
    @Override
    public Long sadd(String key, String... member) {
        return jc.sadd(key, member);
    }

    @Override
    public Set<String> smembers(final String key) {
        return jc.smembers(key);
    }

    @Override
    public Long srem(final String key, final String... members) {
        return jc.srem(key, members);
    }

    @Override
    public String spop(final String key) {
        return jc.spop(key);
    }

    @Override
    public Long scard(final String key) {
        return jc.scard(key);
    }

    @Override
    public Boolean sismember(final String key, final String member) {
        return jc.sismember(key, member);
    }

    @Override
    public Set<String> sinter(final String... keys) {
        return jc.sinter(keys);
    }

    @Override
    public Set<String> sunion(final String... keys) {
        return jc.sunion(keys);
    }

    @Override
    public Set<String> sdiff(final String... keys) {
        return jc.sdiff(keys);
    }

    /** SortedSet **/
    @Override
    public Long zadd(final String key, final double score, final String member) {
        return jc.zadd(key, score, member);
    }
    @Override
    public Long zadd(final byte[] key, final double score, final byte[] member) {
        return jc.zadd(key, score, member);
    }
    @Override
    public Long zadd(final String key, final Map<String, Double> scoreMembers) {
        return jc.zadd(key, scoreMembers);
    }
    @Override
    public Long zadd(final byte[] key, final Map<byte[], Double> scoreMembers) {
        return jc.zadd(key, scoreMembers);
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return jc.zrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        return jc.zrange(key, start, end);
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return jc.zrangeWithScores(key, start, end);
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        return jc.zrangeWithScores(key, start, end);
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return jc.zrangeByScore(key, min, max);
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return jc.zrangeByScore(key, min, max);
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return jc.zrangeByScore(key, min, max, offset, count);
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return jc.zrangeByScore(key, min, max, offset, count);
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return jc.zrevrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return jc.zrevrange(key, start, end);
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return jc.zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        return jc.zscan(key, cursor);
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return jc.zscan(key, cursor, params);
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        return jc.zscan(key, cursor);
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return jc.zscan(key, cursor, params);
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        return jc.zrangeByLex(key, min, max);
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return jc.zrangeByLex(key, min, max, offset, count);
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return jc.zrevrangeByLex(key, min, max);
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return jc.zrevrangeByLex(key, min, max, offset, count);
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return jc.zrevrangeByScore(key, max, min, offset, count);
    }

    @Override
    public Long zcard(String key) {
        return jc.zcard(key);
    }

    @Override
    public Long zcard(byte[] key) {
        return jc.zcard(key);
    }

    @Override
    public Long zrank(String key, String member) {
        return jc.zrank(key, member);
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        return jc.zrank(key, member);
    }

    @Override
    public Long zrevrank(String key, String member) {
        return jc.zrevrank(key, member);
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        return jc.zrevrank(key, member);
    }

    @Override
    public Double zscore(String key, String member) {
        return jc.zscore(key, member);
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        return jc.zscore(key, member);
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return jc.zcount(key, min, max);
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        return jc.zcount(key, min, max);
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        return jc.zincrby(key, score, member);
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        return jc.zincrby(key, score, member);
    }

    @Override
    public Long zrem(String key, String... member) {
        return jc.zrem(key, member);
    }

    @Override
    public Long zrem(byte[] key, byte[]... member) {
        return jc.zrem(key, member);
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        return jc.zremrangeByRank(key, start, end);
    }

    @Override
    public Long zremrangeByRank(byte[] key, long start, long end) {
        return jc.zremrangeByRank(key, start, end);
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        return jc.zremrangeByScore(key, start, end);
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        return jc.zremrangeByScore(key, start, end);
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        return jc.zremrangeByLex(key, min, max);
    }

    @Override
    public Object eval(final String script, List<String> keys, List<String> args) {
        return jc.eval(script, keys, args);
    }

    @Override
    public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
        return jc.eval(script, keys, args);
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        return jc.evalsha(sha1, keys, args);
    }

    /**Pub-Sub**/
    @Override
    public Long publish(byte[] channel, byte[] message) {
        return jc.publish(channel, message);
    }
    @Override
    public Long publish(String channel, String message) {
        return jc.publish(channel, message);
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        jc.subscribe(jedisPubSub, channels);
    }
    @Override
    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
        jc.psubscribe(jedisPubSub, patterns);
    }

    @Override
    public void close() throws IOException {
        jc.close();
    }

}
