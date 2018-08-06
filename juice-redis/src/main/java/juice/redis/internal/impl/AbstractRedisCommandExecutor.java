package juice.redis.internal.impl;

import juice.redis.internal.RedisCommandExecutor;

/**
 * @author Ricky Fung
 */
public abstract class AbstractRedisCommandExecutor implements RedisCommandExecutor {
    private static final String OK_RESP = "OK";

    protected Long parseReply(String reply) {
        return OK_RESP.equals(reply) ? 1L:0L;
    }
}
