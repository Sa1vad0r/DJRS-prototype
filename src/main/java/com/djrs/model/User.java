package com.djrs.model;

import com.djrs.io.PasswordHasher;
import com.djrs.io.UserStore;

public class User {
    // ----- Core Identity Fields -----
    private String id; // Unique ID assigned by the system/database (e.g., UUID or Long)
    private String firstName;
    private String lastName;
    private String username; // What the user sees (ex: Triple T)
    private String passwordHash; // Hashed password. Don't put the actual password in here.

    // ----- Metadata Fields -----
    // accountCreatedAt
    // etc.

    public User() {
        this.id = "";
        this.username = "";
        this.passwordHash = "";
    }

    public User(String id, String username, String passwordHash) {
        this.id = id;
        this.firstName = "";
        this.lastName = "";
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(String id, String firstName, String lastName, String username, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User getUser(String username) {
        return UserStore.findUserByUsername(username);
    }

    public String getUserID() {
        return id;
    }

    public void setUserID(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean checkPassword(String inputPassword, String storedHash) {
        // Check if the input password matches the stored hash.
        // Use PasswordHasher.checkPassword(inputPassword, storedHash) for a simple
        // check.
        return PasswordHasher.checkPassword(inputPassword, storedHash);
    }

    @Override
    public String toString() {
        StringBuilder userDetails = new StringBuilder();
        userDetails.append("User[ ").append("id: ").append(this.id)
                .append(", firstName: ").append(this.firstName)
                .append(", lastName: ").append(this.lastName)
                .append(", username: ").append(this.username)
                .append(", passwordHash: ").append(this.passwordHash)
                .append(" ]");
        return userDetails.toString();
    }
}
