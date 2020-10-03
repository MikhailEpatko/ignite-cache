package ru.emi.ignitecache.service;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

/**
 * Integration test
 */
@Ignore
public class DownloaderTest {

    @Test
    public void test_download_shouldDownloadFile() {
        String source = "http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Electronics_5.json.gz";
        String destinationGz = "/tmp/reviews_Electronics_5.json.gz";

        Downloader downloader = new Downloader();
        downloader.download(source, destinationGz);
        Path pathGz = Paths.get(destinationGz);

        assertTrue(Files.exists(pathGz));
    }
}