package com.djrs.view.cli.menuoption;

import com.djrs.controller.ViewRewindController;
import com.djrs.view.cli.Cli;

public class ViewRewindCli extends Cli {
    private ViewRewindController controller;

    public ViewRewindCli(ViewRewindController controller) {
        this.controller = controller;
    }

    public ViewRewindController getController() {
        return controller;
    }

    public void setController(ViewRewindController controller) {
        this.controller = controller;
    }

    @Override
    protected void setupScene() {

    }

    @Override
    public void run() {
        setupScene();
        // Show first page of presentation
        controller.initialize();
    }
}
