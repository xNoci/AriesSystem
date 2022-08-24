package de.ariesbuildings.commands.system;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.ariesbuildings.I18n;
import de.ariesbuildings.commands.system.annotations.DefaultCommand;
import de.ariesbuildings.commands.system.annotations.Subcommand;
import de.ariesbuildings.commands.system.annotations.UnknownCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BaseCommand {

    @Getter private JavaPlugin plugin;
    @Getter private String name;
    @Getter @Setter(AccessLevel.PROTECTED) private String description = "";
    @Getter @Setter(AccessLevel.PROTECTED) private String usage = "";
    @Getter private List<String> aliases = Lists.newArrayList();

    private List<RegisteredCommandMethod> defaultCommands = Lists.newArrayList();
    private final List<RegisteredCommandMethod> subcommands = Lists.newArrayList();
    private RegisteredCommandMethod unknownCommand;

    public BaseCommand(JavaPlugin plugin, String name, String... aliases) {
        this.plugin = plugin;
        this.name = name;
        this.aliases.addAll(List.of(aliases));
    }

    protected boolean execute(CommandSender sender, String[] args) {
        RegisteredCommandMethod bestMatch = findCommand(sender, args);

        if (bestMatch != null) {

            if (!bestMatch.hasPermission(sender)) {
                sender.sendMessage(I18n.noPermission());
                return true;
            }

            bestMatch.execute(this, sender, args);
            return true;
        }

        if (unknownCommand != null) {
            unknownCommand.execute(this, sender, args);
            return true;
        }

        return false;
    }

    public void register() {
        registerDefaultCommand();
        registerUnknownCommand();
        registerSubcommands();
    }

    private RegisteredCommandMethod findCommand(CommandSender sender, String[] args) {
        Optional<RegisteredCommandMethod> subcommand = subcommands.stream()
                .filter(command -> command.matches(sender, args))
                .min((o1, o2) -> o1.bestMatch(o2, sender, args));


        if (subcommand.isPresent()) return subcommand.get();

        Optional<RegisteredCommandMethod> defaultCommand = defaultCommands.stream()
                .filter(command -> command.matches(sender, args))
                .min((o1, o2) -> o1.bestMatch(o2, sender, args));

        return defaultCommand.orElse(null);
    }


    private void registerDefaultCommand() {
        Set<Method> methods = getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(DefaultCommand.class)) continue;
            try {
                RegisteredCommandMethod defaultCommand = RegisteredCommandMethod.create(method);
                defaultCommands.add(defaultCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (defaultCommands.size() == 0)
            throw new RuntimeException("No 'DefaultCommand' method found for %s".formatted(getClass().getName()));
    }

    private void registerSubcommands() {
        Set<Method> methods = getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(Subcommand.class)) continue;
            try {
                RegisteredCommandMethod subcommand = RegisteredCommandMethod.create(method);
                subcommands.add(subcommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void registerUnknownCommand() {
        Set<Method> methods = getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(UnknownCommand.class)) continue;
            try {
                unknownCommand = RegisteredCommandMethod.create(method);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (unknownCommand == null)
            throw new RuntimeException("No 'UnknownCommand' method found for %s".formatted(getClass().getName()));
    }

    private Set<Method> getMethods() {
        Set<Method> methods = Sets.newLinkedHashSet();
        Collections.addAll(methods, getClass().getDeclaredMethods());
        Collections.addAll(methods, getClass().getMethods());

        return methods;
    }

}
