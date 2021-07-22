package juice.lock;

/**
 * DLM
 * @author Ricky Fung
 */
public interface DistributedLockManager {

    DistributedLock getLock(String name);

    DistributedLock getMultiLock(DistributedLock... locks);
}
