package de.ariesbuildings.hooks;

public class WorldEditHook extends PluginHook {

    protected WorldEditHook() {
        super("WorldEdit");
    }

    public static boolean isEnabled() {
        return Singleton.HOOK.enabled();
    }

    private static class Singleton {
        private static final WorldEditHook HOOK = new WorldEditHook();
    }

}
