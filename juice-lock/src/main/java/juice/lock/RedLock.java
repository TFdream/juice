package juice.lock;

import java.util.List;

/**
 * 参考资料：http://redis.io/topics/distlock">http://redis.io/topics/distlock
 * @author Ricky Fung
 */
public class RedLock extends MultiLock {

    public RedLock(DistributedLock... locks) {
        super(locks);
    }

    @Override
    protected int failedLocksLimit() {
        return locks.size() - minLocksAmount(locks);
    }

    protected int minLocksAmount(final List<DistributedLock> locks) {
        return locks.size()/2 + 1;
    }

}
