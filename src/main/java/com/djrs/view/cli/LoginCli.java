package com.djrs.view.cli;

import com.djrs.controller.LoginController;
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

    public UserSession runLoginPrompt() {
        int userChoice = 0;

        setupScene();
        run();

        // Ask if user wants to log in or create an account (Let controller handle input)
        // Maybe an exit option?
        System.out.println("[1] Log in");
        System.out.println("[2] Create an account");
        System.out.println("[3] Exit");

        userChoice = controller.readBeginningLoginPrompt();

        switch(userChoice) {
            case 1:
                setSession(showLoginPrompt());
                break;
            case 2:
                showCreateAccountPrompt();
                break;
            case 3:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userChoice);
        }


        // Possible while/do-while loop:
        // If user has account, show username: password: input prompts, otherwise call showCreateAccountPrompt
        // Call controller to handle input and let controller call validation of user credentials
        // Invalid credentials: show error message, ask again
        // Add exit option

        // Return user session if successful, null otherwise
        return null;
    }

    private UserSession showLoginPrompt() {

        return null;
    }

    public UserSession showCreateAccountPrompt() {
        // Show username: password: input prompts, call controller to handle input and let controller call validation of user credentials
        System.out.println("--- Create Account ---");

        // Return user session if account creation successful, null otherwise
        return null;
    }

    @Override
    protected void setupScene() {
        printSectionHeader("Login");
        System.out.println("--- Welcome Back ---");
        System.out.println("[Sign in to revisit your listening history and create a new rewind.]");
    }

    @Override
    public void run() {
        setupScene();
        controller.initialize();
    }
}
