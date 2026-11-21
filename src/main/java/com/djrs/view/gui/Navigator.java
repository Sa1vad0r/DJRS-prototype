package com.djrs.view.gui;

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
}
