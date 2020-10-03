package ru.emi.ignitecache.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Activity {
    private String path;
    private String checkSum;
}
