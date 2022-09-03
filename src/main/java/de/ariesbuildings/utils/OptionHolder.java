package de.ariesbuildings.utils;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.Option;
import org.bukkit.Bukkit;

public abstract class OptionHolder<T extends Option> {

    private final OptionMap<T> options = new OptionMap<>();

    public Object getOption(T option) {
        return getOption(option, option.getValueType());
    }

    public <V> V getOption(T option, Class<V> type) {
        V value = options.get(option, type);
        if (value == null) return (V) option.getDefaultValue();
        return value;
    }

    public <V> void setOption(T option, V newValue) {
        V oldValue = (V) getOption(option, option.getValueType());

        OptionChangeEvent event = createOptionChangeEvent(option, oldValue, newValue);
        if (event != null) {
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) return;

            options.set(option, newValue);
        }
    }

    protected abstract OptionChangeEvent createOptionChangeEvent(T option, Object oldValue, Object newValue);

}
