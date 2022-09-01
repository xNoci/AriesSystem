package de.ariesbuildings.options;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.objects.AriesPlayer;
import org.bukkit.Bukkit;

public enum PlayerOption implements Option {
    DEFAULT_GAMEMODE("Default gamemode");

    private final String name;

    PlayerOption(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OptionType getType() {
        return OptionType.PLAYER_OPTION;
    }

    public String getValueAsString(AriesPlayer player) {
        return player.getOptions().get(this);
    }

    public boolean getValueAsBoolean(AriesPlayer player) {
        return player.getOptions().get(this).equals("true");
    }

    public int getValueAsInt(AriesPlayer player) {
        try {
            return Integer.parseInt(player.getOptions().get(this));
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    public double getValueAsDouble(AriesPlayer player) {
        try {
            return Double.parseDouble(player.getOptions().get(this));
        } catch(NumberFormatException e) {
            return 0.0;
        }
    }

    public void setValue(AriesPlayer player, String value) {
        player.getOptions().put(this, value);
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, player.getPlayer()));
    }

    public void setValue(AriesPlayer player, boolean value) {
        player.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, player.getPlayer()));
    }

    public void setValue(AriesPlayer player, int value) {
        player.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, player.getPlayer()));
    }

    public void setValue(AriesPlayer player, double value) {
        player.getOptions().put(this, String.valueOf(value));
        Bukkit.getPluginManager().callEvent(new OptionChangeEvent(this, player.getPlayer()));
    }

}
