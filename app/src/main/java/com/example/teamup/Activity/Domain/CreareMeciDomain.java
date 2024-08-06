package com.example.teamup.Activity.Domain;

public class CreareMeciDomain {
    private String creatorName;
    private String location;
    private String dateTime;
    private String price;
    private int currentParticipants;
    private int maxParticipants;

    // Constructor implicit (necesar pentru Firebase)
    public CreareMeciDomain() {
        // Constructor gol necesar pentru deserializare
    }

    // Constructor cu parametri
    public CreareMeciDomain(String creatorName, String location, String dateTime, String price, int participants, int maxParticipants) {
        this.creatorName = creatorName;
        this.location = location;
        this.dateTime = dateTime;
        this.price = price;
        this.currentParticipants = participants;
        this.maxParticipants = maxParticipants;
    }

    // Getters și setters pentru toate câmpurile
    // Exemplu de getter
    public String getCreatorName() {
        return creatorName;
    }

    // Exemplu de setter
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}
