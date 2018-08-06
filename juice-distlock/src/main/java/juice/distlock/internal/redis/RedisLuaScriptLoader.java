package juice.distlock.internal.redis;

import juice.core.util.TextUtils;

/**
 * @author Ricky Fung
 */
public class RedisLuaScriptLoader {
    private static final String PREFIX = "META-INF/scripts/";

    private final String lockScript;
    private final String unlockScript;
    private final String holdByScript;

    public RedisLuaScriptLoader() {
        try {
            this.lockScript = TextUtils.readClassPath(PREFIX+"distlock_lock.lua");
            this.unlockScript = TextUtils.readClassPath(PREFIX+"distlock_unlock.lua");
            this.holdByScript = TextUtils.readClassPath(PREFIX+"distlock_hold.lua");
        } catch (Exception e) {
            throw new RuntimeException("load redis lua scripts error!", e);
        }
    }

    public static RedisLuaScriptLoader getLoader() {
        return Singleton.INSTANCE;
    }

    public String getLockScript() {
        return lockScript;
    }

    public String getUnlockScript() {
        return unlockScript;
    }

    public String getHoldByScript() {
        return holdByScript;
    }

    private static class Singleton {
        private static final RedisLuaScriptLoader INSTANCE = new RedisLuaScriptLoader();
    }
}
