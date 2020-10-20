package ru.emi.ignitecache.service;

import lombok.extern.slf4j.Slf4j;
import java.io.File;

@Slf4j
public class FileManager {

    public boolean createIfNotExists(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                log.info("-----> Create directory {}.", dir);
                return dir.mkdirs();
            } else {
                log.info("-----> Directory {} already exists.", dir.getPath());
                return true;
            }
        } catch (Exception e) {
            log.error("-----> Exception when try check or create directory: {}", path);
            return false;
        }
    }
}
