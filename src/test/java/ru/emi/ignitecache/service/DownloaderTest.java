package ru.emi.ignitecache.service;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static ru.emi.ignitecache.service.Downloader.FILE_NAME;

/**
 * Integration test
 */
public class DownloaderTest {

    @Test
    public void test_download_shouldDownloadFile() {
        Downloader downloader = new Downloader();
        downloader.download();
        Path path = Paths.get(FILE_NAME);
        assertTrue(Files.exists(path));
    }
}