package juice.config.springsupport.lock;

import juice.config.springsupport.annotation.DLock;
import juice.config.springsupport.CommonAnnotationPointcut;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

import java.lang.annotation.Annotation;

/**
 * 通过实现感知接口EmbeddedValueResolverAware得到一个StringValueResolver来处理我们的占位符、SpEL计算。
 * @author Ricky Fung
 */
public class DLockPointcutAdvisor extends AbstractPointcutAdvisor implements EmbeddedValueResolverAware {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final Advice advice;
    private final Pointcut pointcut;

    private StringValueResolver embeddedValueResolver;

    //目标注解
    private Class<? extends Annotation> targetAnnotation;

    public DLockPointcutAdvisor() {
        this(DLock.class);
    }

    public DLockPointcutAdvisor(Class<? extends Annotation> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
        this.pointcut = buildPointcut(targetAnnotation);
        this.advice = buildAdvice();
    }

    @Override
    public Pointcut getPointcut() {
        return null;
    }

    @Override
    public Advice getAdvice() {
        return null;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.embeddedValueResolver = resolver;
    }

    private Advice buildAdvice() {
        return new DLockInterceptor();
    }

    private Pointcut buildPointcut(Class<? extends Annotation> targetAnnotation) {
        return new CommonAnnotationPointcut(targetAnnotation, true);
    }

}
