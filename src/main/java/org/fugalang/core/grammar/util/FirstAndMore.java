package org.fugalang.core.grammar.util;

import java.util.Iterator;

public class FirstAndMore<E> implements Iterable<E> {

    private final E first;
    private final Iterable<E> more;

    private FirstAndMore(E first, Iterable<E> more) {
        this.first = first;
        this.more = more;
    }

    @Override
    public Iterator<E> iterator() {
        return new FAMIterator();
    }

    private class FAMIterator implements Iterator<E> {

        private boolean didEmitFirst = false;
        private Iterator<E> moreIterator;

        @Override
        public boolean hasNext() {
            if (didEmitFirst) {
                return moreIterator.hasNext();
            } else {
                return true;
            }
        }

        @Override
        public E next() {
            if (didEmitFirst) {
                return moreIterator.next();
            } else {
                moreIterator = more.iterator();
                didEmitFirst = true;
                return first;
            }
        }
    }

    public static <E> FirstAndMore<E> of(E first, Iterable<E> more) {
        return new FirstAndMore<>(first, more);
    }
}
