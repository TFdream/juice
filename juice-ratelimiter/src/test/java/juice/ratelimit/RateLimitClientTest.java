package juice.ratelimit;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Ricky Fung
 */
public class RateLimitClientTest extends RateLimiterTestBase {

    private final String redisKeyPrefix = "user.login.limit";

    @Test
    @Ignore
    public void testAcquire() {

        RateLimiter rateLimiter = client.buildRateLimiter(redisKeyPrefix, 10);
        Response response = rateLimiter.acquire("15");
        if (response.isAllowed()) {
            //do your stuff
            System.out.println("processing...");
        } else {
            System.out.println("too many requests per second");
        }
    }

    @Test
    @Ignore
    public void testAcquireN() {

        RateLimiter rateLimiter = client.buildRateLimiter(redisKeyPrefix, 10);
        Response response = rateLimiter.acquire("15", 5);
        if (response.isAllowed()) {
            //do your stuff
            System.out.println("processing...");
        } else {
            System.out.println("too many requests per second");
        }
    }
}
