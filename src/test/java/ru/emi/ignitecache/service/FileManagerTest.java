package ru.emi.ignitecache.service;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class FileManagerTest {

    private final String path = Objects.requireNonNull(getClass().getClassLoader().getResource(".")).getPath()
        + "test-work-dir";

    @Test
    public void createIfNotExists_shouldCreateWorkDirectories() {
        FileManager fileManager = new FileManager();
        boolean result = fileManager.createIfNotExists(path);

        assertTrue(result);

        File dir = Paths.get(path).toFile();

        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());

        assertTrue(dir.delete());
    }

    @Test
    public void createIfNotExists_shouldCorrectWorkIfWorkDirectoryAlreadyExists() {
        FileManager fileManager = new FileManager();
        boolean result1 = fileManager.createIfNotExists(path);
        boolean result2 = fileManager.createIfNotExists(path);

        assertTrue(result1);
        assertTrue(result2);

        File dir = Paths.get(path).toFile();

        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());

        assertTrue(dir.delete());
    }
}