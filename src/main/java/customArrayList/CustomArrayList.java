package customArrayList;

import sorters.Quicksort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> implements CustomList<E> {

    private int size = 0;
    private Object[] elementData;
    private static final int INITIAL_CAPACITY = 12;

    public CustomArrayList() {
        this.elementData = new Object[INITIAL_CAPACITY];
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Не верный параметр Capacity");
        }
        this.elementData = new Object[initialCapacity];
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (size >= elementData.length) {
            upCapacity(size);
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public void add(E element) {
        if (size >= elementData.length) {
            upCapacity(size);
        }

        elementData[size++] = element;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        Object[] collectionData = c.toArray();
        int collectionSize = c.size();

        if (size + collectionSize > elementData.length) {
            upCapacity(size + collectionSize);
        }

        System.arraycopy(collectionData, 0, elementData, size, collectionSize);
        size += collectionSize;
    }

    @Override
    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        if (elementData != null) {
            Quicksort.sort((E[]) elementData, c);
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = (E) elementData[index];

        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }

        elementData[size - 1] = null;
        size--;

        return removedElement;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elementData[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomArrayList<?> other = (CustomArrayList<?>) obj;
        if (size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!elementData[i].equals(other.elementData[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elementData);
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Указанный index: " + index + ",не в ходит в диапазон size: " + size);
        }
    }

    private void upCapacity(int requiredCapacity) {
        if (requiredCapacity <= elementData.length) {
            return;
        }
        int newCapacity = Math.max(elementData.length * 2, requiredCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
}
