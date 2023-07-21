package de.ariesbuildings.listener;

import me.noci.quickutilities.events.Events;
import me.noci.quickutilities.quicktab.QuickTab;
import me.noci.quickutilities.scoreboard.QuickBoard;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerWorldChangeListener {

    public PlayerWorldChangeListener() {

        Events.subscribe(PlayerChangedWorldEvent.class)
                .handle(event -> {
                    QuickTab.updateAll();
                    QuickBoard.updateAll();
                });

    }

}
