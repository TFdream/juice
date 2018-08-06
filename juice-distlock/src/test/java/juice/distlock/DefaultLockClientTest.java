package juice.distlock;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class DefaultLockClientTest extends LockTestBase {

    @Test
    @Ignore
    public void testReentrantLock() throws InterruptedException {
        String res = "res1";
        DLock lock = client.getLock(res);
        try {
            boolean success = lock.tryLock(5, 30, TimeUnit.SECONDS);
            System.out.println("key:"+res+" value:"+redisTemplate.get(res));
            if (success) {
                System.out.println("re-entry:"+lock.tryLock(5, 30, TimeUnit.SECONDS));
                System.out.println("Lock Success");
            } else {
                System.out.println("Lock Failure");
            }
        } finally {
            lock.unlock();
        }
        System.out.println("key:"+res+" value:"+redisTemplate.get(res));
    }

    @Test
    @Ignore
    public void testLock() throws InterruptedException {
        DLock lock = client.getLock("res");
        try {
            boolean success = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (success) {
                System.out.println("Lock Success");
            } else {
                System.out.println("Lock Failure");
            }
        } finally {
            lock.unlock();
        }
    }
}
