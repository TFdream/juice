package juice.core.util.collection;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ricky Fung
 */
public class ConcurrentHashSet<E> extends AbstractSet<E>
        implements Set<E>, Serializable {

    private static final long serialVersionUID = 2454657854757543876L;

    private static final int DEFAULT_CAPACITY = 16;

    private final ConcurrentHashMap<E, Boolean> map;  // The backing map
    private transient Set<E> s;

    public ConcurrentHashSet() {
        this(DEFAULT_CAPACITY);
    }

    public ConcurrentHashSet(int initialCapacity) {
        map = new ConcurrentHashMap<>(initialCapacity);
        s = map.keySet();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public boolean add(E e) {
        return map.put(e, Boolean.TRUE) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public Object[] toArray() {
        return s.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public String toString() {
        return s.toString();
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o == this || s.equals(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        s = map.keySet();
    }
}
