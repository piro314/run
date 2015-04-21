package com.piro.run.utils;

/**
 * Created by ppirovski on 4/21/15. In Code we trust
 */
public class TimeUtils {

    public static long covertToMillis(int hours, int minutes, int seconds){
        return (hours*60*60 + minutes*60 + seconds) * 1000L;
    }

    public static int getHours(long millis){
        return (int)(millis / (1000L*60*60));
    }

    public static int getMinutes(long millis){
        return (int)(millis % (1000L*60*60) / (1000*60));
    }

    public static int getSeconds(long millis){
        return (int)(millis % (1000L*60)) / 1000;
    }


}
