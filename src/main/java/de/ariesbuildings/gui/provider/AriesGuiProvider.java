package de.ariesbuildings.gui.provider;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.utils.Require;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public abstract class AriesGuiProvider extends QuickGUIProvider implements AriesProvider {

    protected AriesGuiProvider(int size) {
        super(size);
    }

    protected AriesGuiProvider(String title, int size) {
        super(title, size);
    }

    protected AriesGuiProvider(InventoryType type) {
        super(type);
    }

    protected AriesGuiProvider(InventoryType type, String title) {
        super(type, title);
    }

    @Override
    public void provide(AriesPlayer player) {
        Require.nonNull(player, "Cannot provide gui (%s) to null AriesPlayer".formatted(getClass().getName()));
        Require.checkState(player.isValid(), "Could not provide gui (%s) to invalid AriesPlayer".formatted(getClass().getName()));
        super.provide(player.getBase());
    }

    @Override
    public void init(Player player, InventoryContent content) {
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);
        init(ariesPlayer, content);
    }

    protected abstract void init(AriesPlayer player, InventoryContent content);

}
