package de.ariesbuildings.commands.system;

import de.ariesbuildings.commands.system.annotations.CommandArgs;
import de.ariesbuildings.commands.system.annotations.CommandPermission;
import de.ariesbuildings.commands.system.annotations.Subcommand;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class RegisteredCommandMethod {

    public static RegisteredCommandMethod create(Method method) {
        if (method.getParameterCount() > 2)
            throw new IllegalArgumentException("Command method cannot have more than 2 arguments.");

        if (Arrays.stream(method.getParameters())
                .anyMatch(para -> {
                    Class<?> type = para.getType();
                    return !CommandSender.class.isAssignableFrom(type) && !type.equals(String[].class);
                })) {
            throw new IllegalStateException("Command method can only contain %s and %s as type.".formatted(CommandSender.class, String[].class));
        }

        String permission = null;
        String[] subcommandNames = null;
        int commandArgs = -1;
        boolean subcommand = false;
        boolean requirePlayer = false;

        if (method.isAnnotationPresent(CommandPermission.class)) {
            permission = method.getDeclaredAnnotation(CommandPermission.class).value();
        }

        if (method.isAnnotationPresent(Subcommand.class)) {
            subcommandNames = method.getDeclaredAnnotation(Subcommand.class).value().split(" ");
            subcommand = true;
        }

        if (method.isAnnotationPresent(CommandArgs.class)) {
            commandArgs = method.getDeclaredAnnotation(CommandArgs.class).value();
        }

        for (Parameter parameter : method.getParameters()) {
            if (!CommandSender.class.isAssignableFrom(parameter.getType())) continue;
            if (Player.class.isAssignableFrom(parameter.getType())) {
                requirePlayer = true;
                break;
            }
        }

        return new RegisteredCommandMethod(method, permission, subcommandNames, commandArgs, subcommand, requirePlayer);
    }

    private final Method method;
    private final String permission;
    private final String[] subcommandNames;
    private final int commandArgs;
    private final boolean subcommand;
    private final boolean requiresPlayer;

    private RegisteredCommandMethod(Method method, String permission, String[] subcommandNames, int commandArgs, boolean subcommand, boolean requiresPlayer) {
        this.method = method;
        this.permission = permission;
        this.subcommandNames = subcommandNames;
        this.commandArgs = commandArgs;
        this.subcommand = subcommand;
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

    public boolean hasPermission(CommandSender sender) {
        if (permission == null || StringUtils.isBlank(permission)) return true;
        return sender.hasPermission(permission);
    }

    public boolean matches(CommandSender sender, String[] args) {
        if (requiresPlayer && !(sender instanceof Player)) return false;

        if (!subcommand) {
            return matchesDefaultCommand(args);
        }
        return matchesSubcommandName(args);
    }

    private boolean hasFixedArgCount() {
        return commandArgs != -1;
    }

    private boolean matchesDefaultCommand(String[] args) {
        if (!hasFixedArgCount()) return true;
        return commandArgs == args.length;
    }

    private boolean matchesSubcommandName(String[] args) {
        String[] trimmedArgs = trimSubcommandArgs(args);
        if (hasFixedArgCount() && commandArgs != trimmedArgs.length) return false;

        if (args.length < subcommandNames.length) return false;
        for (int i = 0; i < subcommandNames.length; i++) {
            if (subcommandNames[i].equalsIgnoreCase("*")) continue;
            if (!subcommandNames[i].equalsIgnoreCase(args[i])) return false;
        }

        return true;
    }

    private String[] trimSubcommandArgs(String[] args) {
        int argsToRemove = subcommandNames.length;
        int newArrayLength = args.length - argsToRemove;
        if (newArrayLength <= 0) return new String[0];
        return Arrays.copyOfRange(args, argsToRemove, args.length);
    }


    private static final int PRIOR_THIS = -1;
    private static final int PRIOR_OTHER = 1;
    private static final int PRIOR_EQUAL = 0;

    /* Choose between the commands with one would fit better
    -1 -> this has priority
    0 -> equal priority
    1 -> other has priority
    */
    public int bestMatch(@NotNull RegisteredCommandMethod other, CommandSender sender, String[] args) {
        if (this.subcommand && other.subcommand) return bestSubcommandMatch(other, sender, args);
        if (!this.subcommand && !other.subcommand) return bestDefaultCommandMatch(other, sender, args);
        return PRIOR_EQUAL;
    }

    /* Choose between the commands with one would fit better
    -1 -> this has priority
    0 -> equal priority
    1 -> other has priority
    */
    private int bestDefaultCommandMatch(@NotNull RegisteredCommandMethod other, CommandSender sender, String[] args) {
        boolean playerProvided = sender instanceof Player;
        boolean samePlayerRequirements = (this.requiresPlayer && other.requiresPlayer) || (!this.requiresPlayer && !other.requiresPlayer);
        int argsLength = args.length;

        //PRIOR_EQUAL because none of them should be executed
        if (this.commandArgs > argsLength && other.commandArgs > argsLength) return PRIOR_EQUAL;
        //PRIOR_OTHER because this command shouldn't be executed
        if (this.commandArgs > argsLength) return PRIOR_OTHER;
        //PRIOR_THIS because other command shouldn't be executed
        if (other.commandArgs > argsLength) return PRIOR_THIS;

        //Both commands need same amount of args
        if (this.commandArgs == other.commandArgs) {

            // Player is not provided so find command that doesn't need player
            if (samePlayerRequirements) {
                return PRIOR_EQUAL;
            } else if (!playerProvided) {
                if (!this.requiresPlayer) return PRIOR_THIS;
                else return PRIOR_OTHER;
            } else {
                if (this.requiresPlayer) return PRIOR_THIS;
                else return PRIOR_OTHER;
            }
        }

        //Both commands need less arguments than given
        if (this.commandArgs < argsLength && other.commandArgs < argsLength) {
            // Player is not provided so find command that doesn't need player
            if (samePlayerRequirements) {
                return PRIOR_EQUAL;
            } else if (!playerProvided) {
                if (!this.requiresPlayer) return PRIOR_THIS;
                else return PRIOR_OTHER;
            } else {
                if (this.requiresPlayer) return PRIOR_THIS;
                else return PRIOR_OTHER;
            }
        }

        return PRIOR_EQUAL;
    }

    /* Choose between the commands with one would fit better
    -1 -> this has priority
    0 -> equal priority
    1 -> other has priority
    */
    private int bestSubcommandMatch(@NotNull RegisteredCommandMethod other, CommandSender sender, String[] args) {
        if (this.subcommandNames.length < args.length && other.subcommandNames.length < args.length) return PRIOR_EQUAL;
        if (this.subcommandNames.length < args.length) return PRIOR_OTHER;
        if (other.subcommandNames.length < args.length) return PRIOR_THIS;

        int minArgs = Math.min(this.subcommandNames.length, other.subcommandNames.length);

        for (int i = 0; i < minArgs; i++) {
            String arg = args[i];
            String thisArg = this.subcommandNames[i];
            String otherArg = other.subcommandNames[i];

            if ((thisArg.equalsIgnoreCase("*") && otherArg.equalsIgnoreCase("*")) || ((thisArg.equalsIgnoreCase(arg) && otherArg.equalsIgnoreCase(arg))))
                continue;
            if (thisArg.equalsIgnoreCase(arg)) return PRIOR_THIS;
            if (otherArg.equalsIgnoreCase(arg)) return PRIOR_OTHER;
        }

        return PRIOR_EQUAL;
    }

}
