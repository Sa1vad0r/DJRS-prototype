package com.djrs;

import com.djrs.controller.CreateEditController.PageState;
import com.djrs.model.UserSession;
import com.djrs.view.cli.DashboardCli;
import com.djrs.view.cli.LoginCli;
import com.djrs.view.cli.menuoption.CreateEditCli;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Entry point.
 *   GUI (default):  mvn javafx:run
 *   Old CLI flow:   run with the program argument "cli"
 */
public class MainApplication extends Application {

    private static final String MAIN_SCENE_FXML = "/DRJS-prototype/layouts/main-scene.fxml";
    private static final String THEME_CSS = "/DRJS-prototype/style/theme.css";

    @Override
    public void start(Stage stage) throws IOException {
        // Load the main view from FXML
        URL fxml = MainApplication.class.getResource(MAIN_SCENE_FXML);
        if (fxml == null) {
            throw new IOException("Missing resource: " + MAIN_SCENE_FXML);
        }
        Parent root = new FXMLLoader(fxml).load();

        Scene scene = new Scene(root, 1000, 680);

        // Apply the DJRS theme to the whole scene
        URL css = MainApplication.class.getResource(THEME_CSS);
        if (css == null) {
            throw new IOException("Missing resource: " + THEME_CSS);
        }
        scene.getStylesheets().add(css.toExternalForm());

        stage.setTitle("DJRS Rewind");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            runCli();
        } else {
            launch(args);
        }
    }

    /** The original console flow, unchanged. */
    private static void runCli() {
        // Sample flow of main
        // UserSession is a singleton - everyone shares the same instance via getInstance()
        UserSession currentSession = UserSession.getInstance();
        LoginCli userLogin = new LoginCli(currentSession);

        boolean loggedIn = userLogin.runLoginPrompt();

        if (!loggedIn) {
            System.out.println("[System] Exiting.");
            return;
        }

        // Added this for test purposes; Uncomment/Remove after
        CreateEditCli createEdit = new CreateEditCli(currentSession, PageState.CREATE);
        createEdit.run();

        // Now that the session exists, we can load the main dashboard scene
        DashboardCli dashboard = new DashboardCli(currentSession);
        dashboard.setSession(currentSession);
        dashboard.run();
    }
}
