package ru.emi.ignitecache.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Contains logic of managing local files in application work directory.
 */

@Slf4j
public class FileManager {

    /**
     * Create directory if not exists.
     *
     * @param path {@link String} path to the directory.
     * @return {@code true} if directory was created successful or directory already exists, {@code false} if got
     * Exception.
     */
    public boolean createIfNotExists(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                log.info("Create directory {}.", dir);
                return dir.mkdirs();
            } else {
                log.info("Directory {} already exists.", dir.getPath());
                return true;
            }
        } catch (Exception e) {
            log.error("Exception when try check or create directory: {}", path);
            return false;
        }
    }
}
