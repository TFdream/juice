package juice.core.util.reflect;

import juice.core.util.ClassUtils;
import juice.core.util.StringUtils;
import juice.core.util.AssertUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ricky Fung
 */
public class ReflectUtils {

    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final String IS_PREFIX = "is";

    /*** field ***/
    public static Field getField(final Class<?> clazz, final String fieldName){
        for (Class<?> searchType = clazz; searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Field field =  searchType.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                //not handler
            }
        }
        return null;
    }

    /**
     * Gets all fields of the given class and its parents (if any).
     *
     * @param cls
     *            the {@link Class} to query
     * @return an array of Fields (possibly empty).
     * @throws IllegalArgumentException
     *             if the class is {@code null}
     */
    public static Field[] getAllFields(final Class<?> cls) {
        final List<Field> allFieldsList = getAllFieldsList(cls);
        return allFieldsList.toArray(new Field[allFieldsList.size()]);
    }

    /**
     * Gets all fields of the given class and its parents (if any).
     *
     * @param cls
     *            the {@link Class} to query
     * @return an array of Fields (possibly empty).
     * @throws IllegalArgumentException
     *             if the class is {@code null}
     */
    public static List<Field> getAllFieldsList(final Class<?> cls) {
        AssertUtils.isTrue(cls != null, "The class must not be null");
        final List<Field> allFields = new ArrayList<>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * Gets all fields of the given class and its parents (if any) that are annotated with the given annotation.
     * @param cls
     *            the {@link Class} to query
     * @param annotationCls
     *            the {@link Annotation} that must be present on a field to be matched
     * @return an array of Fields (possibly empty).
     * @throws IllegalArgumentException
     *            if the class or annotation are {@code null}
     */
    public static Field[] getFieldsWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
        final List<Field> annotatedFieldsList = getFieldsListWithAnnotation(cls, annotationCls);
        return annotatedFieldsList.toArray(new Field[annotatedFieldsList.size()]);
    }

    /**
     * Gets all fields of the given class and its parents (if any) that are annotated with the given annotation.
     * @param cls
     *            the {@link Class} to query
     * @param annotationCls
     *            the {@link Annotation} that must be present on a field to be matched
     * @return a list of Fields (possibly empty).
     * @throws IllegalArgumentException
     *            if the class or annotation are {@code null}
     */
    public static List<Field> getFieldsListWithAnnotation(final Class<?> cls, final Class<? extends Annotation> annotationCls) {
        AssertUtils.isTrue(annotationCls != null, "The annotation class must not be null");
        final List<Field> allFields = getAllFieldsList(cls);
        final List<Field> annotatedFields = new ArrayList<>();
        for (final Field field : allFields) {
            if (field.getAnnotation(annotationCls) != null) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }
    
    public static void setFieldValue(final Field field, Object target, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Object getFieldValue(final Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Object getStaticFieldValue(final Field field) {
        return getFieldValue(field, null);
    }

    public static Object getStaticFieldValue(final Class<?> cls, final String fieldName) {
        final Field field = getField(cls, fieldName);
        return getStaticFieldValue(field);
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

    /**
     * 按属性名获取前缀为get或is的方法，并设为可访问
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Method getReadMethod(Class<?> clazz, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);

        Method method = getAccessibleMethod(clazz, getterMethodName);
        // retry on another name
        if (method == null) {
            getterMethodName = IS_PREFIX + StringUtils.capitalize(propertyName);
            method = getAccessibleMethod(clazz, getterMethodName);
        }
        return method;
    }

    public static void makeAccessible(Field field) {
        if((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void makeAccessible(Method method) {
        if((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }
}
