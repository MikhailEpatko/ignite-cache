package ru.emi.ignitecache.service;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static ru.emi.ignitecache.enums.Property.FAILED_DOWNLOADS_REPEAT_INTERVAL;
import static ru.emi.ignitecache.enums.Property.FAILED_DOWNLOADS_REPEAT_TIMES;
import static ru.emi.ignitecache.utility.PropertyUtility.PROPERTIES;

@Slf4j
public class Downloader {

    public void startDownloadJob(String sourcePath, String destinationPath) {
        int repeatTimes;
        long repeatInterval;
        try {
            repeatTimes = Integer.parseInt(PROPERTIES.get(FAILED_DOWNLOADS_REPEAT_TIMES));
            repeatInterval = Long.parseLong(PROPERTIES.get(FAILED_DOWNLOADS_REPEAT_INTERVAL));
        } catch (Exception e) {
            log.error("-----> Error getting repeatTimes and repeatInterval properties. "
                + "Will be set default values: repeatTimes=4, repeatInterval=60000. ", e);
            repeatTimes = 4;
            repeatInterval = 60_000L;
        }
        for (int i = 1; i <= repeatTimes; i++) {
            log.info("-----> Start download job: " + i);
            try {
                if (downloadFile(sourcePath, destinationPath)) {
                    break;
                } else {
                    log.info("-----> Download job was failed {} time(s).", i);
                }
                if (i < repeatTimes) {
                    Thread.sleep(repeatInterval);
                }
            } catch (Exception e) {
                log.error("-----> Download job failed: ", e);
            }
        }
        log.info("-----> Finish download job.");
    }

    public boolean downloadFile(String sourcePath, String destinationPath) {
        log.info("-----> Start download file: {} to: {}", sourcePath, destinationPath);
        try (InputStream in = new URL(sourcePath).openStream();
             ReadableByteChannel readableByteChannel = Channels.newChannel(in);
             FileOutputStream fileOutputStream = new FileOutputStream(destinationPath);
             FileChannel fileChannel = fileOutputStream.getChannel()) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            log.error("-----> Error download file: {} to: {}", sourcePath, destinationPath, e);
            return false;
        }
        log.info("-----> Finish download file: {} to: {}", sourcePath, destinationPath);
        return true;
    }
}
