package de.ariesbuildings.gui.provider;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.PageContent;
import me.noci.quickutilities.inventory.PagedQuickGUIProvider;
import me.noci.quickutilities.utils.Require;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public abstract class AriesPagedGuiProvider extends PagedQuickGUIProvider implements AriesProvider {

    protected AriesPagedGuiProvider(int size) {
        super(size);
    }

    protected AriesPagedGuiProvider(String title, int size) {
        super(title, size);
    }

    protected AriesPagedGuiProvider(InventoryType type) {
        super(type);
    }

    protected AriesPagedGuiProvider(InventoryType type, String title) {
        super(type, title);
    }

    @Override
    public void init(Player player, InventoryContent content) {
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);
        init(ariesPlayer, content);
    }

    @Override
    public void initPage(Player player, PageContent content) {
        AriesPlayer ariesPlayer = AriesSystem.getInstance().getPlayerManager().getPlayer(player);
        initPage(ariesPlayer, content);
    }

    @Override
    public void provide(AriesPlayer player) {
        Require.nonNull(player, "Cannot provide gui (%s) to null AriesPlayer".formatted(getClass().getName()));
        super.provide(player.getBase());
    }

    protected abstract void init(AriesPlayer player, InventoryContent content);

    protected abstract void initPage(AriesPlayer player, PageContent content);

}
