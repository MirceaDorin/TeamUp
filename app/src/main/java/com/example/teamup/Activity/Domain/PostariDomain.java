package com.example.teamup.Activity.Domain;

import java.util.List;

public class PostariDomain {
    private String title;
    private String description;
    private String city;
    private List<Integer> imageResIds;
    private float rating;
    private String fullAddress;
    private String hourlyRate;
    private String phoneNumber;

    public PostariDomain(String title, String description, String city, List<Integer> imageResIds, float rating, String fullAddress, String hourlyRate, String phoneNumber) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.imageResIds = imageResIds;
        this.rating = rating;
        this.fullAddress = fullAddress;
        this.hourlyRate = hourlyRate;
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public List<Integer> getImageResIds() {
        return imageResIds;
    }

    public float getRating() {
        return rating;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
