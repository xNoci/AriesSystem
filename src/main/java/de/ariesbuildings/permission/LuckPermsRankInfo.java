package de.ariesbuildings.permission;

import de.ariesbuildings.hooks.LuckPermsHook;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public class LuckPermsRankInfo extends DefaultRankInfo {

    public LuckPermsRankInfo(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getName() {
        return getGroupValue(Group::getName, super.getName());
    }

    @Override
    public String getDisplayname() {
        return getGroupValue(Group::getDisplayName, super.getDisplayname());
    }

    @Override
    public ChatColor getColor() {
        return getMetaDataValue("color", value -> {
            if (value.startsWith("§") && value.length() >= 2) {
                return ChatColor.getByChar(value.charAt(1));
            } else {
                return ChatColor.valueOf(value);
            }
        }, super.getColor());
    }

    @Override
    protected String prefix() {
        return getMetaData(CachedMetaData::getPrefix, StringUtils::isNotBlank, super.prefix());
    }

    @Override
    protected int sortID() {
        return getMetaDataValue("sortID", Integer::parseInt, super.sortID());
    }

    private Optional<Group> getHighestGroup() {
        return LuckPermsHook.getUserGroup(owner);
    }

    private CachedMetaData getMetaData() {
        return getGroupValue(group -> group.getCachedData().getMetaData(), null);
    }

    private <T> T getGroupValue(Function<Group, T> group, T def) {
        return getHighestGroup()
                .map(group)
                .orElse(def);
    }

    private <T> T getMetaDataValue(String key, Function<String, T> valueTransformer, T def) {
        CachedMetaData metaData = getMetaData();
        if (metaData == null) return def;
        return metaData.getMetaValue(key, valueTransformer).orElse(def);
    }

    private <T> T getMetaData(Function<CachedMetaData, T> data, Predicate<T> tester, T def) {
        CachedMetaData metaData = getMetaData();
        if (metaData == null) return def;
        T query = data.apply(metaData);
        return tester.test(query) ? query : def;
    }

}
