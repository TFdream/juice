package juice.distlock.internal.redis;

import juice.distlock.DLock;
import juice.redis.RedisTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 参考：[Distributed locks with Redis](https://redis.io/topics/distlock)
 * @author Ricky Fung
 */
public class RedisDistLock implements DLock {
    /** 唯一标识id **/
    private String requestId;
    /** 锁名称 **/
    private String lockName;
    private RedisTemplate redisTemplate;

    private final List<String> keys = new ArrayList<>(1);

    public RedisDistLock(String requestId, String lockName, RedisTemplate redisTemplate) {
        this.requestId = requestId;
        this.lockName = lockName;
        this.redisTemplate = redisTemplate;
        this.keys.add(this.lockName);

    }

    @Override
    public void lock() {
        while (true) {
            boolean res = tryAcquire(30L, TimeUnit.SECONDS);
            if (res) {
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        long time = unit.toMillis(waitTime);
        long current = System.currentTimeMillis();
        boolean res = tryAcquire(leaseTime, unit);
        // lock acquired
        if (res) {
            return true;
        }
        time -= (System.currentTimeMillis() - current);
        if (time <= 0) {
            return false;
        }
        while (true) {
            long currentTime = System.currentTimeMillis();
            res = tryAcquire(leaseTime, unit);
            // lock acquired
            if (res) {
                return true;
            }
            time -= (System.currentTimeMillis() - currentTime);
            if (time <= 0) {
                return false;
            }
        }
    }

    @Override
    public boolean isLocked() {
        return redisTemplate.exists(lockName);
    }

    @Override
    public boolean isHeldByCurrentThread() {
        List<String> args = new ArrayList<>(1);
        args.add(getLockValue());
        Long update = (Long) redisTemplate.eval(RedisLuaScriptLoader.getLoader().getHoldByScript(), keys,
                args);
        if (update==1) {
            return true;
        }
        return false;
    }

    @Override
    public void forceUnlock() {
        redisTemplate.del(lockName);
    }

    @Override
    public void unlock() {
        List<String> args = new ArrayList<>(1);
        args.add(getLockValue());
        Long update = (Long) redisTemplate.eval(RedisLuaScriptLoader.getLoader().getUnlockScript(), keys, args);

    }

    private boolean tryAcquire(long leaseTime, TimeUnit unit) {
        List<String> args = new ArrayList<>(4);
        args.add(getLockValue());
        args.add("NX");
        args.add("PX");
        args.add(String.valueOf(unit.toMillis(leaseTime)));
        Long update = (Long) redisTemplate.eval(RedisLuaScriptLoader.getLoader().getLockScript(), keys, args);
        if (update==1) {
            return true;
        }
        return false;
    }

    protected String getLockValue() {
        return String.format("%s:%d", requestId, Thread.currentThread().getId());
    }

}
