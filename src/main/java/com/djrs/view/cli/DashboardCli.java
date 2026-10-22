package com.djrs.view.cli;

import com.djrs.controller.DashboardController;
import com.djrs.model.UserSession;

public class DashboardCli extends Cli {
    private DashboardController controller = new DashboardController();
    private UserSession currentSession;

    public DashboardCli(UserSession currentSession) {
        super(currentSession);
    }

    public DashboardController getController() {
        return controller;
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    public void showDeleteRewindPrompt() {
        // Ask if user wants to delete or rewind
        System.out.println("Would you like to delete or rewind?");
        System.out.println("[1] Delete");
        System.out.println("[2] Rewind");
        // Let controller handle input
        int userChoice;

        userChoice = controller.readUserInput();

        switch (userChoice) {
            case 1:
                controller.delete();
                break;
            case 2:
                controller.rewind();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userChoice);
        }
    }

    @Override
    protected void setupScene() {
        printSectionHeader("Dashboard");
    }

    @Override
    public void run() {
        setupScene();
        // Show username and rewinds
        controller.initialize();

        showDeleteRewindPrompt();
    }
}
