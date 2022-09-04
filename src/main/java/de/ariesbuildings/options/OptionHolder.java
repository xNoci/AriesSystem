package de.ariesbuildings.options;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.events.PostOptionChangeEvent;
import lombok.Getter;
import org.bukkit.Bukkit;

public abstract class OptionHolder<T extends Option> {

    @Getter private final OptionMap<T> options = new OptionMap<>();

    public boolean isOptionEnabled(T option) {
        try {
            return (boolean) getOption(option);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Cannot check if option '%s' is enabled/disabled, because it is not a boolean option.".formatted(option.getEnum().name()));
        }
    }

    public boolean isOptionDisabled(T option) {
        return !isOptionEnabled(option);
    }

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


        OptionChangeEvent changeEvent = createOptionChangeEvent(option, oldValue, newValue);
        if (changeEvent != null) {
            Bukkit.getPluginManager().callEvent(changeEvent);
            if (changeEvent.isCancelled()) return;

            options.set(option, newValue);
        }

        PostOptionChangeEvent postChangeEvent = createPostOptionChangeEvent(option, oldValue, newValue);
        if (postChangeEvent != null) {
            Bukkit.getPluginManager().callEvent(postChangeEvent);
        }
    }

    protected abstract OptionChangeEvent createOptionChangeEvent(T option, Object oldValue, Object newValue);

    protected abstract PostOptionChangeEvent createPostOptionChangeEvent(T option, Object oldValue, Object newValue);

}
