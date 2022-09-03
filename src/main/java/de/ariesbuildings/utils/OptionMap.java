package de.ariesbuildings.utils;

import com.google.common.collect.Maps;

import java.util.Map;

public class OptionMap<K> {

        private Map<K, Value> DATA = Maps.newConcurrentMap();

        public <T> void set(K key, T value) {
            Value<T> cache =  getCached(key, (Class<T>) value.getClass());
            if (cache.getValue() != null && !cache.getValue().getClass().equals(value.getClass()))
                throw new UnsupportedOperationException(String.format("Used wrong type for %s. Used type '%s' requires type '%s'", key.toString(), value.getClass(), cache.getValue().getClass()));
            cache.setValue(value);
        }

        public Object get(K key) {
            return getCached(key, Object.class).getValue();
        }

        public <T> T get(K key, Class<T> type) {
            return getCached(key, type).getValue();
        }

        public Class<?> getType(K key) {
            if (!DATA.containsKey(key)) return null;
            Value<?> value = DATA.get(key);
            return value.getValue() == null ? null : value.getValue().getClass();
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