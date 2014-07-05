package net.njay.mesigi.util.log;

import org.joda.time.DateTime;

/**
 * Created by Nick on 7/5/14.
 */
public class ReflectionLogger {

    private static Class<?>[] classList;
    private static FilterMode filterMode;
    private static LogLevel lowLevel;

    public static void filter(LogLevel level, FilterMode mode, Class<?>... classes){
        lowLevel = level;
        filterMode = mode;
        classList = classes;
    }

    public static void filter(FilterMode mode, Class<?>... classes){
        filterMode = mode;
        classList = classes;
    }

    public static void filter(LogLevel level){
        lowLevel = level;
    }

    public static void log(LogLevel level, String string){
        String className = Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length-1].getClassName();
        if (classList != null && filterMode != null){
            boolean foundMatch = (filterMode == FilterMode.BLACKLIST);
            for (Class<?> clazz : classList)
                if (clazz.getName().equals(className)){
                    foundMatch = !foundMatch;
                    break;
                }
            if (!foundMatch) return;
        }
        if (lowLevel != null){
            if (lowLevel.getLevel() > level.getLevel())
                return;
        }
        DateTime dTime = new DateTime();
        System.out.println("["+level.name().toUpperCase()+ " " + dTime.getHourOfDay()+":"+dTime.getMinuteOfHour()+":"+dTime.getSecondOfMinute()+ "] " + string);
    }


    public enum FilterMode{
        WHITELIST, BLACKLIST;
    }

}

