package ru.emi.ignitecache.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.emi.ignitecache.model.Activity;
import ru.emi.ignitecache.repository.CacheRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

@Slf4j
@AllArgsConstructor
public class FileParser {

    private final CacheRepository<String, Activity> activityRepository;

    public <T> void parse(String path, Function<? super String, T> create, Consumer<T> save) {
        log.info("Start parsing file: {}", path);
        String checkSum = getChecksum(path);
        if (isNotAlreadyParsed(path, checkSum)) {
            try (FileInputStream fis = new FileInputStream(path);
                 GZIPInputStream gzis = new GZIPInputStream(fis);
                 InputStreamReader isr = new InputStreamReader(gzis, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                br.lines()
                    .parallel()
                    .map(create)
                    .forEach(save);
                activityRepository.save(new Activity(path, checkSum));
            } catch (Exception e) {
                log.error("Error parsing file: {}", path, e);
            }
        } else {
            log.info("File {} has been already parsed. Parsing was skip.", path);
        }
        log.info("Finish parsing file: {}", path);
    }

    private boolean isNotAlreadyParsed(String path, String checkSum) {
        return activityRepository.findById(path)
            .map(activity -> !activity.getCheckSum().equals(checkSum))
            .orElse(true);
    }

    private String getChecksum(String path) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Files.readAllBytes(Paths.get(path)));
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            log.error("Error get checksum for the file '{}'", path, e);
        }
        return null;
    }
}
