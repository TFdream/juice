package juice.datasource.aop;

import juice.datasource.annotation.DSRouting;
import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcutAdvisor extends AbstractPointcutAdvisor implements InitializingBean {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_DATASOURCE_BEAN_NAME = "dynamicDataSource";

    private final Advice advice;

    private final Pointcut pointcut;

    //默认数据源名称
    private String dataSourceName;

    public DynamicDataSourcePointcutAdvisor() {
        this.pointcut = buildPointcut();
        this.advice = buildAdvice();
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    protected Advice buildAdvice() {
        return new DynamicDataSourceInterceptor();
    }

    protected Pointcut buildPointcut() {
        return new DynamicDSPointcut(DSRouting.class, true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("动态数据源切换-初始化完成, order={}, dataSourceName={}", getOrder(), dataSourceName);
    }

    static class DynamicDSPointcut implements Pointcut {
        private final ClassFilter classFilter;
        private final MethodMatcher methodMatcher;

        DynamicDSPointcut(Class<? extends Annotation> annotationType) {
            this(annotationType, false);
        }

        DynamicDSPointcut(Class<? extends Annotation> annotationType, boolean checkInherited) {
            this.classFilter = new AnnotationClassFilter(annotationType, checkInherited);
            //PS: AnnotationMethodMatcher
            this.methodMatcher = new DynamicMethodMatcher(annotationType, checkInherited);
        }

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return this.methodMatcher;
        }
    }

    static class DynamicMethodMatcher extends StaticMethodMatcher {
        private final Class<? extends Annotation> annotationType;

        private final boolean checkInherited;

        public DynamicMethodMatcher(Class<? extends Annotation> annotationType) {
            this(annotationType, false);
        }

        public DynamicMethodMatcher(Class<? extends Annotation> annotationType, boolean checkInherited) {
            this.annotationType = annotationType;
            this.checkInherited = checkInherited;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return matchesMethod(method, annotationType, checkInherited) ||
                    matchesClass(targetClass, annotationType, checkInherited);
        }

        boolean matchesClass(Class<?> targetClass, Class<? extends Annotation> annotationType, boolean checkInherited) {
            return (checkInherited ? AnnotatedElementUtils.hasAnnotation(targetClass, annotationType) :
                    targetClass.isAnnotationPresent(annotationType));
        }

        boolean matchesMethod(Method method, Class<? extends Annotation> annotationType, boolean checkInherited) {
            return (checkInherited ? AnnotatedElementUtils.hasAnnotation(method, annotationType) :
                    method.isAnnotationPresent(annotationType));
        }
    }

}
