package com.djrs.controller;

import com.djrs.io.InputValidator;

public class DashboardController {
    public void initialize() {
        // --- Setup tasks go here ---

        // Load system configuration data:
        System.out.println("[Controller]: Loading system configuration data...");

        // Load user data:


    }

    public int readUserInput(){
        int userChoice = 0;

        userChoice = InputValidator.readIntNumberBetween(1, 2, "Invalid input. Please enter a number between 1 and 2.");

        return userChoice;
    }

    public void delete(){
        System.out.println("Pick a rewind to delete: ");
        //Todo: Delete logic for the json files
    }

    public void rewind(){
        System.out.println("Pick a rewind to view: ");
        //Todo: Rewind logic for the json files
    }
}
