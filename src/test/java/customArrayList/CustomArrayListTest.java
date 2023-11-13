package customArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomArrayListTest {

    private static CustomList<Integer> list;

    @Before
    public void before() {
        list = new CustomArrayList<>();
    }

    @Test
    public void testAddElementAndCheckSize() {
        list.add(12);
        list.add(25);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals((Integer)12, list.get(0));
        Assert.assertEquals((Integer)25, list.get(1));
    }

    @Test
    public void testAddElementIndex() {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        Assert.assertEquals(4, list.size());
        list.add(2, 50);
        Assert.assertEquals(5, list.size());
        Assert.assertEquals((Integer)50, list.get(2));
        Assert.assertEquals((Integer)30, list.get(3));
    }

    @Test
    public void testAddAll() {
        list.add(10);
        list.add(20);
        Assert.assertEquals(2, list.size());
        list.addAll(List.of(30, 40, 50));
        Assert.assertEquals(5, list.size());
        Assert.assertEquals((Integer)20, list.get(1));
        Assert.assertEquals((Integer)30, list.get(2));
    }

    @Test
    public void testClear() {
        list.add(10);
        list.add(20);
        Assert.assertEquals(2, list.size());
        list.clear();
        Assert.assertEquals(0, list.size());
    }


    @Test
    public void testIsEmpty() {
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testRemoveObject() {
        list.add(10);
        list.add(20);
        Assert.assertEquals(2, list.size());
        list.remove((Integer)10);
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testRemoveByIndex() {
        list.add(10);
        list.add(20);
        Assert.assertEquals(2, list.size());
        list.remove(1);
        Assert.assertEquals(1, list.size());
    }
}