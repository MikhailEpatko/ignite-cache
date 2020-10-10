package ru.emi.ignitecache.utility;

import lombok.extern.slf4j.Slf4j;
import ru.emi.ignitecache.enums.Property;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.emi.ignitecache.enums.Property.FIXED_DELAY_IN_HOURS;
import static ru.emi.ignitecache.enums.Property.START_MINUTE;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;


/**
 * Utility class for date/time based operations.
 */

@Slf4j
public class DateTimeUtility {

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract
     * utility classes, which can be extended, should not have public constructors. Java adds an implicit
     * public constructor to every class which does not define at least one explicitly.
     * Hence, at least one non-public constructor should be defined.
     */
    private DateTimeUtility() {
    }

    /**
     * Calculate current local date/time.
     *
     * @return local date/time in {@link LocalDateTime}
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(PROPERTIES.get(Property.TIME_ZONE)));
    }

    /**
     * Calculate initial delay - time in seconds from now to the second start of the job. By default scheduled job
     * will be started at 15 minutes past every hour.
     *
     * @return long - delay in seconds
     */
    public static long getInitialDelay() {
        long initialDelay;
        long startHour;
        long startMinute;
        try {
            startHour = Long.parseLong(PROPERTIES.get(FIXED_DELAY_IN_HOURS)) * 60L;
            startMinute = Long.parseLong(PROPERTIES.get(START_MINUTE));
        } catch (Exception e) {
            log.error("-----> Error while parsing START_HOUR or START_MINUTE. Set default: 1:15.");
            startHour = 120L;
            startMinute = 20L;
        }
        LocalDateTime now = getLocalDateTime();
        long nowHour = now.getHour();
        long nowMinute = now.getMinute();
        long mod = nowHour % 2L;
        if (mod == 0L && nowMinute > startMinute) {
            initialDelay = startHour + startMinute - nowMinute;
        } else {
            initialDelay = mod * 60L + startMinute - nowMinute;
        }
        return initialDelay * 60L;
    }
}
