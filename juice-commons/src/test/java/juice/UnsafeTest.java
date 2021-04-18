package juice;

import org.junit.Test;

import static juice.concurrent.UnsafeAccess.getUnsafe;

/**
 * @author Ricky Fung
 */
public class UnsafeTest {
    volatile long value;

    private static final long valueOffset;
    static {
        try {
            Class<?> ak = UnsafeTest.class;
            valueOffset = getUnsafe().objectFieldOffset
                    (ak.getDeclaredField("value"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    final boolean cas(long cmp, long val) {
        return getUnsafe().compareAndSwapLong(this, valueOffset, cmp, val);
    }

    @Test
    public void testCas() {
        System.out.println("修改前:"+value);
        System.out.println(cas(0, 3));
        System.out.println("修改后:"+value);
    }

}
