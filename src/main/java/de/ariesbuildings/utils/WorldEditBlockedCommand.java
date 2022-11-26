package de.ariesbuildings.utils;

import com.google.common.collect.Lists;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.world.AriesWorld;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldEditBlockedCommand implements BlockedCommand {

    public static final List<BlockedCommand> BLOCKED_COMMAND_LIST = Lists.newArrayList();

    static {
        //History Control
        addWorldEdit("undo");
        addWorldEdit("redo");
        addWorldEdit("clearhistory", false);

        //Region Selection
        addWorldEdit("wand");
        addWorldEdit("toggleeditwand", false);
        addWorldEdit("sel");
        addWorldEdit("desel");
        addWorldEdit("pos1");
        addWorldEdit("pos2");
        addWorldEdit("hpos1");
        addWorldEdit("hpos2");
        addWorldEdit("chunk");
        addWorldEdit("chunk");
        addWorldEdit("expand");
        addWorldEdit("outset");
        addWorldEdit("inset");
        addWorldEdit("count");
        addWorldEdit("distr");

        //Region Operation
        addWorldEdit("set");
        addWorldEdit("replace");
        addWorldEdit("overlay");
        addWorldEdit("walls");
        addWorldEdit("outline");
        addWorldEdit("center");
        addWorldEdit("smooth");
        addWorldEdit("deform");
        addWorldEdit("regen");
        addWorldEdit("hollow");
        addWorldEdit("move");
        addWorldEdit("stack");
        addWorldEdit("naturalize");
        addWorldEdit("line");
        addWorldEdit("curve");
        addWorldEdit("forest");
        addWorldEdit("flora");

        //Clipboards and Schematics
        addWorldEdit("copy");
        addWorldEdit("cut");
        addWorldEdit("paste");
        addWorldEdit("rotate");
        addWorldEdit("flip");
        addWorldEdit("schematic");
        addWorldEdit("schem");
        addWorldEdit("clearclipboard", false);

        //Generation
        addWorldEdit("generate");
        addWorldEdit("generatebiome");
        addWorldEdit("hcyl");
        addWorldEdit("cyl");
        addWorldEdit("sphere");
        addWorldEdit("hsphere");
        addWorldEdit("pyramid");
        addWorldEdit("hpyramid");
        addWorldEdit("forestgen", false);
        addWorldEdit("pumpkins", false);

        //Utilities
        addWorldEdit("toggleplace", false);
        addWorldEdit("fill");
        addWorldEdit("fillr");
        addWorldEdit("drain");
        addWorldEdit("fixwater");
        addWorldEdit("fixlava");
        addWorldEdit("removeabove", false);
        addWorldEdit("removebelow", false);
        addWorldEdit("replacenear", false);
        addWorldEdit("removenear", false);
        addWorldEdit("snow", false);
        addWorldEdit("thaw", false);
        addWorldEdit("ex", false);
        addWorldEdit("butcher", false);
        addWorldEdit("remove", false);
        addWorldEdit("green", false);
        addWorldEdit("calc");

        //Chunk Tools
        addWorldEdit("/", false);
        addWorldEdit("sp", false);

        //General Tools
        addWorldEdit("tool", false);
        addWorldEdit("none", false);
        addWorldEdit("farwand", false);
        addWorldEdit("lrbuild", false);
        addWorldEdit("tree", false);
        addWorldEdit("deltree", false);
        addWorldEdit("repl", false);
        addWorldEdit("cycler", false);
        addWorldEdit("flood", false);

        //Brushes
        addWorldEdit("brush", false);
        addWorldEdit("size", false);
        addWorldEdit("mat", false);
        addWorldEdit("range", false);
        addWorldEdit("mask", false);
        addWorldEdit("gmask");

        //Quick-Travel
        addWorldEdit("unstuck", false);
        addWorldEdit("ascend", false);
        addWorldEdit("descend", false);
        addWorldEdit("thru", false);
        addWorldEdit("jumpto", false);
        addWorldEdit("up", false);

        //Snapshots
        addWorldEdit("restore");
        addWorldEdit("snapshot", false);

        //Java Scriptings
        addWorldEdit("cs");
        addWorldEdit(".s", false);

        //Biomes
        addWorldEdit("biomelist", false);
        addWorldEdit("biomeinfo", false);
        addWorldEdit("setbiome");
    }

    private static void addWorldEdit(String command) {
        addWorldEdit(command, true);
    }

    private static void addWorldEdit(String command, boolean doubleSlash) {
        BLOCKED_COMMAND_LIST.add(new WorldEditBlockedCommand(command, doubleSlash));
    }

    private final String identifier;
    private final boolean doubleSlash;

    private WorldEditBlockedCommand(String identifier, boolean doubleSlash) {
        this.identifier = identifier;
        this.doubleSlash = doubleSlash;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getReasonKey() {
        return "command_usage.blocked.world_edit";
    }

    @Override
    public boolean shouldBlock(Player player) {
        if (!AriesSystemConfig.WORLD_BLOCK_WORLD_EDIT) return false;

        AriesWorld world = AriesSystem.getInstance().getWorldManager().getWorld(player);
        return world == null || !world.isPermitted(player);
    }

    @Override
    public boolean matches(String command) {
        if (doubleSlash) command = "/" + command;

        return BlockedCommand.super.matches(command);
    }
}
