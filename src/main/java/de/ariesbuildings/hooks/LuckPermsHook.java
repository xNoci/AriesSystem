package de.ariesbuildings.hooks;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.LuckPermsEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LuckPermsHook extends PluginHook {

    protected LuckPermsHook() {
        super("LuckPerms");
    }

    public static boolean isEnabled() {
        return Singleton.HOOK.enabled();
    }

    public static <T extends LuckPermsEvent> void subscribe(JavaPlugin plugin, Class<T> eventClass, Consumer<? super T> handler) {
        if (!isEnabled()) return;
        EventBus eventBus = LuckPermsProvider.get().getEventBus();
        eventBus.subscribe(plugin, eventClass, handler);
    }

    public static Optional<Group> getUserGroup(UUID uuid) {
        if (!isEnabled()) return Optional.empty();

        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().getUser(uuid);
        List<Group> groups = getGroupsSorted();

        if (user == null) {
            return Optional.of(groups.get(0));
        }

        String groupName = user.getPrimaryGroup();
        return Optional.ofNullable(luckPerms.getGroupManager().getGroup(groupName));
    }

    private static List<Group> getGroupsSorted() {
        LuckPerms luckPerms = LuckPermsProvider.get();
        Set<Group> loadedGroups = luckPerms.getGroupManager().getLoadedGroups();

        return loadedGroups.stream().sorted((o1, o2) -> {
            Integer weightOne = o1.getWeight().orElse(0);
            int weightTwo = o2.getWeight().orElse(0);

            return weightOne.compareTo(weightTwo);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private static class Singleton {
        private static final LuckPermsHook HOOK = new LuckPermsHook();
    }

}
