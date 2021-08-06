package juice.config.springsupport.lock;

import juice.commons.Assertions;
import juice.commons.collection.ConcurrentReferenceHashMap;
import juice.config.springsupport.annotation.DLock;
import juice.config.springsupport.util.AnnotationScanUtils;
import juice.config.springsupport.util.ExpressionUtils;
import juice.lock.DistributedLock;
import juice.lock.DistributedLockException;
import juice.lock.DistributedLockManager;
import juice.util.StringUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky Fung
 */
public class DLockInterceptor implements MethodInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentReferenceHashMap<Method, LockInvokeConfig> configMap = new ConcurrentReferenceHashMap<>(256);

    private String lockManagerBeanName;

    private DistributedLockManager distributedLockManager;

    private ApplicationContext context;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (distributedLockManager == null) {
            distributedLockManager = this.context.getBean(this.lockManagerBeanName, DistributedLockManager.class);
        }
        //解析方法上的注解配置
        LockInvokeConfig config = getConfig(invocation);

        LockInvokeContext context = new LockInvokeContext();
        context.setMethod(invocation.getMethod());
        context.setArguments(invocation.getArguments());
        Object obj = invocation.getThis();
        context.setTargetObject(obj);

        //执行SpEL表达式
        Object retVal = ExpressionUtils.eval(config.getKey(), context);
        Assertions.notNull(retVal, "key不能为NULL");

        String key = retVal.toString();
        if (StringUtils.isNotEmpty(config.getPrefix())) {
            key = String.format("%s:%s", config.getPrefix(), key);
        }

        long waitTime = config.getWaitTime();
        long leaseTime = config.getLeaseTime();
        TimeUnit timeUnit = config.getTimeUnit();
        DistributedLock lock = distributedLockManager.getLock(key);
        if (LOG.isDebugEnabled()) {
            LOG.debug("分布式锁-解析解锁元数据, key={}, waitTime={}, leaseTime={}, timeUnit={}", key, waitTime, leaseTime, timeUnit);
        }
        try {
            boolean success = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (!success) {
                LOG.warn("分布式锁-加锁失败, key={}, waitTime={}, leaseTime={}, timeUnit={}", key, waitTime, leaseTime, timeUnit);
                throw new DistributedLockException(String.format("加锁失败, key=%s", key));
            }
            return invocation.proceed();
        } finally {
            lock.unlock();
        }
    }

    public void setLockManagerBeanName(String lockManagerBeanName) {
        this.lockManagerBeanName = lockManagerBeanName;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private LockInvokeConfig getConfig(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        LockInvokeConfig config = configMap.get(method);
        if (config == null) {
            DLock lock = AnnotationScanUtils.findAnnotation(method, DLock.class);
            Assertions.notNull(lock, String.format("@DLock is not present at method=%s, class=%s", method.getName(), method.getDeclaringClass()));

            config = new LockInvokeConfig();
            config.setPrefix(lock.prefix());
            config.setKey(lock.key());
            config.setWaitTime(lock.waitTime());
            config.setLeaseTime(lock.leaseTime());
            config.setTimeUnit(lock.timeUnit());
            LockInvokeConfig old = configMap.putIfAbsent(method, config);
            if (old != null) {
                config = old;
            }
        }
        return config;
    }

}
