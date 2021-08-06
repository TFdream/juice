package juice.config.springsupport;

import juice.config.springsupport.util.AnnotationScanUtils;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class CommonAnnotationPointcut implements Pointcut {
    private final ClassFilter classFilter;
    private final MethodMatcher methodMatcher;

    public CommonAnnotationPointcut(Class<? extends Annotation> annotationType) {
        this(annotationType, false);
    }

    public CommonAnnotationPointcut(Class<? extends Annotation> annotationType, boolean checkInherited) {
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
            return AnnotationScanUtils.matchesMethod(method, annotationType, checkInherited) ||
                    AnnotationScanUtils.matchesClass(targetClass, annotationType, checkInherited);
        }

    }
}
