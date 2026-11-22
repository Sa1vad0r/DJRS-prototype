
package com.djrs.controller;

import com.djrs.view.gui.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Controller for rewind-view.fxml.
 * One FXML holds all 4 slides; this class shows one at a time.
 */
public class ViewRewindController {

    private static final String LIBRARY_FXML = "/DRJS-prototype/layouts/dashboard.fxml";

    @FXML private StackPane slideStack;
    @FXML private HBox segmentBar;
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private PieChart genreChart;
    @FXML private Circle artistCircle;
    @FXML private Rectangle songRectangle;

    private int index = 0;

    /** Runs automatically once, right after the FXML finishes loading. */
    @FXML
    public void initialize() {
        getStatImages();
        genreChart.getData().addAll(
                new PieChart.Data("Vocaloid", 80),
                new PieChart.Data("Hip-Hop", 15),
                new PieChart.Data("Other", 5)
        );
        show(0);
    }

    private void getStatImages() {
        Image songImage = new Image(getClass().getResource("/DRJS-prototype/images/flavor-foley.png").toExternalForm());
        Image artistImage = new Image(getClass().getResource("/DRJS-prototype/images/jamie-page.png").toExternalForm());
        songRectangle.setFill(new ImagePattern(songImage));
        artistCircle.setFill(new ImagePattern(artistImage));
    }

    @FXML
    private void onNext() {
        if (index < lastIndex()) {
            index++;
            show(index);
        } else {
            goToLibrary();
        }
    }

    @FXML
    private void onPrevious() {
        if (index > 0) {
            index--;
            show(index);
        }
    }

    @FXML
    private void onClose() {
        System.out.println("Closing rewind...");
        goToLibrary();
    }

    private int lastIndex() {
        return slideStack.getChildren().size() - 1;
    }

    /** Shows slide number i and hides the rest. */
    private void show(int i) {
        for (int n = 0; n < slideStack.getChildren().size(); n++) {
            Node slide = slideStack.getChildren().get(n);
            boolean isThisOne = (n == i);
            slide.setVisible(isThisOne);
            slide.setManaged(isThisOne);
        }

        for (int n = 0; n < segmentBar.getChildren().size(); n++) {
            Node segment = segmentBar.getChildren().get(n);
            segment.getStyleClass().remove("segment-active");
            if (n <= i) {
                segment.getStyleClass().add("segment-active");
            }
        }

        prevButton.setDisable(i == 0);
        nextButton.setText(i == lastIndex() ? "Back To Library  →" : "Next  →");
    }

    /** Swaps the screen in the current Scene (the stylesheet stays attached). */
    private void goToLibrary() {
        Navigator.show(Navigator.DASHBOARD);
//        try {
//            var root = new FXMLLoader(getClass().getResource(LIBRARY_FXML)).load();
//            nextButton.getScene().setRoot((javafx.scene.Parent) root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
 