package org.fugalang.core.parser.context;

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
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public boolean isFinished(int position) {
        return position >= list.size() && !iterator.hasNext();
    }

    public List<E> getInnerList() {
        int i = list.size();
        while (!isFinished(i)) {
            get(i);
            i++;
        }
        return list;
    }
}
