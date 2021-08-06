package juice.config.springsupport.lock;

import juice.config.springsupport.annotation.DLock;
import juice.config.springsupport.CommonAnnotationPointcut;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

import java.lang.annotation.Annotation;

/**
 * 通过实现感知接口EmbeddedValueResolverAware得到一个StringValueResolver来处理我们的占位符、SpEL计算。
 * @author Ricky Fung
 */
public class DLockPointcutAdvisor extends AbstractPointcutAdvisor implements ApplicationContextAware, EmbeddedValueResolverAware {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private Advice advice;
    private Pointcut pointcut;

    private String lockManagerBeanName;

    private ApplicationContext context;
    private StringValueResolver embeddedValueResolver;

    public DLockPointcutAdvisor() {}

    public void setLockManagerBeanName(String lockManagerBeanName) {
        this.lockManagerBeanName = lockManagerBeanName;
    }

    @Override
    public Pointcut getPointcut() {
        if (this.pointcut == null) {
            this.pointcut = buildPointcut(DLock.class);
        }
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        if (this.advice == null) {
            this.advice = buildAdvice();
        }
        return this.advice;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolver = resolver;
    }

    //==========

    private Advice buildAdvice() {
        DLockInterceptor interceptor = new DLockInterceptor();
        interceptor.setApplicationContext(this.context);
        interceptor.setLockManagerBeanName(this.lockManagerBeanName);
        return interceptor;
    }

    private Pointcut buildPointcut(Class<? extends Annotation> targetAnnotation) {
        return new CommonAnnotationPointcut(targetAnnotation, true);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        LOG.info("分布式锁-初始化, context={}, lockManagerBeanName={}", this.context, this.lockManagerBeanName);
    }
}
