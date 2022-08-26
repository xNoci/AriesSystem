package de.ariesbuildings.commands.system.commandmethod;

import de.ariesbuildings.commands.system.BaseCommand;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public abstract class CommandMethod<T extends CommandMethod<?>> {

    protected final Method method;
    protected final String permission;
    protected final int commandArgs;
    protected final boolean requiresPlayer;


    protected CommandMethod(Method method, String permission, int commandArgs, boolean requiresPlayer) {
        this.method = method;
        this.permission = permission;
        this.commandArgs = commandArgs;
        this.requiresPlayer = requiresPlayer;
    }

    @SneakyThrows
    public void execute(BaseCommand command, CommandSender sender, String[] args) {
        Object[] params = new Object[method.getParameterCount()];

        for (int i = 0; i < params.length; i++) {
            Parameter param = method.getParameters()[i];
            if (param.getType().equals(String[].class)) params[i] = args;
            if (CommandSender.class.isAssignableFrom(param.getType())) params[i] = sender;
        }

        method.setAccessible(true);
        method.invoke(command, params);
    }

    protected boolean hasFixedArgCount() {
        return commandArgs != -1;
    }

    public boolean hasPermission(CommandSender sender) {
        if (!needsPermission()) return true;
        return sender.hasPermission(permission);
    }

    public boolean needsPermission() {
        return permission != null && !StringUtils.isBlank(permission);
    }

    public boolean matches(CommandSender sender, String[] args) {
        if (requiresPlayer && !(sender instanceof Player)) return false;
        return doesCommandMatch(sender, args);
    }

    public int findBestMatch(@NotNull T other, CommandSender sender, String[] args) {
        return calculatePriority(other, sender, args).priority;
    }

    protected abstract boolean doesCommandMatch(CommandSender sender, String[] args);

    protected abstract MatchPriority calculatePriority(T other, CommandSender sender, String[] args);

    protected enum MatchPriority {

        THIS(-1),
        OTHER(1),
        EQUAL(0);

        private final int priority;

        MatchPriority(int priority) {
            this.priority = priority;
        }

    }

}
