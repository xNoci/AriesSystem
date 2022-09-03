package de.ariesbuildings.utils;

import com.google.common.collect.Maps;

import java.util.Map;

public class OptionMap<K> {

    private Map<K, Value> DATA = Maps.newHashMap();

    public <T> void set(K key, T value) {
        Value<T> cache = (Value<T>) getCached(key, value.getClass());
        if (cache.getValue() != null && !cache.getValue().getClass().equals(value.getClass()))
            throw new UnsupportedOperationException(String.format("Used wrong type for %s. Used type '%s' requires type '%s'", key, value.getClass(), cache.getValue().getClass()));
        cache.setValue(value);
    }

    public Object get(K key) {
        return getCached(key, Object.class).getValue();
    }

    public <T> T get(K key, Class<T> type) {
        return getCached(key, type).getValue();
    }

    private <T> Value<T> getCached(K key, Class<T> type) {
        if (DATA.containsKey(key)) return DATA.get(key);
        Value<T> value = new Value<>();
        DATA.put(key, value);
        return value;
    }

    private static class Value<T> {
        private T value = null;

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

}