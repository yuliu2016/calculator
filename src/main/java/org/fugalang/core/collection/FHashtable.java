package org.fugalang.core.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class FHashtable<K, V> implements Map<K, V> {

    FKeySet<K> keySet = new FKeySet<>();
    V[] values;
    int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

    @Override
    public V get(Object key) {
        int i = keySet.indexFor(key);
        return values[i];
    }

    @Override
    public V put(K key, V value) {
        int i = keySet.indexFor(key);
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

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Collection<V> values() {
        return Arrays.asList(values);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
