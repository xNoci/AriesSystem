package de.ariesbuildings.commands.system.commandmethod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class UnknownCommandMethod extends CommandMethod<UnknownCommandMethod> {

    public static UnknownCommandMethod create(Method method) {
        String permission = CommandMethodFactory.parseCommandPermission(method, null);
        int commandArgs = CommandMethodFactory.parseCommandArgs(method, -1);
        boolean requirePlayer = CommandMethodFactory.methodRequirePlayer(method);


        return new UnknownCommandMethod(method, permission, commandArgs, requirePlayer);
    }

    protected UnknownCommandMethod(Method method, String permission, int commandArgs, boolean requiresPlayer) {
        super(method, permission, commandArgs, requiresPlayer);
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return true;
    }

    @Override
    protected boolean doesCommandMatch(CommandSender sender, String[] args) {
        return true;
    }

    @Override
    protected MatchPriority calculatePriority(UnknownCommandMethod other, CommandSender sender, String[] args) {
        boolean needsPlayerAsSender = sender instanceof Player;
        boolean sameCommandSenderType = (this.requiresPlayer && other.requiresPlayer) || (!this.requiresPlayer && !other.requiresPlayer);

        if (sameCommandSenderType) {
            return MatchPriority.EQUAL;
        }

        if (needsPlayerAsSender) {
            return this.requiresPlayer ? MatchPriority.THIS : MatchPriority.OTHER;
        } else {
            return !this.requiresPlayer ? MatchPriority.THIS : MatchPriority.OTHER;
        }
    }

}
