package juice.lock;

import juice.exceptions.NestedRuntimeException;

/**
 * @author Ricky Fung
 */
public class DistributedLockException extends NestedRuntimeException {

    public DistributedLockException(String msg) {
        super(msg);
    }

    public DistributedLockException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
