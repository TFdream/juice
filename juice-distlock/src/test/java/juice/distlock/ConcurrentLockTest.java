package juice.distlock;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class ConcurrentLockTest extends LockTestBase {

    private final int num = 10;
    private final int loop = 100;
    private final String res = "lock";

    @Test
    @Ignore
    public void testLock() throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(num);

        final DLock lock = client.getLock(res);

        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (int i=0; i<num; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {

                    for (int j=0; j<loop; j++) {
                        try {
                            boolean success = lock.tryLock(3, 30, TimeUnit.SECONDS);
                            if (success) {
                                System.out.println(String.format("Thread:[%s] Lock Success", Thread.currentThread().getName()));
                                doWork();
                            } else {
                                System.out.println(String.format("Thread:[%s] Lock Failure", Thread.currentThread().getName()));
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
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

    private void doWork() throws InterruptedException {

        int timeout = new Random().nextInt(2000);
        if (timeout<10) {
            timeout = 10;
        }
        sleep(timeout);
    }
}
