package de.ariesbuildings.utils;

import me.noci.quickutilities.utils.logfilter.LogFilter;
import me.noci.quickutilities.utils.logfilter.LogFilters;

import java.util.function.Supplier;

public class WorldGenerationLogFilter {

    private static boolean stopLog = false;

    public static void stopLogging() {
        stopLog = true;
    }

    public static void startLogging() {
        stopLog = false;
    }

    static {
        LogFilters.addFilter(message -> stopLog ? LogFilter.Result.DENY : LogFilter.Result.NEUTRAL);
    }

    public static <T> T handleSilently(Supplier<T> supplier) {
        stopLogging();
        T value = supplier.get();
        startLogging();
        return value;
    }

}
