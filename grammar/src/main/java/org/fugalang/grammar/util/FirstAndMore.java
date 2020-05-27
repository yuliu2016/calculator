package org.fugalang.grammar.util;

import java.util.Iterator;
import java.util.function.Function;

public class FirstAndMore<I, O> implements Iterable<O> {

    private final O first;
    private final Iterator<I> more;
    private final Function<I, O> converter;
    private final Iterator<O> iterator;

    private FirstAndMore(O first, Iterator<I> more, Function<I, O> converter) {
        this.first = first;
        this.more = more;
        this.converter = converter;
        iterator = new FAMIterator();
    }

    @Override
    public Iterator<O> iterator() {
        return iterator;
    }

    private class FAMIterator implements Iterator<O> {

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
        public O next() {
            if (didEmitFirst) {
                I next = more.next();
                return converter.apply(next);
            } else {
                didEmitFirst = true;
                return first;
            }
        }
    }

    public static <E> Iterable<E> of(E first, Iterable<E> more) {
        return of(first, more, e -> e);
    }

    public static <E> Iterable<E> of(E first, Iterator<E> more) {
        return of(first, more, e -> e);
    }

    public static <I, O> Iterable<O> of(O first, Iterable<I> more, Function<I, O> converter) {
        return of(first, more.iterator(), converter);
    }

    public static <I, O> Iterable<O> of(O first, Iterator<I> more, Function<I, O> converter) {
        return new FirstAndMore<>(first, more, converter);
    }
}
