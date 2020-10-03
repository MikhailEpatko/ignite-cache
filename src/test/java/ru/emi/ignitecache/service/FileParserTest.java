package ru.emi.ignitecache.service;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.emi.ignitecache.model.Activity;
import ru.emi.ignitecache.model.Review;
import ru.emi.ignitecache.repository.CacheRepository;
import ru.emi.ignitecache.testRepository.ActivityTestRepository;
import ru.emi.ignitecache.testRepository.ReviewTestRepository;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

public class FileParserTest {

    private final Gson gson = new Gson();
    private final String path = Objects.requireNonNull(
        getClass().getClassLoader().getResource("reviews_Electronics_5_short.json.gz")
    ).getPath();
    private CacheRepository<String, Review> reviewRepository;
    private CacheRepository<String, Activity> activityRepository;
    private FileParser parser;

    @Before
    public void init() {
        reviewRepository = spy(new ReviewTestRepository());
        activityRepository = spy(new ActivityTestRepository());
        parser = new FileParser(activityRepository);
    }

    @Test
    public void parseFile_shouldParseFile() {
        parser.parse(path, line -> gson.fromJson(line, Review.class), reviewRepository::save);

        verify(reviewRepository, times(26)).save(any(Review.class));
        verify(activityRepository, times(1)).findById(path);
        verify(activityRepository, times(1)).save(any(Activity.class));

        assertEquals(26, reviewRepository.count());
        assertEquals(1, activityRepository.count());
    }

    @Test
    public void parseFile_shouldParseTheSameFileOnlyOnce() {
        parser.parse(path, line -> gson.fromJson(line, Review.class), reviewRepository::save);
        parser.parse(path, line -> gson.fromJson(line, Review.class), reviewRepository::save);

        verify(reviewRepository, times(26)).save(any(Review.class));
        verify(activityRepository, times(2)).findById(path);
        verify(activityRepository, times(1)).save(any(Activity.class));

        assertEquals(26, reviewRepository.count());
        assertEquals(1, activityRepository.count());
    }
}