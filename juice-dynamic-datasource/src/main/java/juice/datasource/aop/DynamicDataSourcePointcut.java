package juice.datasource.aop;

import juice.datasource.annotation.DSRouting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author Ricky Fung
 */
public class DynamicDataSourcePointcut extends StaticMethodMatcherPointcut {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final String[] basePackages;

    public DynamicDataSourcePointcut(String[] basePackages) {
        setClassFilter(new DynamicDataSourceClassFilter());
        this.basePackages = basePackages;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        boolean b = isMatchMethod(method, targetClass);
        if (b) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("check method match true, method={}, declaringClass={}, targetClass={}",
                        method.getName(),
                        ClassUtils.getShortName(method.getDeclaringClass()),
                        targetClass == null ? null : ClassUtils.getShortName(targetClass));
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("check method match false, method={}, declaringClass={}, targetClass={}",
                        method.getName(),
                        ClassUtils.getShortName(method.getDeclaringClass()),
                        targetClass == null ? null : ClassUtils.getShortName(targetClass));
            }
        }
        return b;
    }

    //=========
    private class DynamicDataSourceClassFilter implements ClassFilter {

        @Override
        public boolean matches(Class<?> clazz) {
            return isMatchClass(clazz);
        }
    }
    private boolean isMatchMethod(Method method, Class<?> targetClass) {
        if (Object.class.equals(method.getDeclaringClass())) {  //toString之类的方法
            return false;
        }
        DSRouting dsRouting = AnnotationUtils.getAnnotation(method, DSRouting.class);
        if (dsRouting != null) {
            return true;
        }
        dsRouting = AnnotationUtils.findAnnotation(targetClass, DSRouting.class);
        if (dsRouting != null) {
            return true;
        }
        return false;
    }

    //=========

    private boolean isMatchClass(Class clazz) {
        if (matchesPkg(clazz)) {
            return true;
        }
        //先检查类自身上有没有注解
        if (AnnotationUtils.findAnnotation(clazz, DSRouting.class) != null) {
            return true;
        }

        Class[] cs = clazz.getInterfaces();
        if (cs != null) {
            for (Class c : cs) {
                if (AnnotationUtils.findAnnotation(c, DSRouting.class) != null) {
                    return true;
                }
            }
        }
        if (!clazz.isInterface()) {
            Class sp = clazz.getSuperclass();
            if (sp != null && isMatchClass(sp)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesPkg(Class clazz) {
        String name = clazz.getName();
        return include(name);
    }

    private boolean include(String name) {
        if (basePackages != null) {
            for (String p : basePackages) {
                if (name.startsWith(p)) {
                    return true;
                }
            }
        }
        return false;
    }
}
