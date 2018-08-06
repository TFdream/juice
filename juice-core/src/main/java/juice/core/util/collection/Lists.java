package juice.core.util.collection;

import juice.core.util.AssertUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ricky Fung
 */
public abstract class Lists {

    // ArrayList
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        AssertUtils.notNull(elements);
        // Avoid integer overflow when a large array is passed in
        int capacity = computeArrayListCapacity(elements.length);
        ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        AssertUtils.notNull(elements);
        // Let ArrayList's sizing logic work, if possible
        return (elements instanceof Collection)
                ? new ArrayList<E>(Collections2.cast(elements))
                : newArrayList(elements.iterator());
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        Iterators.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(
            int initialArraySize) {
        AssertUtils.isTrue(initialArraySize > 0, "initialArraySize");
        return new ArrayList<E>(initialArraySize);
    }

    public static <E> ArrayList<E> newArrayListWithExpectedSize(
            int estimatedSize) {
        return new ArrayList<E>(computeArrayListCapacity(estimatedSize));
    }

    // LinkedList

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    public static <E> LinkedList<E> newLinkedList(
            Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        Iterables.addAll(list, elements);
        return list;
    }

    // CopyOnWriteArrayList
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<E>();
    }

    /**
     * Creates a {@code CopyOnWriteArrayList} instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     * @return a new {@code CopyOnWriteArrayList} containing those elements
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(
            Iterable<? extends E> elements) {
        // We copy elements to an ArrayList first, rather than incurring the
        // quadratic cost of adding them to the COWAL directly.
        Collection<? extends E> elementsCollection = (elements instanceof Collection)
                ? Collections2.cast(elements)
                : newArrayList(elements);
        return new CopyOnWriteArrayList<E>(elementsCollection);
    }
    /**
     * Returns consecutive {@linkplain List#subList(int, int) sublists} of a list,
     * each of the same size (the final list may be smaller). For example,
     * partitioning a list containing {@code [a, b, c, d, e]} with a partition
     * size of 3 yields {@code [[a, b, c], [d, e]]} -- an outer list containing
     * two inner lists of three and two elements, all in the original order.
     *
     * <p>The outer list is unmodifiable, but reflects the latest state of the
     * source list. The inner lists are sublist views of the original list,
     * produced on demand using {@link List#subList(int, int)}, and are subject
     * to all the usual caveats about modification as explained in that API.
     *
     * @param list the list to return consecutive sublists of
     * @param size the desired size of each sublist (the last may be
     *     smaller)
     * @return a list of consecutive sublists
     * @throws IllegalArgumentException if {@code partitionSize} is nonpositive
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        AssertUtils.notNull(list);
        AssertUtils.isTrue(size > 0);
        return (list instanceof RandomAccess)
                ? new RandomAccessPartition<T>(list, size)
                : new Partition<T>(list, size);
    }

    static int computeArrayListCapacity(int arraySize) {
        AssertUtils.isTrue(arraySize > 0, "arraySize");
        return arraySize + (arraySize / 10);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        final List<T> list;
        final int size;

        Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            AssertUtils.checkElementIndex(index, size());
            int start = index * size;
            int end = Math.min(start + size, list.size());
            return list.subList(start, end);
        }

        @Override
        public int size() {
            int ls = list.size();
            int div = (ls + size - 1) / size;
            return div;
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }

    private static class RandomAccessPartition<T> extends Partition<T>
            implements RandomAccess {
        RandomAccessPartition(List<T> list, int size) {
            super(list, size);
        }
    }
}
