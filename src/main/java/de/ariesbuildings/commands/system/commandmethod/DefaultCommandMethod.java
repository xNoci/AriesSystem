package de.ariesbuildings.commands.system.commandmethod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

public class DefaultCommandMethod extends CommandMethod<DefaultCommandMethod> {

    public static DefaultCommandMethod create(Method method) {
        String permission = CommandMethodFactory.parseCommandPermission(method, null);
        int commandArgs = CommandMethodFactory.parseCommandArgs(method, -1);
        boolean requirePlayer = CommandMethodFactory.methodRequirePlayer(method);


        return new DefaultCommandMethod(method, permission, commandArgs, requirePlayer);
    }

    protected DefaultCommandMethod(Method method, String permission, int commandArgs, boolean requiresPlayer) {
        super(method, permission, commandArgs, requiresPlayer);
    }

    @Override
    protected boolean doesCommandMatch(CommandSender sender, String[] args) {
        if (!hasFixedArgCount()) return true;
        return commandArgs == args.length;
    }

    @Override
    protected MatchPriority calculatePriority(DefaultCommandMethod other, CommandSender sender, String[] args) {
        boolean playerProvided = sender instanceof Player;
        boolean sameCommandSenderType = (this.requiresPlayer && other.requiresPlayer) || (!this.requiresPlayer && !other.requiresPlayer);

        if (sameCommandSenderType) {
            return MatchPriority.EQUAL;
        }

        if (!playerProvided) {
            return this.requiresPlayer ? MatchPriority.THIS : MatchPriority.OTHER;
        } else {
            return !this.requiresPlayer ? MatchPriority.THIS : MatchPriority.OTHER;
        }
    }
}
