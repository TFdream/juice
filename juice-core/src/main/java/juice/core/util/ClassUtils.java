package juice.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * @author Ricky Fung
 */
public abstract class ClassUtils {

    /**
     * Map with primitive wrapper type as key and corresponding primitive
     * type as value, for example: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    /**
     * Map with primitive type name as key and corresponding primitive
     * type as value, for example: "int" -> "int.class".
     */
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(32);

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
        //primitiveWrapperTypeMap.put(Void.TYPE, Void.TYPE);

        Set<Class<?>> primitiveTypes = new HashSet<Class<?>>(32);
        primitiveTypes.addAll(primitiveWrapperTypeMap.values());
        primitiveTypes.addAll(Arrays.asList(new Class<?>[] {
                boolean[].class, byte[].class, char[].class, double[].class,
                float[].class, int[].class, long[].class, short[].class}));
        primitiveTypes.add(void.class);
        for (Class<?> primitiveType : primitiveTypes) {
            primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
        }
    }

    public static InputStream getResourceAsStream(String fullName) throws IOException {
        return getClassLoader(ClassUtils.class).getResourceAsStream(fullName);
    }

    public static Enumeration<URL> getResources(String fullName) throws IOException {
        return getClassLoader(ClassUtils.class).getResources(fullName);
    }

    /**
     * get class loader
     *
     * @param cls
     * @return class loader
     */
    public static ClassLoader getClassLoader(Class<?> cls) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = cls.getClassLoader();
        }
        return cl;
    }

    /**
     * 兼容原子类型与非原子类型的转换，不考虑依赖两者不同来区分不同函数的场景
     */
    public static void wrapClasses(Class<?>[] source) {
        for (int i = 0; i < source.length; i++) {
            Class<?> wrapClass = primitiveWrapperTypeMap.get(source[i]);
            if (wrapClass != null) {
                source[i] = wrapClass;
            }
        }
    }

    /**
     * Check whether the given class is cache-safe in the given context,
     * i.e. whether it is loaded by the given ClassLoader or a parent of it.
     * @param clazz the class to analyze
     * @param classLoader the ClassLoader to potentially cache metadata in
     */
    public static boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader) {
        AssertUtils.notNull(clazz, "Class must not be null");
        try {
            ClassLoader target = clazz.getClassLoader();
            if (target == null) {
                return true;
            }
            ClassLoader cur = classLoader;
            if (cur == target) {
                return true;
            }
            while (cur != null) {
                cur = cur.getParent();
                if (cur == target) {
                    return true;
                }
            }
            return false;
        }
        catch (SecurityException ex) {
            // Probably from the system ClassLoader - let's consider it safe.
            return true;
        }
    }

    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            forName(className, classLoader);
            return true;
        }
        catch (Throwable ex) {
            return false;
        }
    }

    public static Class<?> forName(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }

    public static Class<?> forName(String name, ClassLoader cl) throws ClassNotFoundException {
        return Class.forName(name, true, cl);
    }

    /**
     * 递归返回所有的SupperClasses，包含Object.class
     */
    public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
        if (cls == null) {
            return null;
        }
        final List<Class<?>> classes = new ArrayList<>();
        Class<?> superclass = cls.getSuperclass();
        while (superclass != null) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }

    /**
     * 递归返回本类及所有基类继承的接口，及接口继承的接口。
     */
    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        LinkedHashSet<Class<?>> interfaces = new LinkedHashSet<>();
        while(cls!=null){
            Class<?>[] arr = cls.getInterfaces();
            if(arr!=null){
                for(Class<?> inter : arr){
                    interfaces.add(inter);
                }
            }
            cls = cls.getSuperclass();
        }
        return new ArrayList<>(interfaces);
    }

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }
}
