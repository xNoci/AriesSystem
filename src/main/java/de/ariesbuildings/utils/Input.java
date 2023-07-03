package de.ariesbuildings.utils;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import me.noci.quickutilities.input.BasePlayerInput;
import me.noci.quickutilities.input.PlayerChatInput;
import me.noci.quickutilities.input.TitledPlayerChatInput;
import org.bukkit.entity.Player;

public class Input {

    public static void title(AriesPlayer player, String title, BasePlayerInput.InputExecutor inputExecutor, BasePlayerInput.CanceledInput canceledInput) {
        title(player.getBase(), title, inputExecutor, canceledInput);
    }

    public static void title(Player player, String title, BasePlayerInput.InputExecutor inputExecutor, BasePlayerInput.CanceledInput canceledInput) {
        var input = new TitledPlayerChatInput(AriesSystem.getInstance(), player, AriesSystemConfig.PLAYER_INPUT_CANCEL, inputExecutor, title, I18n.translate("input.subtitle.cancel", AriesSystemConfig.PLAYER_INPUT_CANCEL));
        input.onCancel(canceledInput);
    }

    public static void chat(AriesPlayer player, String notify, BasePlayerInput.InputExecutor inputExecutor, BasePlayerInput.CanceledInput canceledInput) {
        chat(player.getBase(), notify, inputExecutor, canceledInput);
    }

    public static void chat(Player player, String notify, BasePlayerInput.InputExecutor inputExecutor, BasePlayerInput.CanceledInput canceledInput) {
        var input = new PlayerChatInput(AriesSystem.getInstance(), player, notify, AriesSystemConfig.PLAYER_INPUT_CANCEL, inputExecutor);
        input.onCancel(canceledInput);
    }

}
