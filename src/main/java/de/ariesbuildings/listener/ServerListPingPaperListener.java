package de.ariesbuildings.listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.objects.AriesPlayer;
import de.ariesbuildings.options.PlayerOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerListPingPaperListener implements Listener {

    @EventHandler
    public void handleServerList(PaperServerListPingEvent event) {
        event.setMotd(I18n.translate("serverlist.motd"));

        long visiblePlayers = AriesSystem.getInstance().getPlayerManager().getPlayers()
                .stream()
                .filter(ariesPlayer -> ariesPlayer.isOptionDisabled(PlayerOption.VANISH))
                .count();

        event.setNumPlayers(Math.toIntExact(visiblePlayers));
        event.setMaxPlayers(event.getNumPlayers() + 1);

        event.getPlayerSample().removeIf(playerProfile -> {
            AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(playerProfile.getId());
            if (player == null) return false;
            return player.isOptionEnabled(PlayerOption.VANISH);
        });
    }

}
