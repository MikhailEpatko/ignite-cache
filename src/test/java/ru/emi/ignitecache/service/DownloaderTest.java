package ru.emi.ignitecache.service;

import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;
import static ru.emi.ignitecache.enums.Property.FAILED_DOWNLOADS_REPEAT_INTERVAL;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

public class DownloaderTest {

    private final Downloader downloader = spy(Downloader.class);
    private final String sourcePath = "/tmp/source/test.gz";
    private final String destinationPath = "/tmp/destination/test.gz";

    @Test
    public void downloadFile_shouldTryDownloadOnlyOnce() {
        when(downloader.downloadFile(sourcePath, destinationPath)).thenReturn(true);

        downloader.startDownloadJob(sourcePath, destinationPath);

        verify(downloader, times(1)).startDownloadJob(sourcePath, destinationPath);
        verify(downloader, (times(1))).downloadFile(sourcePath, destinationPath);
    }

    @Test
    public void downloadFile_shouldTryDownloadFourTimes() {
        PROPERTIES.put(FAILED_DOWNLOADS_REPEAT_INTERVAL, "1");
        when(downloader.downloadFile(sourcePath, destinationPath)).thenReturn(false);

        downloader.startDownloadJob(sourcePath, destinationPath);

        verify(downloader, times(1)).startDownloadJob(sourcePath, destinationPath);
        verify(downloader, (times(4))).downloadFile(sourcePath, destinationPath);
    }
}