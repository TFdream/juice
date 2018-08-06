package juice.ratelimit;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ricky Fung
 */
public class ConcurrentLimitTest extends RateLimiterTestBase {

    private final int num = 10;
    private final int loop = 100;
    private final String redisKeyPrefix = "limit";

    @Test
    @Ignore
    public void testLimit() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(num);

        final RateLimiter rateLimiter = client.buildRateLimiter(redisKeyPrefix, 10);

        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (int i=0; i<num; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {

                    for (int j=0; j<loop; j++) {
                        Response response = rateLimiter.acquire("15");
                        if (response.isAllowed()) {
                            System.out.println(String.format("Thread:[%s] processing...", Thread.currentThread().getName()));
                        } else {
                            System.out.println(String.format("Thread:[%s] too many requests per second", Thread.currentThread().getName()));
                        }
                    }
                    latch.countDown();
                    System.out.println(String.format("Thread:[%s] Done", Thread.currentThread().getName()));
                }
            });
        }

        latch.await();
        System.out.println("Finish!");
        pool.shutdown();
    }
}
