package juice.lock.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 单例
 * @author Ricky Fung
 */
public class RedisScriptHolder {
    // 加锁脚本
    private final DefaultRedisScript<Long> lockScript;

    // 解锁脚本
    private final DefaultRedisScript<Long> unlockScript;

    private RedisScriptHolder() {
        // Lock script
        this.lockScript = new DefaultRedisScript<>();
        this.lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/lua/juice/try_lock.lua")));
        this.lockScript.setResultType(Long.class);

        // unlock script
        this.unlockScript = new DefaultRedisScript<>();
        this.unlockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/lua/juice/try_unlock.lua")));
        this.unlockScript.setResultType(Long.class);
    }

    public DefaultRedisScript<Long> getLockScript() {
        return lockScript;
    }

    public DefaultRedisScript<Long> getUnlockScript() {
        return unlockScript;
    }

    //-----------
    public static RedisScriptHolder getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final RedisScriptHolder INSTANCE = new RedisScriptHolder();
    }
}
