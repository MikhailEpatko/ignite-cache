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
    public boolean download() {
        try (InputStream in = new URL(FILE_URL).openStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(in);
             FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
             FileChannel fileChannel = fileOutputStream.getChannel()) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            log.error("-----> Error download files: ", e);
            return false;
        }
        return true;
    }
}
