package ru.emi.ignitecache.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import ru.emi.ignitecache.model.Review;

import java.util.Optional;

import static ru.emi.ignitecache.enums.Property.REVIEW_CACHE;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

@Slf4j
public class ReviewRepository implements CacheRepository<String, Review>, Service {

    @IgniteInstanceResource
    private transient Ignite ignite;
    private transient IgniteCache<String, Review> storage;

    @Override
    public void init(ServiceContext ctx) {
        storage = ignite.getOrCreateCache(PROPERTIES.get(REVIEW_CACHE));
        log.info("-----> Service was initialized: {}", ctx);
    }

    @Override
    public void execute(ServiceContext ctx) {
        /*
         * Not implemented
         * */
    }

    @Override
    public void cancel(ServiceContext ctx) {
        log.info("-----> Service was cancelled: {}", ctx);
    }


    @Override
    public void save(Review review) {
        if (review.getId() == null) {
            review.setId(review.getReviewerID() + review.getAsin());
        }
        storage.put(review.getId(), review);
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    @Override
    public long count() {
        return storage.size(CachePeekMode.ALL);
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<Review> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }
}
