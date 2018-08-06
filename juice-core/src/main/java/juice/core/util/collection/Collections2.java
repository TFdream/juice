package juice.core.util.collection;

import java.util.Collection;

/**
 * @author Ricky Fung
 */
public abstract class Collections2 {

    /**
     * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
     */
    public static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }
}
