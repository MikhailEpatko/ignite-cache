package ru.emi.ignitecache.testRepository;

import ru.emi.ignitecache.model.Review;
import ru.emi.ignitecache.repository.CacheRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReviewTestRepository implements CacheRepository<String, Review> {

    private final Map<String, Review> storage = new HashMap<>();

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
        return storage.size();
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
