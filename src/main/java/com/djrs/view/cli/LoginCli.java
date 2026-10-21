package com.djrs.view.cli;

import com.djrs.controller.LoginController;
import com.djrs.model.UserSession;

public class LoginCli extends Cli {
    private LoginController controller = new LoginController();

    public LoginCli(UserSession currentSession) {
        super(currentSession);
    }

    public LoginController getController() {
        return controller;
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public UserSession runLoginPrompt() {
        setupScene();
        run();
        // Ask if user wants to log in or create an account (Let controller handle input)
        // Maybe an exit option?

        // Possible while/do-while loop:
        // If user has account, show username: password: input prompts, otherwise call showCreateAccountPrompt
        // Call controller to handle input and let controller call validation of user credentials
        // Invalid credentials: show error message, ask again
        // Add exit option

        // Return user session if successful, null otherwise
        return null;
    }

    public UserSession showCreateAccountPrompt() {
        // Show username: password: input prompts, call controller to handle input and let controller call validation of user credentials

        // Return user session if account creation successful, null otherwise
        return null;
    }

    @Override
    protected void setupScene() {
        printSectionHeader("Login");
        System.out.println("--- Welcome Back ---");
        System.out.println("[Sign in to revisit your listening history and create a new rewind.");
    }

    @Override
    public void run() {
        setupScene();
        controller.initialize();
    }
}
