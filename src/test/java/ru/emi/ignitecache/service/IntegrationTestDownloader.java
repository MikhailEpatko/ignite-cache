package ru.emi.ignitecache.service;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

/**
 * Integration test.
 * Downloads large gzip file.
 */
public class IntegrationTestDownloader {

    @Test
    public void test_downloadFile_shouldDownloadFile() {
        String source = "http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Electronics_5.json.gz";
        String destinationGz = "/tmp/reviews_Electronics_5.json.gz";

        Downloader downloader = new Downloader();
        downloader.startDownloadJob(source, destinationGz);
        Path pathGz = Paths.get(destinationGz);

        assertTrue(Files.exists(pathGz));
    }
}