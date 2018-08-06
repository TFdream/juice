package juice.spi.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Ricky Fung
 */
public abstract class ReflectUtils {

    private static final String SETTER_PREFIX = "set";

    /**
     * 按属性名获取前缀为set的方法，并设为可访问
     * @param clazz
     * @param propertyName
     * @param parameterType
     * @return
     */
    public static Method getWriteMethod(Class<?> clazz, String propertyName, Class<?> parameterType) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        return getAccessibleMethod(clazz, setterMethodName, parameterType);
    }

    /*** method ***/
    /**
     * <p>Returns an accessible method (that is, one that can be invoked via
     * reflection) with given name and parameters. If no such method
     * can be found, return {@code null}.
     *
     * @param clazz get method from this class
     * @param methodName get method with this name
     * @param parameterTypes with these parameters types
     * @return The accessible method
     */
    public static Method getAccessibleMethod(final Class<?> clazz, final String methodName,
                                             Class<?>... parameterTypes) {

        // 处理原子类型与对象类型的兼容
        ClassUtils.wrapClasses(parameterTypes);

        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                        && !method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }
}
