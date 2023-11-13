package customArrayList;

import java.util.Collection;
import java.util.Comparator;

public interface CustomList<E> {

    void add(int index, E element);

    void add(E element);

    void addAll(Collection<? extends E> c);

    void clear();

    void sort(Comparator<? super E> c);

    E get(int index);

    boolean isEmpty();

    E remove(int index);

    boolean remove(Object o);

    int size();

    String toString();

    boolean equals(Object obj);

    int hashCode();
}
