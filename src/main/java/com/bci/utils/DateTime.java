package com.bci.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public final class DateTime {

    private DateTime() {
    }

    public static Timestamp getActualTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }
    
    public static String getActualStringTimeStamp() {
        Timestamp currentTimestamp = getActualTimestamp();
        return formatTimestampToString(currentTimestamp);
    }
    
    public static String formatTimestampToString(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }
    
    public static Date getCurrentDate() {
        return new Date();
    }
    
}
