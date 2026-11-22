package com.djrs.view.gui;

import com.djrs.controller.CreateEditController;
import com.djrs.model.Rewind;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class Navigator {
    public static final String CREATE_USER = "/DRJS-prototype/layouts/sign-up.fxml";
    public static final String CREATE_REWIND = "/DRJS-prototype/layouts/create-edit-rewind.fxml";
    public static final String LOGIN = "/DRJS-prototype/layouts/login-view.fxml";
    public static final String DASHBOARD = "/DRJS-prototype/layouts/dashboard.fxml";
    public static final String VIEW_REWIND = "/DRJS-prototype/layouts/rewind.fxml";
    public static final String SETTINGS = "/DRJS-prototype/layouts/settings.fxml";

    private static Scene scene;

    public static void init(Scene s) {
        scene = s;
    }

    public static void show(String fxmlPath) {
        try {
            URL url = Navigator.class.getResource(fxmlPath);
            if (url == null) throw new IOException("Missing resource: " + fxmlPath);
            Parent root = new FXMLLoader(url).load();
            scene.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not load " + fxmlPath, e);
        }
    }

    public static void showCreateEdit(CreateEditController.PageState state, Rewind rewind) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Navigator.class.getResource(CREATE_REWIND)); // your actual path
            loader.setControllerFactory(type -> new CreateEditController(state, rewind));
            Parent root = loader.load();
            scene.setRoot(root); // whatever your scene variable is called
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
