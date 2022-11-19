package de.ariesbuildings.managers;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.Permission;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class VanishManager {

    private BukkitTask vanishRunnable;

    public VanishManager() {
        this.vanishRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                sendActionbar();
            }
        }.runTaskTimerAsynchronously(AriesSystem.getInstance(), 0, 20);
    }

    public static void sendActionbar() {
        AriesSystem.getInstance().getPlayerManager().getPlayers().forEach(player -> {
            String actionBar = player.getOptions().isEnabled(PlayerOption.VANISH) ? I18n.translate("actionbar.vanish_enabled") : " ";
            player.getBase().sendActionBar(actionBar);
        });
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

    public void stopTask() {
        if (vanishRunnable == null) return;
        vanishRunnable.cancel();
        vanishRunnable = null;
    }

}
