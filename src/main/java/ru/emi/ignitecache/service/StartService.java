package ru.emi.ignitecache.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;
import ru.emi.ignitecache.model.Review;
import ru.emi.ignitecache.repository.ActivityRepository;
import ru.emi.ignitecache.repository.CacheRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.emi.ignitecache.enums.Property.*;
import static ru.emi.ignitecache.utility.DateTimeUtility.getInitialDelay;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

@Slf4j
public class StartService implements Service {

    @IgniteInstanceResource
    private transient Ignite ignite;
    private transient ScheduledExecutorService scheduledExecutor;
    private final Gson gson = new Gson();

    @Override
    public void init(ServiceContext ctx) {
        log.info("-----> StartService was initialized: {}.", ctx);
    }

    @Override
    public void execute(ServiceContext ctx) {
        log.info("-----> Start service execution.");
        ActivityRepository activityRepository = new ActivityRepository(ignite);
        FileManager fileManager = new FileManager();
        Downloader downloader = new Downloader();
        FileParser fileParser = new FileParser(activityRepository);
        CacheRepository<String, Review> reviewRepository = ignite.services()
            .serviceProxy(PROPERTIES.get(REVIEW_REPOSITORY), CacheRepository.class, false);

        Runnable worker = () -> {
            log.info("-----> Start job.");
            try {
                if (!fileManager.createIfNotExists(PROPERTIES.get(WORK_DIR))) {
                    log.error("Can't proceed the job. Cause: ERROR when try check or create work directories.");
                    return;
                }
                String destinationFilePath = PROPERTIES.get(WORK_DIR) + PROPERTIES.get(DESTINATION_FILE_NAME);
                downloader.startDownloadJob(PROPERTIES.get(SOURCE_PATH), destinationFilePath);
                fileParser.parse(destinationFilePath, line -> gson.fromJson(line, Review.class), reviewRepository::save);
                log.info("-----> Finish job.");
            } catch (Exception e) {
                log.error("-----> Unexpected job failure: ", e);
            }
        };

        worker.run();

        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(
            worker,
            getInitialDelay(),
            Long.parseLong(PROPERTIES.get(FIXED_DELAY_IN_HOURS)) * 60L * 60L,
            TimeUnit.SECONDS);
    }

    @Override
    public void cancel(ServiceContext ctx) {
        scheduledExecutor.shutdownNow();
        log.info("-----> Service was cancelled: {}, {}.", scheduledExecutor.isShutdown(), ctx);
    }
}
