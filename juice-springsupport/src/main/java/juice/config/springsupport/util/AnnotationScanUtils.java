package juice.config.springsupport.util;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Ricky Fung
 */
public abstract class AnnotationScanUtils {

    private static final Map<AnnotationCacheKey, Annotation> classAnnotationCache =
            new ConcurrentReferenceHashMap<>(256);

    private static final Map<AnnotationCacheKey, Annotation> methodAnnotationCache =
            new ConcurrentReferenceHashMap<>(256);

    //==========

    public static boolean matchesClass(Class<?> targetClass, Class<? extends Annotation> annotationType) {
        return matchesClass(targetClass, annotationType, false);
    }

    public static boolean matchesClass(Class<?> targetClass, Class<? extends Annotation> annotationType, boolean checkInherited) {
        return (checkInherited ? AnnotatedElementUtils.hasAnnotation(targetClass, annotationType) :
                targetClass.isAnnotationPresent(annotationType));
    }

    public static boolean matchesMethod(Method method, Class<? extends Annotation> annotationType) {
        return matchesMethod(method, annotationType, false);
    }
    public static boolean matchesMethod(Method method, Class<? extends Annotation> annotationType, boolean checkInherited) {
        return (checkInherited ? AnnotatedElementUtils.hasAnnotation(method, annotationType) :
                method.isAnnotationPresent(annotationType));
    }

    //==========

    public static <A extends Annotation> A findAnnotation(Class<?> clazz, @Nullable Class<A> annotationType) {
        AnnotationCacheKey cacheKey = new AnnotationCacheKey(clazz, annotationType);
        A result = (A) classAnnotationCache.computeIfAbsent(cacheKey, key -> AnnotatedElementUtils.findMergedAnnotation(clazz, annotationType));
        return result;
    }

    public static <A extends Annotation> A findAnnotation(Method method, @Nullable Class<A> annotationType) {
        AnnotationCacheKey cacheKey = new AnnotationCacheKey(method, annotationType);
        A result = (A) methodAnnotationCache.get(cacheKey);
        if (result == null) {
            result = AnnotatedElementUtils.findMergedAnnotation(method, annotationType);
            if (result != null) {
                methodAnnotationCache.put(cacheKey, result);
            }
        }
        return result;
    }

    /**
     * Cache key for the AnnotatedElement cache.
     */
    private static final class AnnotationCacheKey implements Comparable<AnnotationCacheKey> {

        private final AnnotatedElement element;

        private final Class<? extends Annotation> annotationType;

        public AnnotationCacheKey(AnnotatedElement element, Class<? extends Annotation> annotationType) {
            this.element = element;
            this.annotationType = annotationType;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AnnotationCacheKey)) {
                return false;
            }
            AnnotationCacheKey otherKey = (AnnotationCacheKey) other;
            return (this.element.equals(otherKey.element) && this.annotationType.equals(otherKey.annotationType));
        }

        @Override
        public int hashCode() {
            return (this.element.hashCode() * 29 + this.annotationType.hashCode());
        }

        @Override
        public String toString() {
            return "@" + this.annotationType + " on " + this.element;
        }

        @Override
        public int compareTo(AnnotationCacheKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0) {
                result = this.annotationType.getName().compareTo(other.annotationType.getName());
            }
            return result;
        }
    }
}
