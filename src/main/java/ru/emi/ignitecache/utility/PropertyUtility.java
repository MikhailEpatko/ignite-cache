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
        PROPERTIES.put(Property.WORK_DIR, "/tmp/ignite-cache-work-dir");
    }
}
