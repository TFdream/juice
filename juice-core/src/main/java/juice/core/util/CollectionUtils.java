package juice.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author Ricky Fung
 */
public abstract class CollectionUtils {

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * Null-safe check if the specified collection is not empty.
     * <p>
     * Null returns false.
     *
     * @param coll  the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty.
     * Otherwise, return {@code false}.
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Null-safe check if the specified map is not empty.
     * <p>
     * Null returns false.
     *
     * @param map  the map to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Gets the size of the collection/iterator specified.
     * <p>
     * This method can handles objects as follows
     * <ul>
     * <li>Collection - the collection size
     * <li>Map - the map size
     * <li>Array - the array size
     * </ul>
     *
     * @param object  the object to get the size of, may be null
     * @return the size of the specified collection or 0 if the object was null
     * @throws IllegalArgumentException thrown if object is not recognised
     */
    public static int size(final Object object) {
        if (object == null) {
            return 0;
        }
        int total = 0;
        if (object instanceof Map<?,?>) {
            total = ((Map<?, ?>) object).size();
        } else if (object instanceof Collection<?>) {
            total = ((Collection<?>) object).size();
        } else if (object instanceof Object[]) {
            total = ((Object[]) object).length;
        } else {
            try {
                total = Array.getLength(object);
            } catch (final IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
        }
        return total;
    }
}
