package com.djrs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller for /DRJS-prototype/layouts/main-scene.fxml.
 * Rows are placeholder data for now - replace with the user's real rewinds
 * (e.g. from UserSession / the rewinds.json store).
 */
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

        // View/Delete only make sense with a selected row
        viewButton.disableProperty().bind(rewindTable.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(rewindTable.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void onAddRewind() {
        System.out.println("[GUI] Add Rewind clicked");   // TODO: open Create/Edit view
    }

    @FXML
    private void onView() {
        System.out.println("[GUI] View: " + rewindTable.getSelectionModel().getSelectedItem()[0]);   // TODO
    }

    @FXML
    private void onDelete() {
        System.out.println("[GUI] Delete: " + rewindTable.getSelectionModel().getSelectedItem()[0]);   // TODO
    }

    @FXML
    private void onSignOut() {
        System.out.println("[GUI] Sign out clicked");   // TODO: clear UserSession, show login view
    }
}
