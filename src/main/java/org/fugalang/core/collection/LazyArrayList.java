package org.fugalang.core.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LazyArrayList<E> {
    private final List<E> list = new ArrayList<>();
    private final Iterator<E> iterator;

    public LazyArrayList(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    public E get(int index) {
        if (index < 0 || index > list.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (index == list.size()) {
            if (iterator.hasNext()) {
                list.add(iterator.next());
            } else {
                throw new ArrayIndexOutOfBoundsException(
                        "Index " + index + " out of bounds for list" + list);
            }
        }
        return list.get(index);
    }

    public boolean didFinish(int position) {
        return position >= list.size() && !iterator.hasNext();
    }

    public E getLast() {
        return list.get(list.size() - 1);
    }

    public List<E> getInnerList() {
        int i = list.size();
        while (!didFinish(i)) {
            get(i);
            i++;
        }
        return list;
    }
}
