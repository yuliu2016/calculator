package org.fugalang.core.collection;

import java.util.*;
import java.util.function.Function;

public class FHashtable<K, V> implements Map<K, V> {

    public static final int INITIAL_SIZE = 8;

    public static class Keys<K> {
        int size;
        int tsize;
        Entry<K>[] ordered;
        Entry<K>[] entries;

        @SuppressWarnings("unchecked")
        public Keys(int initialSize) {
            this.size = initialSize;
            ordered = new Entry[initialSize];
            entries = new Entry[initialSize];
        }
    }

    public static class Entry<K> {
        int hash;
        K key;
        int index;

        public Entry(int hash, K key, int index) {
            this.hash = hash;
            this.key = key;
            this.index = index;
        }
    }

    Keys<K> keys = new Keys<>(INITIAL_SIZE);
    @SuppressWarnings("unchecked")
    V[] values = (V[]) new Object[INITIAL_SIZE];

    @Override
    public int size() {
        return keys.size;
    }

    @Override
    public boolean isEmpty() {
        return keys.size == 0;
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean containsKey(Object key) {
        return keySet.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (V v : values) {
            if (v != null && v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private int indexFor(Object key) {
        int hash = key.hashCode();
        int b = hash % keys.tsize;
        while (b < keys.size) {
            Entry<K> e = keys.entries[b];
            if (e == null) {
                return -1;
            }
            if (e.hash != hash) {
                throw new IllegalStateException();
            }
            if (e.key.equals(key)) {
                return b;
            }
            b = b + 1;
        }
        return -1;
    }

    @Override
    public V get(Object key) {
        int i = indexFor(Objects.requireNonNull(key));
        return i == -1 ? null : values[i];
    }

    @Override
    public V put(K key, V value) {
        int i = indexFor(Objects.requireNonNull(key));
        V old = values[i];
        values[i] = value;
        return old;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public void clear() {

    }

    Set<K> keySet = null;

    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new SetView<>(e -> e.key);
        }
        return keySet;
    }

    Set<V> valueSet = null;

    @Override
    public Collection<V> values() {
        if (valueSet == null) {
            valueSet = new SetView<>(e -> values[e.index]);
        }
        return valueSet;
    }

    Set<Map.Entry<K, V>> entrySet = null;

    private class MapEntry implements Map.Entry<K, V> {

        final Entry<K> e;

        public MapEntry(Entry<K> e) {
            this.e = e;
        }

        @Override
        public K getKey() {
            return e.key;
        }

        @Override
        public V getValue() {
            int i = e.index;
            return values[i];
        }

        @Override
        public V setValue(V value) {
            int i = e.index;
            V old = values[i];
            values[i] = value;
            return old;
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new SetView<>(MapEntry::new);
        }
        return entrySet;
    }


    private class SetView<E> implements Set<E> {

        private SetView(Function<Entry<K>, E> f) {

        }

        @Override
        public int size() {
            return keys.size;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    }
}
