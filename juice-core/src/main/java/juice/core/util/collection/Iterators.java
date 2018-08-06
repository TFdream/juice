package juice.core.util.collection;

import juice.core.util.AssertUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ricky Fung
 */
public abstract class Iterators {

    /**
     * Adds all elements in {@code iterator} to {@code collection}. The iterator
     * will be left exhausted: its {@code hasNext()} method will return
     * {@code false}.
     *
     * @return {@code true} if {@code collection} was modified as a result of this
     *         operation
     */
    public static <T> boolean addAll(
            Collection<T> addTo, Iterator<? extends T> iterator) {
        AssertUtils.notNull(addTo);
        AssertUtils.notNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }
}
