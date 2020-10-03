package ru.emi.ignitecache.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Slf4j
@AllArgsConstructor
public class Downloader {

    public static final String FILE_URL = "http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Electronics_5.json.gz";
    public static final String FILE_NAME = "reviews_Electronics_5.json.gz";

    /**
     * Download file.
     */
    public boolean download(String sourcePath, String destinationPath) {
        log.info("Start download file: {} to: {}", sourcePath, destinationPath);
        try (InputStream in = new URL(sourcePath).openStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(in);
             FileOutputStream fileOutputStream = new FileOutputStream(destinationPath);
             FileChannel fileChannel = fileOutputStream.getChannel()) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            log.error("Error download file: {} to: {}", sourcePath, destinationPath, e);
            return false;
        }
        log.info("Finish download file: {} to: {}", sourcePath, destinationPath);
        return true;
    }
}
