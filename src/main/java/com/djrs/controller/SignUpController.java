package com.djrs.controller;

import com.djrs.model.User;
import com.djrs.model.UserSession;
import com.djrs.model.UsersData;
import com.djrs.view.gui.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
    UserSession currentSession;
    UsersData users;

    @FXML private TextField lastnameField;
    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    public SignUpController() {
        this(UserSession.getInstance());
    }

    public SignUpController(UserSession currentSession) {
        this.currentSession = currentSession;
    }

    public void initialize() {
        // --- Setup tasks go here ---
        users = new UsersData();
        users.loadUsersData();

        // Load system configuration data:
        System.out.println("[Controller]: Loading system configuration data...");
    }

    @FXML
    private void goToSignIn() {
        Navigator.show(Navigator.LOGIN);
    }

    @FXML
    private void onSignUp() {
        String first = nameField.getText() == null ? "" : nameField.getText().trim();
        String last = lastnameField.getText() == null ? "" : lastnameField.getText().trim();
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password are required.");
            return;
        }
        if (users.findUser(username) != null) {
            errorLabel.setText("That username is already taken.");
            return;
        }

        String id = String.valueOf(users.getUsers().size() + 1);
        User newUser = new User(id, first, last, username, password);
        users.addUser(newUser);
        users.saveDataToFile();

        currentSession.setCurrentUser(newUser);
        Navigator.show(Navigator.DASHBOARD);
    }
}
