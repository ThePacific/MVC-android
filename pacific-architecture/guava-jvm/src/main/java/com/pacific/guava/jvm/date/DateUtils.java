package com.pacific.guava.jvm.date;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @link https://github.com/urbanairship/android-library/blob/master/urbanairship-core/src/main/java/com/urbanairship/util/DateUtils.java
 */
public class DateUtils {

    private DateUtils() {
    }

    // SimpleDateFormat isn't thread safe, cache one instance per thread as needed.
    private static final ThreadLocal<DateFormat> iso8601Holder = new ThreadLocal<DateFormat>() {
        @NonNull
        @Override
        protected DateFormat initialValue() {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            iso8601.setTimeZone(tz);
            return iso8601;
        }
    };

    // SimpleDateFormat isn't thread safe, cache one instance per thread as needed.
    private static final ThreadLocal<DateFormat> altIso8601Holder = new ThreadLocal<DateFormat>() {
        @NonNull
        @Override
        protected DateFormat initialValue() {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            iso8601.setTimeZone(tz);
            return iso8601;
        }
    };

    /**
     * Parses an ISO 8601 timestamp.
     *
     * @param timeStamp The ISO time stamp string.
     * @return The time in milliseconds since Jan. 1, 1970, midnight GMT or -1 if the timestamp was unable
     * to be parsed.
     * @throws ParseException if the timestamp was unable to be parsed.
     */
    public static long fromIso8601Le(@NonNull String timeStamp) throws ParseException {
        return fromIso8601(timeStamp).getTime();
    }

    /**
     * Parses an ISO 8601 timestamp.
     *
     * @param timeStamp    The ISO time stamp string.
     * @param defaultValue The default value
     * @return The time in milliseconds since Jan. 1, 1970, midnight GMT or the default value
     * if the timestamp was unable to be parsed.
     */
    public static long fromIso8601Le(@NonNull String timeStamp, long defaultValue) {
        return fromIso8601(timeStamp, new Date(defaultValue)).getTime();
    }

    /**
     * Creates an ISO 8601 formatted time stamp.
     *
     * @param milliseconds The time in milliseconds since Jan. 1, 1970, midnight GMT.
     * @return An ISO 8601 formatted time stamp.
     */
    public static String toIso8601Le(long milliseconds) {
        return iso8601Holder.get().format(new Date(milliseconds));
    }

    public static String toIso8601(Date date) {
        return iso8601Holder.get().format(date);
    }

    public static Date fromIso8601(@NonNull String timeStamp) throws ParseException {
        //noinspection ConstantConditions
        if (timeStamp == null) {
            throw new ParseException("Unable to parse null timestamp", -1);
        }
        try {
            return iso8601Holder.get().parse(timeStamp);
        } catch (ParseException ignored) {
            return altIso8601Holder.get().parse(timeStamp);
        }
    }

    public static Date fromIso8601(@NonNull String timeStamp, Date defaultValue) {
        try {
            return fromIso8601(timeStamp);
        } catch (ParseException ignored) {
            return defaultValue;
        }
    }

    public static String msToString(long ms) {
        long totalSeconds = ms / 1000;
        long hours = (totalSeconds / 3600);
        long minutes = (totalSeconds / 60) % 60;
        long seconds = totalSeconds % 60;

        String hoursString = (hours == 0)
                ? "00"
                : ((hours < 10)
                ? "0" + hours
                : "" + hours);
        String minutesString = (minutes == 0)
                ? "00"
                : ((minutes < 10)
                ? "0" + minutes
                : "" + minutes);
        String secondsString = (seconds == 0)
                ? "00"
                : ((seconds < 10)
                ? "0" + seconds
                : "" + seconds);
        return hoursString + ":" + minutesString + ":" + secondsString;
    }
}
