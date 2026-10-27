package com.djrs.view.cli.menuoption;

import com.djrs.controller.CreateEditController;
import com.djrs.controller.CreateEditController.PageState;
import com.djrs.model.UserSession;
import com.djrs.view.cli.Cli;

public class CreateEditCli extends Cli {

    private CreateEditController controller;
    private UserSession currentSession;
    private CreateEditController.PageState pageState;

    public CreateEditCli(UserSession currentSession, PageState pageState) {
        super(currentSession);
        this.pageState = pageState;
        controller = new CreateEditController(pageState);
    }

    public PageState getPageState() {
        return pageState;
    }

    public void setPageState(PageState pageState) {
        this.pageState = pageState;
    }

    public CreateEditController getController() {
        return controller;
    }

    public void setController(CreateEditController controller) {
        this.controller = controller;
    }

    public void showRewindDetails() {
        if (pageState == PageState.EDIT) {
            controller.showBasicRewindInfo();
        } else {
            showRewindEditPrompt();
            controller.showBasicRewindInfo();
        }

        System.out.println();
    }

    public void showPageOptionsPrompt() {
        int choice;

        do {
            System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
            controller.showTracks(null);
            System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");

            System.out.println("[1] Edit Rewind Info");
            System.out.println("[2] Manually Add Song ");
            System.out.println("[3] Edit Existing Song");
            System.out.println("[4] Cancel Changes");
            System.out.println("[5] Save & Generate Rewind");
            System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
            System.out.println("Enter your choice: ");
            choice = controller.readPageOptions();

            switch (choice) {
                case 1:
                    controller.readBasicRewindInfo();
                    controller.showBasicRewindInfo();
                    break;
                case 2:
                    showAddSongsPrompt();
                    break;
                case 3:
                    showEditSongPrompt();
                    break;
                case 4:
                    // Route to dashboard
                    break;
                case 5:
                    // Save and generate rewind
                    break;
                default:
                    System.out.println("Invalid choice. Choice must be between 1 and 5");
                    break;
            }
        } while (choice != 4 && choice != 5);
    }

    private void showAddSongsPrompt() {
        controller.addSongInfo();
    }
    private void showEditSongPrompt() {
        controller.editSongInfo();
    }

    public void showRewindEditPrompt() {
        controller.readBasicRewindInfo();
    }

    @Override
    protected void setupScene() {
        printSectionHeader("Create/Edit Rewind");
    }

    @Override
    public void run() {
        // Show rewind name and date range + songs table
        // Show options for editing rewind name, date range, searching songs, adding
        // songs, deleting songs,
        // Cancelling create/edit -> going back to dashboard and saving and generating
        // rewind presentation
        // If we are in create mode, prompt user for rewind name and date range instead
        // of showing the options
        controller.initialize();
        setupScene();
        showRewindDetails();
        showPageOptionsPrompt();
    }
}
