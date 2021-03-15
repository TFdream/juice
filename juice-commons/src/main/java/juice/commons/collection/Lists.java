package juice.commons.collection;

import juice.commons.Assertions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ricky Fung
 */
public final class Lists {
    private Lists() {}

    //=========
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int initialCapacity) {
        Assertions.checkNonNegative(initialCapacity,"initialCapacity");
        return new ArrayList<>(initialCapacity);
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    //==========

    public static <T> List<T> asList(T... array) {
        Objects.requireNonNull(array);
        List<T> list = new ArrayList<>(array.length);
        for (T e : array) {
            list.add(e);
        }
        return list;
    }

    //==========
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
        Assertions.notNull(list, "list is null");
        Assertions.isTrue(size > 0, "size cannot be negative");

        //计算总份数(向上取整)
        int partitionSize = (list.size() + size - 1) / size;
        List<List<T>> partitionList = new ArrayList<>(partitionSize);
        for (int index=0; index<partitionSize; index++) {
            int start = index * size;
            int end = Math.min(start + size, list.size());
            partitionList.add(list.subList(start, end));
        }
        return partitionList;
    }

}
