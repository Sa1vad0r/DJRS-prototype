package com.djrs.model;

import com.djrs.io.PasswordHasher;

public class User {
    // ----- Core Identity Fields -----
    private String userID;      // Unique ID assigned by the system/database (e.g., UUID or Long)
    private String displayName; // What the user sees (ex: Triple T)
    private String passwordHash; // Hashed password. Don't put the actual password in here.

    // ----- Metadata Fields -----
    // accountCreatedAt
    // etc.

    public User() {
        this.userID = "";
        this.displayName = "";
        this.passwordHash = "";
    }

    public User(String userID, String displayName, String passwordHash) {
        this.userID = userID;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
    }

    public User getUser(String username) {
        // Get user from /data folder (Most likely stored in UsersData.json)

        // If user not found, return null
        return null;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean checkPassword(String inputPassword, String storedHash) {
        // Check if the input password matches the stored hash.
        // Use PasswordHasher.checkPassword(inputPassword, storedHash) for a simple check.
        return PasswordHasher.checkPassword(inputPassword, storedHash);
    }
}
