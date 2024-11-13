package IteratorForList;

import java.util.Iterator;

public interface listIterator<T> extends Iterator<T> {
    boolean hasNext();
    T next();
    void remove();
    Iterator<T> iterator();
}
