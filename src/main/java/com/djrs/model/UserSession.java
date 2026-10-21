package com.djrs.model;

public class UserSession {
    private static UserSession instance = null;
    // Add attributes here
    private User currentUser;

    // Create private constructor restricted to this class itself
    private UserSession(User currentUser) {
       // Initialize attributes here
        this.currentUser = currentUser;
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
