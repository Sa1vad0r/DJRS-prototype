package com.djrs.view.cli;

import com.djrs.model.UserSession;

import java.util.Locale;

public abstract class Cli {
    private UserSession currentSession; // This state is passed into the constructor

    public Cli(UserSession currentSession) {
        this.currentSession = currentSession; // Get instance of UserSession singleton
    }

    public UserSession getSession() {
        return currentSession;
    }

    public void setSession(UserSession currentSession) {
        this.currentSession = currentSession;
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

    public void printSectionHeader(String header) {
        // Header settings
        final int MIN_WIDTH = 50; // minimum width between the '|'
        final int PADDING = 2; // Number of spaces on each side of the text

        String headerText = header.toUpperCase();
        int width = Math.max(MIN_WIDTH, headerText.length() + (PADDING * 2)); // Total width of the section header

        // Center Header Text within the width
        int totalSpace = width - headerText.length();
        int left = totalSpace / 2;
        int right = totalSpace - left;

        String horizontalBorder = "+" + "=".repeat(width) + "+";
        String paddedHeader = "|" + " ".repeat(left) + headerText + " ".repeat(right) + "|";

        System.out.println(horizontalBorder);
        System.out.println(paddedHeader);
        System.out.println(horizontalBorder);
    }
}
