package juice.core.util.collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Ricky Fung
 */
public abstract class Sets {

    // HashSet
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<E>();
    }

    public static <E> HashSet<E> newHashSet(E... elements) {
        HashSet<E> set = newHashSetWithExpectedSize(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    /**
     * Creates a {@code HashSet} instance, with a high enough "initial capacity"
     * that it <i>should</i> hold {@code expectedSize} elements without growth.
     * This behavior cannot be broadly guaranteed, but it is observed to be true
     * for OpenJDK 1.6. It also can't be guaranteed that the method isn't
     * inadvertently <i>oversizing</i> the returned set.
     *
     * @param expectedSize the number of elements you expect to add to the
     *        returned set
     * @return a new, empty {@code HashSet} with enough capacity to hold {@code
     *         expectedSize} elements without resizing
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     */
    public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
        return new HashSet<E>(Maps.capacity(expectedSize));
    }

    // ConcurrentHashSet
    public static <E> Set<E> newConcurrentHashSet() {
        return new ConcurrentHashSet<E>();
    }

    public static <E> Set<E> newConcurrentHashSet(
            Iterable<? extends E> elements) {
        Set<E> set = newConcurrentHashSet();
        Iterables.addAll(set, elements);
        return set;
    }

    // LinkedHashSet

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<E>();
    }

    /**
     * Creates a {@code LinkedHashSet} instance, with a high enough "initial
     * capacity" that it <i>should</i> hold {@code expectedSize} elements without
     * growth. This behavior cannot be broadly guaranteed, but it is observed to
     * be true for OpenJDK 1.6. It also can't be guaranteed that the method isn't
     * inadvertently <i>oversizing</i> the returned set.
     *
     * @param expectedSize the number of elements you expect to add to the
     *        returned set
     * @return a new, empty {@code LinkedHashSet} with enough capacity to hold
     *         {@code expectedSize} elements without resizing
     * @throws IllegalArgumentException if {@code expectedSize} is negative
     */
    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(
            int expectedSize) {
        return new LinkedHashSet<E>(Maps.capacity(expectedSize));
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(
            Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return new LinkedHashSet<E>((Collection<E>) elements);
        }
        LinkedHashSet<E> set = newLinkedHashSet();
        Iterables.addAll(set, elements);
        return set;
    }

    // TreeSet

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet<E>();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(
            Iterable<? extends E> elements) {
        TreeSet<E> set = newTreeSet();
        Iterables.addAll(set, elements);
        return set;
    }

    // CopyOnWriteArraySet
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<E>();
    }

    /**
     * Creates a {@code CopyOnWriteArraySet} instance containing the given elements.
     *
     * @param elements the elements that the set should contain, in order
     * @return a new {@code CopyOnWriteArraySet} containing those elements
     */
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(
            Iterable<? extends E> elements) {
        // We copy elements to an ArrayList first, rather than incurring the
        // quadratic cost of adding them to the COWAS directly.
        Collection<? extends E> elementsCollection = (elements instanceof Collection)
                ? Collections2.cast(elements)
                : Lists.newArrayList(elements);
        return new CopyOnWriteArraySet<E>(elementsCollection);
    }
}
