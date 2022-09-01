package de.ariesbuildings.options;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.objects.AriesWorld;
import org.bukkit.Bukkit;

public enum WorldOption implements Option {
    ANTI_BLOCK_UPDATES("AntiBlockUpdates");

    private final String name;

    WorldOption(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OptionType getType() {
        return OptionType.WORLD_OPTION;
    }

    public String getValueAsString(AriesWorld world) {
        return world.getOptions().get(this);
    }

    public boolean getValueAsBoolean(AriesWorld world) {
        return world.getOptions().get(this).equals("true");
    }

    public int getValueAsInt(AriesWorld world) {
        try {
            return Integer.parseInt(world.getOptions().get(this));
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    public double getValueAsDouble(AriesWorld world) {
        try {
            return Double.parseDouble(world.getOptions().get(this));
        } catch(NumberFormatException e) {
            return 0.0;
        }
    }

    public void setValue(AriesWorld world, String value) {
        world.getOptions().put(this, value);
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, world.getWorld()));
    }

    public void setValue(AriesWorld world, boolean value) {
        world.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, world.getWorld()));
    }

    public void setValue(AriesWorld world, int value) {
        world.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, world.getWorld()));
    }

    public void setValue(AriesWorld world, double value) {
        world.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, world.getWorld()));
    }

}
