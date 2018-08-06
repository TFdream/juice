package juice.distlock;

/**
 * @author Ricky Fung
 */
public interface LockClient {

    DLock getLock(String name);
}
