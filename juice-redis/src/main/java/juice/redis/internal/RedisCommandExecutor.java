package juice.redis.internal;


import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ricky Fung
 */
public interface RedisCommandExecutor {

    Long set(String key, String value);

    Long set(final byte[] key, final byte[] value);

    Long setnx(String key, String value);

    Long setnx(final byte[] key, final byte[] value);

    Long setex(String key, int seconds, String value);

    Long setex(final byte[] key, int seconds, final byte[] value);

    Long set(String key, String value, String nxxx, String expx, long time);

    Long set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time);

    String get(String key);

    byte[] get(byte[] key);

    Boolean exists(String key);

    Boolean exists(final byte[] key);

    Long exists(final String... keys);

    Long exists(final byte[]... keys);

    Long del(String key);

    Long del(final byte[] key);

    Long del(final String... keys);

    Long del(final byte[]... keys);

    Long ttl(final String key);

    Long ttl(byte[] key);

    Long pttl(final String key);

    Long expire(final String key, int seconds);

    Long expire(final byte[] key, int seconds);

    Long expireAt(final String key, long unixTime);

    Long expireAt(final byte[] key, long unixTime);

    Long pexpire(final String key, final long milliseconds);

    Long pexpire(final byte[] key, final long milliseconds);

    Long incr(final String key);

    Long incr(final byte[] key);

    Long decr(final String key);

    Long decr(final byte[] key);

    Long incrBy(final String key, long delta);

    Long incrBy(final byte[] key, long delta);

    Long decrBy(final String key, long delta);

    Long decrBy(final byte[] key, long delta);

    /***List**/
    Long lpush(String key, String... values);

    Long rpush(final String key, final String... values);

    String lpop(String key);

    String rpop(String key);

    List<String> lrange(String key, long start, long end);

    String lindex(String key, long index);

    Long llen(String key);

    /**hashmap**/
    Long hset(String key, String field, String value);

    Long hsetnx(String key, String field, String value);

    String hmset(final String key, final Map<String, String> hash);

    Set<String> hkeys(final String key);

    List<String> hvals(final String key);

    Long hincrBy(String key, String field, long value);

    Long hdel(final String key, final String... fields);

    Boolean hexists(String key, String field);

    Long hlen(final String key);

    String hget(final String key, final String field);

    List<String> hmget(final String key, final String... fields);

    Map<String, String> hgetAll(String key);

    /**SET**/
    Long sadd(String key, String... members);

    Set<String> smembers(final String key);

    Long srem(final String key, final String... members);

    String spop(final String key);

    Long scard(final String key);

    Boolean sismember(final String key, final String member);

    Set<String> sinter(final String... keys);

    Set<String> sunion(final String... keys);

    Set<String> sdiff(final String... keys);

    /** SortedSet **/
    Long zadd(final String key, final double score, final String member);
    Long zadd(final byte[] key, final double score, final byte[] member);

    Long zadd(final String key, final Map<String, Double> scoreMembers);
    Long zadd(final byte[] key, final Map<byte[], Double> scoreMembers);

    Set<String> zrange(final String key, final long start, final long end);
    Set<byte[]> zrange(final byte[] key, final long start, final long end);

    Set<String> zrangeByScore(final String key, final double min, final double max);
    Set<byte[]> zrangeByScore(final byte[] key, final double min, final double max);

    Set<String> zrangeByScore(final String key, final double min, final double max,
                              final int offset, final int count);
    Set<byte[]> zrangeByScore(final byte[] key, final byte[] min, final byte[] max,
                              final int offset, final int count);

    Set<Tuple> zrangeWithScores(final String key, final long start, final long end);
    Set<Tuple> zrangeWithScores(final byte[] key, final long start, final long end);


    Set<String> zrevrange(final String key, final long start, final long end);
    Set<byte[]> zrevrange(final byte[] key, final long start, final long end);

    Set<String> zrevrangeByScore(final String key, final double max, final double min,
                                 final int offset, final int count);
    Set<byte[]> zrevrangeByScore(final byte[] key, final double max, final double min,
                                 final int offset, final int count);

    ScanResult<Tuple> zscan(final String key, final String cursor);
    ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params);

    ScanResult<Tuple> zscan(final byte[] key, final byte[] cursor);
    ScanResult<Tuple> zscan(final byte[] key, final byte[] cursor, final ScanParams params);

    Set<String> zrangeByLex(final String key, final String min, final String max);
    Set<String> zrangeByLex(final String key, final String min, final String max,
                            final int offset, final int count);

    Set<String> zrevrangeByLex(final String key, final String max, final String min);
    Set<String> zrevrangeByLex(final String key, final String max, final String min,
                               final int offset, final int count);

    Long zcard(final String key);
    Long zcard(final byte[] key);

    Long zrank(final String key, final String member);
    Long zrank(final byte[] key, final byte[] member);

    Long zrevrank(final String key, final String member);
    Long zrevrank(final byte[] key, final byte[] member);

    Double zscore(final String key, final String member);
    Double zscore(final byte[] key, final byte[] member);

    Long zcount(final String key, final double min, final double max);
    Long zcount(final byte[] key, final double min, final double max);

    Double zincrby(final String key, final double score, final String member);
    Double zincrby(final byte[] key, final double score, final byte[] member);

    Long zrem(final String key, final String... member);
    Long zrem(final byte[] key, final byte[]... member);

    Long zremrangeByRank(final String key, final long start, final long end);
    Long zremrangeByRank(final byte[] key, final long start, final long end);

    Long zremrangeByScore(final String key, final double start, final double end);
    Long zremrangeByScore(final byte[] key, final double start, final double end);

    Long zremrangeByLex(final String key, final String min, final String max);


    /** Lua scripts **/
    Object eval(final String script, List<String> keys, List<String> args);
    Object eval(final byte[] script, final List<byte[]> keys, final List<byte[]> args);

    Object evalsha(String sha1, List<String> keys, List<String> args);

    /**Pub-Sub**/
    Long publish(byte[] channel, byte[] message);

    Long publish(String channel, String message);

    void subscribe(JedisPubSub jedisPubSub, String... channels);

    void psubscribe(final JedisPubSub jedisPubSub, final String... patterns);


    void close() throws IOException;

}
