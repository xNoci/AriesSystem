package de.ariesbuildings;

import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);
    }

}
