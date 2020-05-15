package org.fugalang.core.grammar.util;

import java.util.Iterator;

public class FirstAndMore<E> implements Iterable<E> {

    private final E first;
    private final Iterator<E> more;
    private final Iterator<E> iterator;

    private FirstAndMore(E first, Iterator<E> more) {
        this.first = first;
        this.more = more;
        iterator = new FAMIterator();
    }

    @Override
    public Iterator<E> iterator() {
        return iterator;
    }

    private class FAMIterator implements Iterator<E> {

        private boolean didEmitFirst = false;

        @Override
        public boolean hasNext() {
            if (didEmitFirst) {
                return more.hasNext();
            } else {
                return true;
            }
        }

        @Override
        public E next() {
            if (didEmitFirst) {
                return more.next();
            } else {
                didEmitFirst = true;
                return first;
            }
        }
    }

    public static <E> FirstAndMore<E> of(E first, Iterable<E> more) {
        return of(first, more.iterator());
    }

    public static <E> FirstAndMore<E> of(E first, Iterator<E> more) {
        return new FirstAndMore<>(first, more);
    }
}
