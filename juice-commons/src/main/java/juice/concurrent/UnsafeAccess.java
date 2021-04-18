package juice.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author Ricky Fung
 */
public final class UnsafeAccess {
    public static final Unsafe UNSAFE;
    static {
        UNSAFE = getUnsafe();
    }

    private UnsafeAccess() {}

    /**
     * UNSAFE.objectFieldOffset shortcut
     * @param clz
     * @param fieldName
     * @return
     * @throws RuntimeException
     */
    public static long fieldOffset(Class clz, String fieldName) throws RuntimeException {
        try {
            return UNSAFE.objectFieldOffset(clz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static Unsafe getUnsafe() {
        Unsafe instance;
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            instance = (Unsafe) field.get(null);
        } catch (Exception ignored) {
            // Some platforms, notably Android, might not have a sun.misc.Unsafe implementation with a private
            // `theUnsafe` static instance. In this case we can try to call the default constructor, which is sufficient
            // for Android usage.
            try {
                Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
                c.setAccessible(true);
                instance = c.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

}
