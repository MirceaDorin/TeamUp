package com.example.teamup.Activity.Domain;

public class PopularDomain {
    private String title;
    private String subtitle;
    private String picAddress;

    public PopularDomain(String title, String subtitle, String picAddress) {
        this.title = title;
        this.subtitle = subtitle;
        this.picAddress = picAddress;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPicAddress() {
        return picAddress;
    }
}
