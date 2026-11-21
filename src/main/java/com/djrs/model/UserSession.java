package com.djrs.model;

import java.util.ArrayList;

public class UserSession {
    private static UserSession instance = null;
    private User currentUser;
    private ArrayList<Rewind> userRewinds;

    private UserSession(User currentUser) {
        this.currentUser = currentUser;
        userRewinds = new ArrayList<>();
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession(new User());
        }
        return instance;
    }

    public ArrayList<Rewind> getUserRewinds() {
        return userRewinds;
    }

    public void addRewind(Rewind rewind) {
        userRewinds.add(rewind);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
