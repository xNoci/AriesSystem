package de.ariesbuildings.listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.ariesbuildings.I18n;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerListPingPaperListener implements Listener {

    @EventHandler
    public void handleServerList(PaperServerListPingEvent event) {
        event.setMotd(I18n.translate("serverlist.motd"));

        //TODO Remove vanished players from count
        event.setMaxPlayers(event.getNumPlayers() + 1);
    }

}
