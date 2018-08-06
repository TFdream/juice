package juice.core.util.collection;

import juice.core.util.AssertUtils;
import java.util.Collection;

/**
 * @author Ricky Fung
 */
public abstract class Iterables {

    /**
     * Adds all elements in {@code iterable} to {@code collection}.
     *
     * @return {@code true} if {@code collection} was modified as a result of this
     *     operation.
     */
    public static <T> boolean addAll(
            Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
        AssertUtils.notNull(addTo);
        AssertUtils.notNull(elementsToAdd);
        if (elementsToAdd instanceof Collection) {
            Collection<? extends T> c = Collections2.cast(elementsToAdd);
            return addTo.addAll(c);
        }
        return Iterators.addAll(addTo, elementsToAdd.iterator());
    }

}
