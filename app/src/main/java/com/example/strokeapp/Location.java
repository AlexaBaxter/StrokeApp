package com.example.strokeapp;

public class Location {

    private String objectName, locationName;

    public Location(String o, String l) {
        objectName = o;
        locationName = l;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
