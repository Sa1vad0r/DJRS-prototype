package com.djrs.view.cli;

import com.djrs.controller.LoginController;
import com.djrs.io.InputValidator;
import com.djrs.model.UserSession;

public class LoginCli extends Cli {
    private LoginController controller;

    public LoginCli(UserSession currentSession) {
        super(currentSession);
        controller = new LoginController(currentSession);
    }

    public LoginController getController() {
        return controller;
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    /**
     * @return true if the user is now logged in (session singleton has a current user), false otherwise.
     */
    public boolean runLoginPrompt() {
        int userChoice = 0;
        boolean success = false;

        setupScene();
        run();

        // Ask if user wants to log in or create an account (Let controller handle input)
        System.out.println("[1] Log in");
        System.out.println("[2] Create an account");
        System.out.println("[3] Exit");

        userChoice = controller.readBeginningLoginPrompt();

        switch (userChoice) {
            case 1:
                success = showLoginPrompt();
                break;
            case 2:
                success = showCreateAccountPrompt();
                break;
            case 3:
                success = false;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userChoice);
        }

        return success;
    }

    private boolean showLoginPrompt() {
        boolean success = false;
        System.out.println("Log in --------\n");

        while (!success) {
            System.out.println("enter your username and password (or type 'esc' to go back)");
            String username = InputValidator.readString();

            if (username.equalsIgnoreCase("esc")) {
                return false;
            }

            String password = InputValidator.readString();

            success = controller.attemptLogin(username, password);

            if (!success) {
                System.out.println("username and/or password is invalid");
            }
        }
        return success;
    }

    public boolean showCreateAccountPrompt() {
        // Show username: password: input prompts, call controller to handle input and let controller call validation of user credentials
        System.out.println("--- Create Account ---");

        // TODO: not implemented yet
        return false;
    }

    @Override
    protected void setupScene() {
        printSectionHeader("Login");
        System.out.println("--- Welcome Back ---");
        System.out.println("[Sign in to revisit your listening history and create a new rewind.]");
    }

    @Override
    public void run() {
        controller.initialize();
    }
}
