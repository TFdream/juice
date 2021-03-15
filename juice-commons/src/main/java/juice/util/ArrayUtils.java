package juice.util;

import java.util.Random;

/**
 * @author Ricky Fung
 */
public abstract class ArrayUtils {

    public static boolean isEmpty(short[] arr) {
        return arr==null || arr.length==0;
    }

    public static boolean isEmpty(int[] arr) {
        return arr == null || arr.length==0;
    }

    public static boolean isEmpty(long[] arr) {
        return arr == null || arr.length==0;
    }

    public static boolean isEmpty(float[] arr) {
        return arr == null || arr.length==0;
    }

    public static boolean isEmpty(double[] arr) {
        return arr == null || arr.length==0;
    }

    public static boolean isEmpty(String[] arr) {
        return arr == null || arr.length==0;
    }

    /**
     * Determine whether the given array is empty:
     * i.e. {@code null} or of zero length.
     * @param array the array to check
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    //============

    /**
     * Determine whether the given object is an array:
     * either an Object array or a primitive array.
     * @param obj the object to check
     */
    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    //============
    public static int[] partition(int num, int size) {
        int parLen = (num + size - 1)/size;
        int[] arr = new int[size];
        for (int index=0; index<size; index++) {
            int start = index * parLen;
            int end = Math.min(start + parLen, num);
            if (start > end) {
                arr[index] = 0;
            } else {
                arr[index] = end - start;
            }
        }
        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    //--------

    public static void shuffle(int[] arr) {
        int size = arr.length;
        Random rnd = new Random();
        // Shuffle array
        for (int i=size; i>1; i--) {
            swap(arr, i - 1, rnd.nextInt(i));
        }
    }
}
