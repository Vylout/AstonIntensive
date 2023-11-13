package sorters;

import java.util.Comparator;

public class Quicksort<E> {

    public static <E> void sort(E[] array, Comparator<? super E> c) {
        quicksort(array, 0, array.length - 1, c);
    }

    private static <E> void quicksort(E[] array, int low, int high, Comparator<? super E> c) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, c);
            quicksort(array, low, pivotIndex - 1, c);
            quicksort(array, pivotIndex + 1, high, c);
        }
    }

    private static <E> int partition(E[] array, int low, int high, Comparator<? super E> c) {
        E pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (c.compare(array[j], pivot) < 0) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private static <E> void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
