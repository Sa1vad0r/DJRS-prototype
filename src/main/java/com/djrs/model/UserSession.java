package com.djrs.model;

public class UserSession {
    private static UserSession instance = null;
    // Add attributes here

    // Create private constructor restricted to this class itself
    private UserSession() {
       // Initialize attributes here
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
}
