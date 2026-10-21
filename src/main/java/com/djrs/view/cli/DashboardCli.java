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
        // Let controller handle input
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

    }
}
