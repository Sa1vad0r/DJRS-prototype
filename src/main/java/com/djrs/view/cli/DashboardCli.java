package com.djrs.view.cli;

import com.djrs.controller.DashboardController;
import com.djrs.model.UserSession;

public class DashboardCli extends Cli {
    private DashboardController controller = new DashboardController();
    private UserSession currentSession;

    public DashboardCli(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    public DashboardController getController() {
        return controller;
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    public UserSession getSession() {
        return currentSession;
    }

    public void setSession(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    public void showDeleteRewindPrompt() {
        // Ask if user wants to delete or rewind
        // Let controller handle input
    }

    @Override
    protected void setupScene() {

    }

    @Override
    public void run() {
        setupScene();
        // Show username and rewinds
        controller.initialize();

    }
}
