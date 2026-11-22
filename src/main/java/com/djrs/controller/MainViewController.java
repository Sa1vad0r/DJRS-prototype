package com.djrs.controller;

import com.djrs.model.Rewind;
import com.djrs.model.User;
import com.djrs.model.UserSession;
import com.djrs.view.gui.Navigator;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy");

    private static final List<String[]> demoRewinds = new ArrayList<>(List.of(
            new String[]{"Spring 2026", "Mar 1, 2026 – May 31, 2026", "727", "king-crimson.png"},
            new String[]{"Vocaloid Summer 2026", "Jun 1, 2026 – Aug 31, 2026", "512", "teto.png"}));

    @FXML private Circle profileCircle;
    @FXML private FlowPane rewindCards;

    @FXML
    private void initialize() {
        getProfilePicture();
        refreshCards();
    }

    private void getProfilePicture() {
        Image image = new Image(getClass().getResource("/DRJS-prototype/images/triple-t.png").toExternalForm());
        profileCircle.setFill(new ImagePattern(image));
    }

    private void refreshCards() {
        rewindCards.getChildren().clear();

        for (String[] demo : new ArrayList<>(demoRewinds)) {
            rewindCards.getChildren().add(buildCard(demo[0], demo[1], demo[2], demo[3],
                    () -> demoRewinds.remove(demo)));
        }

        for (Rewind r : new ArrayList<>(UserSession.getInstance().getUserRewinds())) {
            String range = r.getDateRange().getStart().format(DATE_FORMAT)
                    + " – " + r.getDateRange().getEnd().format(DATE_FORMAT);
            rewindCards.getChildren().add(buildCard(r.getTitle(), range,
                    String.valueOf(r.getSongs().size()),
                    () -> UserSession.getInstance().getUserRewinds().remove(r)));
        }
    }

    private ImageView loadButtonIcon(String fileName) {
        URL url = getClass().getResource("/DRJS-prototype/images/" + fileName);
        if (url == null) {
            System.out.println("Missing icon: " + fileName);
            return null;
        }
        ImageView view = new ImageView(new Image(url.toExternalForm()));
        view.setFitWidth(16);
        view.setFitHeight(16);
        view.setPreserveRatio(true);
        return view;
    }

    private VBox buildCard(String title, String range, String songCount, Runnable onRemove) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-title");
        Label rangeLabel = new Label(range);
        rangeLabel.getStyleClass().add("card-meta");
        Label songsLabel = new Label(songCount + " songs analyzed");
        songsLabel.getStyleClass().add("card-songs");

        VBox text = new VBox(4, titleLabel, rangeLabel, songsLabel);
        HBox.setHgrow(text, Priority.ALWAYS);

        Rectangle art = new Rectangle(56, 56);
        art.setArcWidth(6);
        art.setArcHeight(6);
        art.setFill(Color.web("#c0c5c9"));
        art.getStyleClass().add("album-art");

        HBox top = new HBox(12, text, art);

        Button viewButton = new Button("View Rewind");
        viewButton.setGraphic(loadButtonIcon("view.png"));
        viewButton.getStyleClass().add("button-small");
        viewButton.setOnAction(e -> onView());

        Button editButton = new Button("Edit");
        editButton.setGraphic(loadButtonIcon("edit.png"));
        editButton.getStyleClass().addAll("button-small", "button-secondary");
        editButton.setOnAction(e -> onEditRewind());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button trashButton = new Button("🗑");
        trashButton.getStyleClass().addAll("button-small", "button-trash");
        trashButton.setOnAction(e -> confirmDelete(title, onRemove));

        HBox actions = new HBox(8, viewButton, editButton, spacer, trashButton);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(12, top, new Separator(), actions);
        card.getStyleClass().add("card");
        card.setPrefWidth(300);
        return card;
    }

    private VBox buildCard(String title, String range, String songCount, String imagePath, Runnable onRemove) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-title");
        Label rangeLabel = new Label(range);
        rangeLabel.getStyleClass().add("card-meta");
        Label songsLabel = new Label(songCount + " songs analyzed");
        songsLabel.getStyleClass().add("card-songs");

        VBox text = new VBox(4, titleLabel, rangeLabel, songsLabel);
        HBox.setHgrow(text, Priority.ALWAYS);

        Image image = new Image(getClass().getResource("/DRJS-prototype/images/" + imagePath).toExternalForm());
        ImageView art = new ImageView(image);
        art.setFitHeight(56);
        art.setFitWidth(56);

        Rectangle clip = new Rectangle(56,56);
        clip.setArcWidth(6);
        clip.setArcHeight(6);
        art.setClip(clip);

        HBox top = new HBox(12, text, art);

        Button viewButton = new Button("View Rewind");
        viewButton.setGraphic(loadButtonIcon("view.png"));
        viewButton.getStyleClass().add("button-small");
        viewButton.setOnAction(e -> onView());

        Button editButton = new Button("Edit");
        editButton.setGraphic(loadButtonIcon("edit.png"));
        editButton.getStyleClass().addAll("button-small", "button-secondary");
        editButton.setOnAction(e -> onEditRewind());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button trashButton = new Button("🗑");
        trashButton.getStyleClass().addAll("button-small", "button-trash");
        trashButton.setOnAction(e -> confirmDelete(title, onRemove));

        HBox actions = new HBox(8, viewButton, editButton, spacer, trashButton);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(12, top, new Separator(), actions);
        card.getStyleClass().add("card");
        card.setPrefWidth(300);
        return card;
    }

    private void confirmDelete(String title, Runnable onRemove) {
        Alert confirm = new Alert(Alert.AlertType.ERROR);
        confirm.setGraphic(new ImageView(new Image(getClass().getResource("/DRJS-prototype/images/delete-warning.png").toExternalForm())));

        // Setting up labels and buttons
        Label deleteLabel = new Label("Delete \"" + title + "\"?");
        deleteLabel.getStyleClass().add("title");
        Label subtitle = new Label("This action is permanent and cannot be undone.");
        subtitle.getStyleClass().add("subtitle");

        VBox headerText = new VBox(deleteLabel, subtitle);
        HBox header = new HBox(10, confirm.getGraphic(), headerText);
        headerText.setAlignment(Pos.CENTER_LEFT);
        header.setAlignment(Pos.CENTER_LEFT);
        confirm.setGraphic(null);
        confirm.getDialogPane().setHeader(header);

        ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        confirm.getButtonTypes().setAll(delete, ButtonType.CANCEL);

        Button cancel = (Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.getStyleClass().add("button-secondary");
        Button deleteButton = (Button) confirm.getDialogPane().lookupButton(delete);
        deleteButton.getStyleClass().add("button-danger");

        Label first = new Label("Are you sure you want to delete ");
        Label titleLabel = new Label(title);
        Label last = new Label("?");

        first.getStyleClass().add("subtitle");
        titleLabel.getStyleClass().add("subtitle-bold");
        last.getStyleClass().add("subtitle");

        HBox message = new HBox(0, first, titleLabel, last);
        message.setAlignment(Pos.CENTER);

        VBox content = new VBox(10, new Separator(), message);
        content.setAlignment(Pos.CENTER);
        content.setFillWidth(true);
        content.setMaxWidth(Double.MAX_VALUE);

        confirm.getDialogPane().setContent(content);

        URL css = getClass().getResource("/DRJS-prototype/style/theme.css");
        if (css != null) {
            confirm.getDialogPane().getStylesheets().add(css.toExternalForm());
        }

        confirm.showAndWait().ifPresent(choice -> {
            if (choice == delete) {
                onRemove.run();
                refreshCards();
            }
        });
    }

    // Rewind is null for now; This would be the rewind clicked later
    private void onEditRewind() {
        Navigator.showCreateEdit(CreateEditController.PageState.EDIT, null);
    }

    @FXML
    private void onAddRewind() {
        Navigator.showCreateEdit(CreateEditController.PageState.CREATE, null);
    }

    @FXML
    private void onView() {
        Navigator.show(Navigator.VIEW_REWIND);
    }

    @FXML
    private void onOpenSettings() {
        Navigator.show(Navigator.SETTINGS);
    }

    @FXML
    private void onSignOut() {
        UserSession.getInstance().setCurrentUser(new User());
        Navigator.show(Navigator.LOGIN);
    }
}
