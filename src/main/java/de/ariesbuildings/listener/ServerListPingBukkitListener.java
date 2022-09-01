package de.ariesbuildings.listener;

import de.ariesbuildings.I18n;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingBukkitListener implements Listener {

    @EventHandler
    public void handleServerList(ServerListPingEvent event) {
        event.setMotd(I18n.translate("serverlist.motd"));

        //TODO Remove vanished players from count
        event.setMaxPlayers(event.getNumPlayers() + 1);
    }

}
