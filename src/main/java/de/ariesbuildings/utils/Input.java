package de.ariesbuildings.utils;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.config.AriesSystemConfig;
import me.noci.quickutilities.input.Inputs;
import me.noci.quickutilities.input.functions.CanceledInput;
import me.noci.quickutilities.input.functions.InputExecutor;
import me.noci.quickutilities.utils.Require;
import org.bukkit.entity.Player;

import java.util.List;

public class Input {

    public static void title(AriesPlayer player, String title, InputExecutor inputExecutor, WrappedCanceledInput canceledInput) {
        Require.checkState(player != null, "Could not open titled input, AriesPlayer is null");
        title(player.getBase(), title, inputExecutor, canceledInput);
    }

    public static void title(Player player, String title, InputExecutor inputExecutor, CanceledInput canceledInput) {
        var input = Inputs.title(player, AriesSystemConfig.PLAYER_INPUT_CANCEL, title, I18n.translate("input.subtitle.cancel", AriesSystemConfig.PLAYER_INPUT_CANCEL), inputExecutor);
        input.onCancel(canceledInput);
    }

    public static void chat(AriesPlayer player, String notify, InputExecutor inputExecutor, WrappedCanceledInput canceledInput) {
        Require.checkState(player != null, "Could not open chat input, AriesPlayer is null");
        chat(player.getBase(), notify, inputExecutor, canceledInput);
    }

    public static void chat(Player player, String notify, InputExecutor inputExecutor, CanceledInput canceledInput) {
        var input = Inputs.chat(player, notify, AriesSystemConfig.PLAYER_INPUT_CANCEL, inputExecutor);
        input.onCancel(canceledInput);
    }

    public static void sign(AriesPlayer player, int inputLine, List<String> lines, InputExecutor inputExecutor, WrappedCanceledInput canceledInput) {
        Require.checkState(player != null, "Could not open sign chat input, AriesPlayer is null");
        sign(player.getBase(), inputLine, lines, inputExecutor, canceledInput);
    }

    public static void sign(Player player, int inputLine, List<String> lines, InputExecutor inputExecutor, CanceledInput canceledInput) {
        var input = Inputs.sign(player, inputLine, lines, inputExecutor);
        input.onCancel(canceledInput);
    }

    @FunctionalInterface
    public interface WrappedCanceledInput extends CanceledInput {
        void execute(AriesPlayer player);

        default void execute(Player player) {
            execute(AriesSystem.getInstance().getPlayerManager().getPlayer(player));
        }
    }

}
