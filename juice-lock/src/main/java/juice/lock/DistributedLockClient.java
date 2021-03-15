package juice.lock;

/**
 * @author Ricky Fung
 */
public interface DistributedLockClient {

    DistributedLock getLock(String name);
}
