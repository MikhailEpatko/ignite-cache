package ru.emi.ignitecache.service;

import lombok.SneakyThrows;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.services.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import ru.emi.ignitecache.enums.Property;
import ru.emi.ignitecache.repository.ActivityRepository;
import ru.emi.ignitecache.repository.CacheRepository;
import ru.emi.ignitecache.repository.ReviewRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StartService.class, Executors.class, Ignite.class})
public class StartServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private FileManager fileManager;
    @Mock
    private Downloader downloader;
    @Mock
    private FileParser parser;
    @Mock
    private ScheduledExecutorService executor;
    @Mock
    private ServiceContext ctx;
    @Mock
    private Ignite ignite;
    @Mock
    private IgniteServices services;

    @Before
    @SneakyThrows
    public void init() {
        whenNew(ActivityRepository.class).withAnyArguments().thenReturn(activityRepository);
        whenNew(ReviewRepository.class).withAnyArguments().thenReturn(reviewRepository);

        whenNew(FileManager.class).withAnyArguments().thenReturn(fileManager);
        when(fileManager.createIfNotExists(anyString())).thenReturn(true);
        whenNew(Downloader.class).withAnyArguments().thenReturn(downloader);
        whenNew(FileParser.class).withAnyArguments().thenReturn(parser);

        PowerMockito.mockStatic(Executors.class);
        when(Executors.newSingleThreadScheduledExecutor()).thenReturn(executor);

        when(ignite.services()).thenReturn(services);
        when(services.serviceProxy(PROPERTIES.get(Property.REVIEW_REPOSITORY), CacheRepository.class, false))
            .thenReturn(reviewRepository);
    }

    @Test
    public void execute_whenFileDownloadSuccess_shouldTryToDownloadFilesOnlyOnce() {
        StartService startService = new StartService();
        Whitebox.setInternalState(startService, "ignite", ignite);
        when(downloader.downloadFile(anyString(), anyString())).thenReturn(true);

        startService.execute(ctx);

        verify(fileManager, times(1)).createIfNotExists(anyString());
        verify(downloader, times(1)).startDownloadJob(anyString(), anyString());
        verify(parser, times(1)).parse(anyString(), any(), any());
    }
}