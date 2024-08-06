package com.example.teamup.Activity.Domain;

public class BazaSportivaDomain {
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private float rating;
    private int hourlyRate;

    public BazaSportivaDomain(String name, String description, String address, String phoneNumber, float rating, int hourlyRate) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.hourlyRate = hourlyRate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }
}
