package ru.emi.ignitecache.repository;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import ru.emi.ignitecache.model.Activity;

import java.util.Optional;

import static ru.emi.ignitecache.enums.Property.ACTIVITY_CACHE;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

public class ActivityRepository implements CacheRepository<String, Activity> {

    private final IgniteCache<String, Activity> storage;

    public ActivityRepository(Ignite ignite) {
        storage = ignite.getOrCreateCache(PROPERTIES.get(ACTIVITY_CACHE));
    }

    @Override
    public void save(Activity activity) {
        storage.put(activity.getPath(), activity);
    }

    @Override
    public Optional<Activity> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    public long count() {
        return storage.size(CachePeekMode.ALL);
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
