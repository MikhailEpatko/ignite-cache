package ru.emi.ignitecache.model;

import lombok.Data;

@Data
public class Review {

    private String id;
    private String reviewerID;
    private String asin;
    private String reviewerName;
    private int[] helpful;
    private String reviewText;
    private double overall;
    private String summary;
    private long unixReviewTime;
    private String reviewTime;
}
