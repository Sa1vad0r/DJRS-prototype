package com.djrs.controller;

import com.djrs.model.Rewind;
import com.djrs.model.UserSession;
import com.djrs.model.User;
import com.djrs.view.gui.Navigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainViewController {

    @FXML private TableView<String[]> rewindTable;
    @FXML private TableColumn<String[], String> nameColumn;
    @FXML private TableColumn<String[], String> rangeColumn;
    @FXML private TableColumn<String[], String> songsColumn;
    @FXML private Button viewButton;
    @FXML private Button deleteButton;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[0]));
        rangeColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[1]));
        songsColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[2]));

        rewindTable.setPlaceholder(new Label("No rewinds yet - click \"+ Add Rewind\"."));
        rewindTable.setItems(FXCollections.observableArrayList(
                new String[]{"Spring 2026", "2026-03-01 to 2026-05-31", "727"},
                new String[]{"Summer 2026", "2026-06-01 to 2026-08-31", "512"}));
        for (Rewind r : UserSession.getInstance().getUserRewinds()) {
            rewindTable.getItems().add(new String[]{
                    r.getTitle(),
                    r.getDateRange().getStart().toLocalDate() + " to " + r.getDateRange().getEnd().toLocalDate(),
                    String.valueOf(r.getSongs().size())});
        }

        viewButton.disableProperty().bind(rewindTable.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(rewindTable.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void onAddRewind() {
        Navigator.show(Navigator.CREATE_REWIND);
    }

    @FXML
    private void onView() {
        Navigator.show(Navigator.VIEW_REWIND);
    }

    @FXML
    private void onDelete() {
        System.out.println("[GUI] Delete: " + rewindTable.getSelectionModel().getSelectedItem()[0]);
    }

    @FXML
    private void onSignOut() {
        UserSession.getInstance().setCurrentUser(new User());
        Navigator.show(Navigator.LOGIN);
    }
}
