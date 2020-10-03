package ru.emi.ignitecache.testRepository;

import ru.emi.ignitecache.model.Activity;
import ru.emi.ignitecache.repository.CacheRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActivityTestRepository implements CacheRepository<String, Activity> {

    public final Map<String, Activity> storage = new HashMap<>();

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

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }

}
