package juice.ratelimit;

/**
 * @author Ricky Fung
 */
public interface RateLimiter {

    Response acquire(String id);

    Response acquire(String id, int permits);

}
