package com.example.teamup.Activity.Domain;

public class PrieteniDomain {
    private String imageResId; // This should be a valid URL or local path
    private String username;

    public PrieteniDomain() {
        // Required empty constructor for Firebase
    }

    public PrieteniDomain(String imageResId, String username) {
        this.imageResId = imageResId;
        this.username = username;
    }

    public String getImageResId() {
        return imageResId;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

