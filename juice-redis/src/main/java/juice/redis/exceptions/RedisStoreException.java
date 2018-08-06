package juice.redis.exceptions;

/**
 * @author Ricky Fung
 */
public class RedisStoreException extends RuntimeException {
    private static final long serialVersionUID = 1367995132314603786L;

    public RedisStoreException() {
    }

    public RedisStoreException(String message) {
        super(message);
    }

    public RedisStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
