package com.example.contactsapplication.domain;

public enum PhoneUseType {

    WORK("Work"),
    HOME("Home"),
    MOBILE("Hobby"),
    OTHER("Other");

    String displayName;

    PhoneUseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
