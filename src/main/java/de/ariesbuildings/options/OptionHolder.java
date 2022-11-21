package de.ariesbuildings.options;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.events.PostOptionChangeEvent;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.world.AriesWorld;
import lombok.Getter;
import org.bukkit.Bukkit;

public class OptionHolder<T extends Option> {

    @Getter private final OptionMap<T> options = new OptionMap<>();
    private final AriesPlayer player;
    private final AriesWorld world;

    public OptionHolder(AriesPlayer player) {
        this(player, null);
    }

    public OptionHolder(AriesWorld world) {
        this(null, world);
    }

    public OptionHolder(AriesPlayer player, AriesWorld world) {
        this.player = player;
        this.world = world;
    }

    public boolean isEnabled(T option) {
        try {
            return (boolean) get(option);
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Cannot check if option '%s' is enabled/disabled, because it is not a boolean option.".formatted(option.toString()));
        }
    }

    public boolean isDisabled(T option) {
        return !isEnabled(option);
    }

    public Object get(T option) {
        return get(option, option.getValueType());
    }

    public <V> V get(T option, Class<V> type) {
        V value = options.get(option, type);
        if (value == null) return (V) option.getDefaultValue();
        return value;
    }

    public <V> void set(T option, V newValue) {
        V oldValue = (V) get(option, option.getValueType());

        OptionChangeEvent changeEvent = new OptionChangeEvent(option, oldValue, newValue, player, world);
        Bukkit.getPluginManager().callEvent(changeEvent);
        if (changeEvent.isCancelled()) return;

        options.set(option, newValue);


        PostOptionChangeEvent postChangeEvent = new PostOptionChangeEvent(option, oldValue, newValue, player, world);
        Bukkit.getPluginManager().callEvent(postChangeEvent);
    }

    public void setOptions(OptionMap<T> options) {
        for (T key : options.getKeys()) {
            this.options.set(key, options.get(key));
        }
    }

}
