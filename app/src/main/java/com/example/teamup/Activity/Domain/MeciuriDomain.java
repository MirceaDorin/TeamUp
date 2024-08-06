package com.example.teamup.Activity.Domain;

public class MeciuriDomain {
    private String creator;
    private String location;
    private String dateTime;
    private String price;
    private int participants;
    private static final int MAX_PARTICIPANTS = 12;

    public MeciuriDomain(String creator, String location, String dateTime, String price, int participants) {
        this.creator = creator;
        this.location = location;
        this.dateTime = dateTime;
        this.price = price;
        this.participants = participants;
    }

    public String getCreator() {
        return creator;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getPrice() {
        return price;
    }

    public int getParticipants() {
        return participants;
    }

    public void incrementParticipants() {
        if (participants < MAX_PARTICIPANTS) {
            participants++;
        }
    }

    public String getParticipantsText() {
        return participants + "/" + MAX_PARTICIPANTS + " locuri";
    }
}
