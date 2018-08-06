package juice.core.util.collection;

import juice.core.util.AssertUtils;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Ricky Fung
 */
public abstract class Maps {

    /**
     * The largest power of two that can be represented as an {@code int}.
     */
    public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

    // HashMap
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * Creates a {@code HashMap} instance, with a high enough "initial capacity"
     * that it <i>should</i> hold {@code expectedSize} elements without growth.
     * This behavior cannot be broadly guaranteed, but it is observed to be true
     * for OpenJDK 1.6. It also can't be guaranteed that the method isn't
     * inadvertently <i>oversizing</i> the returned map.
     *
     * @param expectedSize the number of elements you expect to add to the
     *        returned map
     * @return a new, empty {@code HashMap} with enough capacity to hold {@code
     *         expectedSize} elements without resizing
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     */
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(
            int expectedSize) {
        return new HashMap<K, V>(capacity(expectedSize));
    }

    public static <K, V> HashMap<K, V> newHashMapWithCapacity(
            int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    public static <K, V> HashMap<K, V> newHashMap(
            Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    // LinkedHashMap

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithCapacity(int initialCapacity) {
        return new LinkedHashMap<K, V>(initialCapacity);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(
            Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    // ConcurrentMap
    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<K, V>();
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMapWithCapacity(int initialCapacity) {
        return new ConcurrentHashMap<K, V>(initialCapacity);
    }

    // TreeMap
    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap<K, V>(map);
    }


    /**
     * Returns a capacity that is sufficient to keep the map from being resized as
     * long as it grows no larger than expectedSize and the load factor is >= its
     * default (0.75).
     */
    static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            AssertUtils.isTrue(expectedSize > 0, "expectedSize");
            return expectedSize + 1;
        }
        if (expectedSize < MAX_POWER_OF_TWO) {
            return expectedSize + expectedSize / 3;
        }
        return Integer.MAX_VALUE; // any large value
    }
}
