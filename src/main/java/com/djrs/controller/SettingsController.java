package com.djrs.controller;

import com.djrs.model.User;
import com.djrs.model.UserSession;
import com.djrs.model.UsersData;
import com.djrs.view.gui.Navigator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;

/**
 * Controller for settings.fxml.
 * Every @FXML field name must match a fx:id in the FXML,
 * and every @FXML method must match an onAction="#..." in the FXML.
 */
public class SettingsController {

    /** Change this to match whatever your create-account screen requires. */
    private static final int MIN_PASSWORD_LENGTH = 4;

    @FXML private Circle bigProfileCircle;
    @FXML private Circle littleProfileCircle;

    @FXML private TextField displayNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private Label profileToast;
    @FXML private Label passwordToast;
    @FXML private Label dataToast;

    @FXML private Label profileError;
    @FXML private Label passwordError;
    @FXML private Label dangerError;

    private final UserSession session = UserSession.getInstance();

    /** Runs once after the FXML loads: fills the fields from the logged-in user. */
    @FXML
    private void initialize() {
        getProfilePicture();
        User user = session.getCurrentUser();

        displayNameField.setText((nz(user.getFirstName()) + " " + nz(user.getLastName())).trim());
        usernameField.setText(nz(user.getUserName()));

        hide(profileError);
        hide(passwordError);
        hide(dangerError);
    }

    private void getProfilePicture() {
        Image image = new Image(getClass().getResource("/DRJS-prototype/images/triple-t.png").toExternalForm());
        littleProfileCircle.setFill(new ImagePattern(image));
        bigProfileCircle.setFill(new ImagePattern(image));
    }


    // ---------- Back to Dashboard ----------
    @FXML
    private void onBack() {
        Navigator.show(Navigator.DASHBOARD);
    }

    // ---------- Profile ----------

    @FXML
    private void onChangePicture() {
        // TODO: Add functionality
        System.out.println("Change picture clicked");
    }

    @FXML
    private void onSaveProfile() {
        hide(profileError);

        String displayName = displayNameField.getText().trim();
        String username = usernameField.getText().trim();

        // 1. Check the inputs
        if (displayName.isEmpty()) {
            showError(profileError, "Display name can't be empty.");
            return;
        }
        if (username.isEmpty()) {
            showError(profileError, "Username can't be empty.");
            return;
        }

        User current = session.getCurrentUser();
        if (nz(current.getUserID()).isEmpty()) {
            showError(profileError, "No user is signed in.");
            return;
        }

        // 2. Load the saved users and make sure the username isn't taken by someone else
        UsersData data = loadUsers();
        User taken = data.findUser(username);
        if (taken != null && !nz(taken.getUserID()).equals(current.getUserID())) {
            showError(profileError, "That username is already taken.");
            return;
        }

        User stored = findById(data, current.getUserID());
        if (stored == null) {
            showError(profileError, "Couldn't find your account in the saved data.");
            return;
        }

        // 3. "Tripple T" -> first name "Tripple", last name "T"
        String[] parts = displayName.split("\\s+", 2);
        String first = parts[0];
        String last = parts.length > 1 ? parts[1] : "";

        stored.setFirstName(first);
        stored.setLastName(last);
        stored.setUserName(username);

        // 4. Save to the file, then update the signed-in user so the rest of the app sees it
        if (!data.saveDataToFile()) {
            showError(profileError, "Couldn't save your changes. Try again.");
            return;
        }
        current.setFirstName(first);
        current.setLastName(last);
        current.setUserName(username);

        showToast(profileToast);
    }

    // ---------- Security ----------

    @FXML
    private void onSavePassword() {
        hide(passwordError);

        String newPassword = passwordField.getText();

        if (newPassword.isBlank()) {
            showError(passwordError, "Password can't be empty.");
            return;
        }
        if (newPassword.length() < MIN_PASSWORD_LENGTH) {
            showError(passwordError, "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
            return;
        }

        User current = session.getCurrentUser();
        if (nz(current.getUserID()).isEmpty()) {
            showError(passwordError, "No user is signed in.");
            return;
        }

        UsersData data = loadUsers();
        User stored = findById(data, current.getUserID());
        if (stored == null) {
            showError(passwordError, "Couldn't find your account in the saved data.");
            return;
        }

        // IMPORTANT: stored as plain text on purpose. PasswordHasher.checkPassword() compares
        // plain equality, so saving PasswordHasher.hash(...) here would lock the user out.
        // When you add real hashing, change BOTH PasswordHasher methods, then use hash() here.
        stored.setPasswordHash(newPassword);

        if (!data.saveDataToFile()) {
            showError(passwordError, "Couldn't save your password. Try again.");
            return;
        }
        current.setPasswordHash(newPassword);

        passwordField.clear();
        showToast(passwordToast);
    }

    // ---------- Danger Zone ----------

    @FXML
    private void onDeleteData() {
        hide(dangerError);
        if (confirm("Delete all of your rewinds? This cannot be undone.")) {
            session.getUserRewinds().clear();
            showToast(dataToast);
        }
    }

    @FXML
    private void onDeleteAccount() {
        hide(dangerError);
        if (!confirm("Delete your account? This cannot be undone.")) {
            return;
        }

        User current = session.getCurrentUser();
        UsersData data = loadUsers();
        User stored = findById(data, current.getUserID());

        // deleteUser() saves the file itself and returns false on any problem
        if (stored == null || !data.deleteUser(stored)) {
            showError(dangerError, "Couldn't delete your account. Try again.");
            return;
        }

        session.getUserRewinds().clear();
        session.setCurrentUser(new User());
        Navigator.show(Navigator.LOGIN);
    }

    // ---------- Sign Out ----------

    @FXML
    private void onSignOut() {
        session.setCurrentUser(new User());
        Navigator.show(Navigator.LOGIN);
    }

    // ---------- helpers ----------

    /** Reads data/usersData.json into a fresh UsersData. */
    private UsersData loadUsers() {
        UsersData data = new UsersData();
        data.loadUsersData();
        return data;
    }

    /** Finds the saved copy of a user by their id (the id never changes, unlike the username). */
    private User findById(UsersData data, String id) {
        for (User u : data.getUsers()) {
            if (nz(u.getUserID()).equals(nz(id))) {
                return u;
            }
        }
        return null;
    }

    /** null -> "" so we never crash on a missing field. */
    private String nz(String s) {
        return s == null ? "" : s;
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hide(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    /** Shows a toast label for 3 seconds, then hides it again. */
    private void showToast(Label toast) {
        toast.setVisible(true);
        toast.setManaged(true);
        PauseTransition wait = new PauseTransition(Duration.seconds(3));
        wait.setOnFinished(e -> hide(toast));
        wait.play();
    }

    /** Pops up an OK/Cancel box (themed like the delete-rewind box). True if OK was clicked. */
    private boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText(null);

        URL css = getClass().getResource("/DRJS-prototype/style/theme.css");
        if (css != null) {
            alert.getDialogPane().getStylesheets().add(css.toExternalForm());
        }

        return alert.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }
}
