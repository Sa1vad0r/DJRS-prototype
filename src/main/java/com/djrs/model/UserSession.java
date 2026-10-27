package com.djrs.model;

import java.util.ArrayList;

public class UserSession {
    private static UserSession instance = null;
    // Add attributes here
    private User currentUser;
    private ArrayList<Rewind> userRewinds;

    // Create private constructor restricted to this class itself
    private UserSession(User currentUser) {
        // Initialize attributes here
        this.currentUser = currentUser;
        userRewinds = new ArrayList<>();
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession(new User());
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
