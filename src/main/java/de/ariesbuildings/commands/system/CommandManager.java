package de.ariesbuildings.commands.system;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class CommandManager {

    private static final MethodHandle COMMAND_MAP;

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle commandMap = null;

        try {
            commandMap = lookup.findVirtual(Bukkit.getServer().getClass(), "getCommandMap", MethodType.methodType(SimpleCommandMap.class));
        } catch(NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        COMMAND_MAP = commandMap;
    }

    @SneakyThrows
    public static void register(BaseCommand command) {
        if(command.getPlugin() == null) throw new IllegalArgumentException("Plugin cannot be null.");
        if(command.getName() == null || StringUtils.isBlank(command.getName()))
            throw new IllegalArgumentException("Command name cannot be null or empty");

        SimpleCommandMap commandMap = (SimpleCommandMap) COMMAND_MAP.invoke(Bukkit.getServer());
        commandMap.register(command.getPlugin().getName(), new CommandContainer(command));
    }

    private static class CommandContainer extends org.bukkit.command.Command implements PluginIdentifiableCommand {
        private final BaseCommand command;

        public CommandContainer(BaseCommand command) {
            super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());
            this.command = command;
        }

        @Override
        public boolean execute(@NotNull CommandSender commandSender, @NotNull String commandLabel, String[] args) {
            return command.execute(commandSender, commandLabel, args);
        }

        @Override
        public Plugin getPlugin() {
            return this.command.getPlugin();
        }
    }

}