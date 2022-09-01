package de.ariesbuildings.utils;

import org.bukkit.Bukkit;

public final class ReflectionUtils {

    private static final String VERSION;
    private static final int MAJOR_VERSION;
    private static final String CRAFTBUKKIT;
    private static final String NMS;

    static {
        try {
            String serverPackageName = Bukkit.getServer().getClass().getPackage().getName();

            VERSION = serverPackageName.substring(serverPackageName.lastIndexOf("."));

            MAJOR_VERSION = Integer.parseInt(VERSION.substring(1).split("_")[1]);
            CRAFTBUKKIT = "org.bukkit.craftbukkit" + VERSION + ".";
            NMS = supports(17) ? "net.minecraft." : "net.minecraft.server." + VERSION + ".";

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse server version.", e);
        }
    }

    private ReflectionUtils() {
        //Seal class
    }

    public static boolean supports(int version) {
        return MAJOR_VERSION >= version;
    }

    public static boolean isBelow(int version) {
        return MAJOR_VERSION < version;
    }

    public static boolean isVersion(int version) {
        return MAJOR_VERSION == version;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Class<Object> getUntypedClass(String lookupName) {
        return (Class) getClass(lookupName);
    }

    public static Class<?> getClass(String lookupName) {
        return getCanonicalClass(lookupName);
    }

    public static Class<?> getMinecraftClass(String newPackage, String name) {
        if (supports(17)) name = newPackage + "." + name;
        return getMinecraftClass(name);
    }

    public static Class<?> getMinecraftClass(String name) {
        return getCanonicalClass(NMS + name);
    }

    public static Class<?> getCraftBukkitClass(String name) {
        return getCanonicalClass(CRAFTBUKKIT + name);
    }

    private static Class<?> getCanonicalClass(String canonicalName) {
        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, e);
        }
    }
}