package com.example.contactsapplication.domain;

public enum PhoneUseType {

    PRIMARY("Primary"),
    WORK("Work"),
    HOME("Home"),
    OTHER("Other");

    String displayName;

    PhoneUseType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
