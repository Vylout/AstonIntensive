package sorters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

public class QuicksortTest {

    @Test
    public void testSort() {
        Integer[] a = {5, 2, 4, 3, 1};
        Integer[] b = {1, 2, 3, 4, 5};
        Comparator<Integer> c = Comparator.naturalOrder();
        Quicksort.sort(a, c);

        Assert.assertArrayEquals(b, a);

    }
}