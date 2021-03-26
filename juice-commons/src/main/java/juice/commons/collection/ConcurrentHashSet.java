package juice.commons.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ricky Fung
 */
public class ConcurrentHashSet<E> extends AbstractSet<E>
        implements Set<E> {

    /**
     * The default initial capacity
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    private final ConcurrentHashMap<E, Boolean> map;

    public ConcurrentHashSet() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ConcurrentHashSet(int initialCapacity) {
        this.map = new ConcurrentHashMap<>(initialCapacity);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return this.map.put(e, Boolean.TRUE);
    }

    @Override
    public boolean remove(Object o) {
        return this.map.remove(o);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

}
