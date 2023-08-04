package de.ariesbuildings.managers;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.Permission;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.Scheduler;

public class VanishManager {

    static {
        Scheduler.repeat(BukkitUnit.SECONDS.toTicks(1) / 2, VanishManager::updateActionBar);
    }

    private static void updateActionBar() {
        AriesSystem.getInstance().getPlayerManager().getPlayers().stream()
                .filter(player -> player.getOptions().isEnabled(PlayerOption.VANISH))
                .forEach(player -> player.sendActionBar(I18n.translate("actionbar.vanish_enabled")));
    }

    public static void updatePlayerVisibility() {
        AriesSystem.getInstance().getPlayerManager().getPlayers().forEach(player -> {
            boolean vanish = player.getOptions().isEnabled(PlayerOption.VANISH);

            AriesSystem.getInstance().getPlayerManager().getPlayers().forEach(target -> {
                if (target.getUUID().equals(player.getUUID())) return;

                if (!vanish || target.getBase().hasPermission(Permission.PLAYER_OPTION_VANISH)) {
                    target.getBase().showPlayer(AriesSystem.getInstance(), player.getBase());
                } else {
                    target.getBase().hidePlayer(AriesSystem.getInstance(), player.getBase());
                }
            });
        });
    }

}
