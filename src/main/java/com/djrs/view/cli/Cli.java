package com.djrs.view.cli;

import com.djrs.model.UserSession;

public abstract class Cli {
    private UserSession currentSession; // This state is passed into the constructor

    public Cli() {
        currentSession = UserSession.getInstance(); // Get instance of UserSession singleton
    }

    /**
     *  Mandatory method to initialize any necessary resources (e.g., loading mock data).
     *  All child classes MUST call this first.
     */
    protected abstract void setupScene();

    /**
     *  Defines the main logic loop and must be overriden by children.
     */
    public abstract void run();
}
