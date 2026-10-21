package com.djrs.view.cli.menuoption;

import com.djrs.controller.CreateEditController;
import com.djrs.model.UserSession;
import com.djrs.view.cli.Cli;

public class CreateEditCli extends Cli {
    CreateEditController controller;
    UserSession currentSession;

    public CreateEditCli(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    protected void setupScene() {

    }

    @Override
    public void run() {
        setupScene();
        // Show rewind name and date range + songs table
        // Show options for editing rewind name, date range, searching songs, adding songs, deleting songs,
        // Cancelling create/edit -> going back to dashboard and saving and generating rewind presentation
        // If we are in create mode, prompt user for rewind name and date range isntead of showing the options
        controller.initialize();
    }
}
