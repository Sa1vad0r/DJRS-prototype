package com.djrs.view.cli.menuoption;

import com.djrs.controller.ViewRewindController;
import com.djrs.model.UserSession;
import com.djrs.view.cli.Cli;

public class ViewRewindCli extends Cli {
    private ViewRewindController controller;

    public ViewRewindCli(UserSession currentSession) {
        super(currentSession);
    }

    public ViewRewindController getController() {
        return controller;
    }

    public void setController(ViewRewindController controller) {
        this.controller = controller;
    }

    @Override
    protected void setupScene() {
        printSectionHeader("View Rewind");
    }

    @Override
    public void run() {
        setupScene();
        // Show first page of presentation
        controller.initialize();
    }
}
