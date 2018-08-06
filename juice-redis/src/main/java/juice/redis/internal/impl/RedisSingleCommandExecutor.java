package juice.redis.internal.impl;

import redis.clients.jedis.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public class RedisSingleCommandExecutor extends AbstractRedisCommandExecutor {

    private final JedisPool jp;

    public RedisSingleCommandExecutor(JedisPool jp) {
        this.jp = jp;
    }

    @Override
    public Long set(String key, String value) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.set(key, value));
        }
    }

    @Override
    public Long set(final byte[] key, final byte[] value) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.set(key, value));
        }
    }

    @Override
    public Long setnx(String key, String value) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.setnx(key, value);
        }
    }

    @Override
    public Long setnx(final byte[] key, final byte[] value) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.setnx(key, value);
        }
    }

    @Override
    public Long setex(String key, int seconds, String value) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.setex(key, seconds, value));
        }
    }

    @Override
    public Long setex(final byte[] key, int seconds, final byte[] value) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.setex(key, seconds, value));
        }
    }

    @Override
    public Long set(String key, String value, String nxxx, String expx, long time) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.set(key, value, nxxx, expx, time));
        }
    }

    @Override
    public Long set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        try (Jedis jedis = jp.getResource()) {
            return parseReply(jedis.set(key, value, nxxx, expx, time));
        }
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public byte[] get(byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public Boolean exists(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.exists(key);
        }
    }

    @Override
    public Boolean exists(final byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.exists(key);
        }
    }

    @Override
    public Long exists(final String... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.exists(keys);
        }
    }

    @Override
    public Long exists(final byte[]... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.exists(keys);
        }
    }

    @Override
    public Long del(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.del(key);
        }
    }

    @Override
    public Long del(final byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.del(key);
        }
    }

    @Override
    public Long del(final String... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.del(keys);
        }
    }

    @Override
    public Long del(final byte[]... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.del(keys);
        }
    }

    @Override
    public Long ttl(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long ttl(byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.ttl(key);
        }
    }

    @Override
    public Long pttl(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.pttl(key);
        }
    }

    @Override
    public Long expire(final String key, int seconds) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.expire(key, seconds);
        }
    }

    @Override
    public Long expire(final byte[] key, int seconds) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.expire(key, seconds);
        }
    }

    @Override
    public Long expireAt(final String key, long unixTime) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.expireAt(key, unixTime);
        }
    }

    @Override
    public Long expireAt(final byte[] key, long unixTime) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.expireAt(key, unixTime);
        }
    }

    @Override
    public Long pexpire(final String key, final long milliseconds) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.pexpire(key, milliseconds);
        }
    }

    @Override
    public Long pexpire(final byte[] key, final long milliseconds) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.pexpire(key, milliseconds);
        }
    }

    @Override
    public Long incr(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.incr(key);
        }
    }

    @Override
    public Long incr(final byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.incr(key);
        }
    }

    @Override
    public Long decr(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.decr(key);
        }
    }

    @Override
    public Long decr(final byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.decr(key);
        }
    }

    @Override
    public Long incrBy(final String key, long delta) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.incrBy(key, delta);
        }
    }

    @Override
    public Long incrBy(final byte[] key, long delta) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.incrBy(key, delta);
        }
    }

    @Override
    public Long decrBy(final String key, long delta) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.decrBy(key, delta);
        }
    }

    @Override
    public Long decrBy(final byte[] key, long delta) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.decrBy(key, delta);
        }
    }

    /***List**/
    @Override
    public Long lpush(String key, String... values) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.lpush(key, values);
        }
    }

    @Override
    public Long rpush(final String key, final String... values) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.rpush(key, values);
        }
    }

    @Override
    public String lpop(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.lpop(key);
        }
    }

    @Override
    public String rpop(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.rpop(key);
        }
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.lrange(key, start, end);
        }
    }

    @Override
    public String lindex(String key, long index) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.lindex(key, index);
        }
    }

    @Override
    public Long llen(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.llen(key);
        }
    }

    /**hashmap**/
    @Override
    public Long hset(String key, String field, String value) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hset(key, field, value);
        }
    }
    @Override
    public Long hsetnx(String key, String field, String value) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hsetnx(key, field, value);
        }
    }
    @Override
    public String hmset(final String key, final Map<String, String> hash) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hmset(key, hash);
        }
    }
    @Override
    public Set<String> hkeys(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hkeys(key);
        }
    }
    @Override
    public List<String> hvals(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hvals(key);
        }
    }
    @Override
    public Long hincrBy(String key, String field, long value) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }
    @Override
    public Long hdel(final String key, final String... fields) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hdel(key, fields);
        }
    }
    @Override
    public Boolean hexists(String key, String field) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hexists(key, field);
        }
    }
    @Override
    public Long hlen(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hlen(key);
        }
    }
    @Override
    public String hget(final String key, final String field) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hget(key, field);
        }
    }
    @Override
    public List<String> hmget(final String key, final String... fields) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hmget(key, fields);
        }
    }
    @Override
    public Map<String, String> hgetAll(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    /**SET**/
    @Override
    public Long sadd(String key, String... member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.sadd(key, member);
        }
    }
    @Override
    public Set<String> smembers(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.smembers(key);
        }
    }
    @Override
    public Long srem(final String key, final String... members) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.srem(key, members);
        }
    }
    @Override
    public String spop(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.spop(key);
        }
    }
    @Override
    public Long scard(final String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.scard(key);
        }
    }
    @Override
    public Boolean sismember(final String key, final String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.sismember(key, member);
        }
    }
    @Override
    public Set<String> sinter(final String... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.sinter(keys);
        }
    }
    @Override
    public Set<String> sunion(final String... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.sunion(keys);
        }
    }
    @Override
    public Set<String> sdiff(final String... keys) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.sdiff(keys);
        }
    }

    @Override
    public Long zadd(String key, double score, String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByScore(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByScore(key, min, max, offset, count);
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscan(key, cursor, params);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscan(key, cursor);
        }
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscan(key, cursor, params);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByLex(key, min, max);
        }
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrangeByLex(key, min, max, offset, count);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrangeByLex(key, min, max);
        }
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrangeByLex(key, min, max, offset, count);
        }
    }

    @Override
    public Long zcard(String key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zcard(key);
        }
    }

    @Override
    public Long zcard(byte[] key) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zcard(key);
        }
    }

    @Override
    public Long zrank(String key, String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrank(key, member);
        }
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrank(key, member);
        }
    }

    @Override
    public Long zrevrank(String key, String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrank(key, member);
        }
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrevrank(key, member);
        }
    }

    @Override
    public Double zscore(String key, String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscore(key, member);
        }
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zscore(key, member);
        }
    }

    @Override
    public Long zcount(String key, double min, double max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zcount(key, min, max);
        }
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zcount(key, min, max);
        }
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    @Override
    public Long zrem(String key, String... member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrem(key, member);
        }
    }

    @Override
    public Long zrem(byte[] key, byte[]... member) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zrem(key, member);
        }
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zremrangeByRank(key, start, end);
        }
    }

    @Override
    public Long zremrangeByRank(byte[] key, long start, long end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zremrangeByRank(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.zremrangeByLex(key, min, max);
        }
    }

    //
    public Object eval(String script) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.eval(script);
        }
    }

    @Override
    public Object eval(final String script, List<String> keys, List<String> args) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.eval(script, keys, args);
        }
    }

    @Override
    public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.eval(script, keys, args);
        }
    }

    //
    public Object evalsha(String script) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.evalsha(script);
        }
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.evalsha(sha1, keys, args);
        }
    }

    /**Pub-Sub**/
    @Override
    public Long publish(byte[] channel, byte[] message) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.publish(channel, message);
        }
    }
    @Override
    public Long publish(String channel, String message) {
        try (Jedis jedis = jp.getResource()) {
            return jedis.publish(channel, message);
        }
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        try (Jedis jedis = jp.getResource()) {
            jedis.subscribe(jedisPubSub, channels);
        }
    }
    @Override
    public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
        try (Jedis jedis = jp.getResource()) {
            jedis.psubscribe(jedisPubSub, patterns);
        }
    }

    @Override
    public void close() throws IOException {
        jp.close();
    }
}
