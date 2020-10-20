package ru.emi.ignitecache.utility;

import ru.emi.ignitecache.enums.Property;

import java.util.EnumMap;
import java.util.Map;

/**
 * Simple class provides application properties.
 */
public class PropertyUtility {

    public static final Map<Property, String> PROPERTIES = new EnumMap<>(Property.class);

    /**
     * Utility classes, which are collections of static members, are not meant to be instantiated. Even abstract utility classes, which can
     * be extended, should not have public constructors. Java adds an implicit public constructor to every class which does not define at
     * least one explicitly. Hence, at least one non-public constructor should be defined.
     */
    private PropertyUtility() {
    }

    static {
        PROPERTIES.put(Property.SOURCE_PATH, "http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Electronics_5.json.gz");
        PROPERTIES.put(Property.WORK_DIR, "/tmp/");
        PROPERTIES.put(Property.DESTINATION_FILE_NAME, "reviews_Electronics_5.json.gz");

        PROPERTIES.put(Property.TIME_ZONE, "Europe/Moscow");
        PROPERTIES.put(Property.FIXED_DELAY_IN_HOURS, "1");
        PROPERTIES.put(Property.START_MINUTE, "15");

        PROPERTIES.put(Property.ACTIVITY_CACHE, "activity_cache");
        PROPERTIES.put(Property.REVIEW_CACHE, "review_cache");

        PROPERTIES.put(Property.REVIEW_REPOSITORY, "ReviewRepository");

        PROPERTIES.put(Property.FAILED_DOWNLOADS_REPEAT_TIMES, "4");
        PROPERTIES.put(Property.FAILED_DOWNLOADS_REPEAT_INTERVAL, "60000");
    }
}
