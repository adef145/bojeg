package com.bojeg.model;

import com.bojeg.BuildConfig;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String userName;

    private String type = BuildConfig.APPLICATION_ID;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isCustomer() {
        return type.equals("com.bojeg.customer");
    }

    public boolean isDriver() {
        return type.equals("com.bojeg.driver");
    }
}
