package com.djrs.controller;

import com.djrs.io.InputValidator;
import com.djrs.model.User;
import com.djrs.model.UserSession;
import com.djrs.model.UsersData;

public class LoginController {
    UserSession currentSession;
    UsersData users;

    public LoginController(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    public void initialize() {
        // --- Setup tasks go here ---
        users = new UsersData();
        users.loadUsersData();

        // Load system configuration data:
        System.out.println("[Controller]: Loading system configuration data...");

        // Load user data:


    }

    public int readBeginningLoginPrompt() {
        int userChoice = 0;

        userChoice = InputValidator.readIntNumberBetween(1, 3, "Invalid input. Please enter a number between 1 and 3.");

        return userChoice;
    }

    /**
     * Attempts to log the user in using provided credentials. On success, mutates the
     * shared UserSession singleton with the logged-in user.
     * @param username The username from the CLI input.
     * @param password The password from the CLI input.
     * @return true if authentication succeeded, false otherwise.
     */
    public boolean attemptLogin(String username, String password) {
        // 1. Validation Logic: Check user existence in the database/file system
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false; // Failed validation
        }

        // 2. Security Logic: Retrieve hashed password for comparison
        User storedUser = users.findUser(username);

        if (storedUser != null && verifyPassword(password, storedUser)) {
            // SUCCESS! Update the shared session singleton with the logged-in user.
            System.out.println("[Controller]: Authentication successful.");

            currentSession.setCurrentUser(storedUser);
            return true;
        } else {
            // Failure: Log the attempt (for debugging) and return false.
            return false;
        }
    }

    /**
     *  Verifies a plain-text password against a stored hash value from the User object.
     * @param inputPassword The plain-text password entered by the user during login attempt.
     * @param user The User object containing the hashed password.
     * @return
     */
    private boolean verifyPassword(String inputPassword, User user) {
        // Check for nulls and empty inputs immediately
        if (inputPassword == null || user == null || inputPassword.isBlank()) {
            System.err.println("[SECURITY]: Validation Failed: Missing credentials.");
            return false;
        }

        // Retrieve the stored hashed password from the User object.
        // The 'storedHash' string is what you read from UsersData.json
        String storedHash = user.getPasswordHash();

        // Perform verifican using a secure hashing mechanism (e.g., BCrypt)
        boolean match = user.checkPassword(inputPassword, storedHash);

        if (!match) {
            System.err.println("[SECURITY]: Validation Failed: Provided password is invalid.");
        }
        return match; // Mock check for now.
    }
}
