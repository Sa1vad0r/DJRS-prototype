package com.djrs.view.gui;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Sample screen showing how to use /DRJS-prototype/style/theme.css.
 * Run with:  mvn javafx:run
 *
 * The key lines are in start(): load the stylesheet once on the Scene, then
 * opt in to the look of each node with getStyleClass().add("..."). Standard
 * controls (Button, TextField, TableView, ...) are styled automatically.
 */
public class ThemePreview extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setTop(buildNavBar());

        VBox content = new VBox(24,
                buildLoginAndInputs(),
                section("Your Rewinds", buildTable()),
                section("Rewind slide", buildSlide()));
        content.setPadding(new Insets(24));

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        root.setCenter(scroll);

        Scene scene = new Scene(root, 1000, 760);
        // 1) Load the theme once per Scene
        scene.getStylesheets().add(getClass().getResource("/DRJS-prototype/style/theme.css").toExternalForm());

        stage.setTitle("DJRS Rewind - Theme Preview");
        stage.setScene(scene);
        stage.show();
    }

    // ---- Nav bar: .nav-bar, .app-logo, .nav-link, .status-dot --------------
    private HBox buildNavBar() {
        Label logo = new Label("DJRS rewind");
        logo.getStyleClass().add("app-logo");

        Button home = navLink("Home");
        Button rewinds = navLink("Rewinds");
        Button settings = navLink("Settings");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Circle dot = new Circle(7);
        dot.getStyleClass().add("status-dot");
        dot.setFill(Color.web("#efefb3")); // Shapes use -fx-fill; set directly or via a style class

        HBox bar = new HBox(logo, home, rewinds, settings, spacer, dot);
        bar.getStyleClass().add("nav-bar");
        return bar;
    }

    private Button navLink(String text) {
        Button b = new Button(text);
        b.getStyleClass().add("nav-link");
        return b;
    }

    // ---- Login box + buttons + inputs --------------------------------------
    private HBox buildLoginAndInputs() {
        // .auth-box + .title/.subtitle + normal TextField/PasswordField/Button
        Label title = new Label("Welcome back");
        title.getStyleClass().add("title");
        Label sub = new Label("Sign in to see your rewind");
        sub.getStyleClass().add("subtitle");

        TextField user = new TextField();
        user.setPromptText("Username");
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        Label error = new Label("Incorrect password");
        error.getStyleClass().add("alert-error");

        Button signIn = new Button("Sign in");
        signIn.setMaxWidth(Double.MAX_VALUE);
        Button create = new Button("Create account");
        create.getStyleClass().add("button-link");

        VBox auth = new VBox(10, title, sub, user, pass, error, signIn, create);
        auth.getStyleClass().add("auth-box");
        auth.setPrefWidth(320);

        // Button variants
        Button primary = new Button("Primary");
        Button accent = new Button("+ Add Rewind");
        accent.getStyleClass().add("button-accent");
        Button danger = new Button("Delete");
        danger.getStyleClass().add("button-danger");
        Button ghost = new Button("Previous");
        ghost.getStyleClass().add("button-ghost");
        Button disabled = new Button("Disabled");
        disabled.setDisable(true);

        ComboBox<String> range = new ComboBox<>(FXCollections.observableArrayList(
                "Spring 2026", "Summer 2026", "Fall 2026"));
        range.setValue("Spring 2026");
        CheckBox check = new CheckBox("Include skipped songs");
        check.setSelected(true);
        ToggleButton toggle = new ToggleButton("Public");
        toggle.setSelected(true);
        ProgressBar progress = new ProgressBar(0.6);
        progress.setPrefWidth(220);

        FlowPane buttons = new FlowPane(10, 10, primary, accent, danger, ghost, disabled);
        VBox controls = new VBox(14, sectionLabel("Buttons"), buttons,
                sectionLabel("Inputs"), new HBox(10, range, check, toggle),
                sectionLabel("Progress"), progress);
        controls.getStyleClass().add("card");
        HBox.setHgrow(controls, Priority.ALWAYS);

        HBox row = new HBox(24, auth, controls);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    // ---- Table: styled automatically; just add the node --------------------
    private TableView<String[]> buildTable() {
        TableView<String[]> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(220);
        String[] heads = {"Song", "Artist", "Genre", "Plays"};
        for (int i = 0; i < heads.length; i++) {
            final int col = i;
            TableColumn<String[], String> c = new TableColumn<>(heads[i]);
            c.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[col]));
            table.getColumns().add(c);
        }
        table.setItems(FXCollections.observableArrayList(
                new String[]{"Spoken For", "FLAVOR FOLEY", "Vocaloid", "33"},
                new String[]{"Song Two", "Jamie Paige", "Vocaloid", "21"},
                new String[]{"Song Three", "Artist C", "Pop", "17"},
                new String[]{"Song Four", "Artist D", "Rock", "9"}));
        return table;
    }

    // ---- Rewind slide: .rewind-slide, .slide-title, .slide-stat, .segment --
    private StackPane buildSlide() {
        Circle g1 = glow(120, "#76b87a", -220, -40);
        Circle g2 = glow(100, "#7c7ad0", 240, 50);

        HBox segments = new HBox(6);
        for (int i = 0; i < 4; i++) {
            Region seg = new Region();
            seg.getStyleClass().add(i == 0 ? "segment-active" : "segment");
            seg.setPrefHeight(3);
            HBox.setHgrow(seg, Priority.ALWAYS);
            segments.getChildren().add(seg);
        }
        segments.setMaxHeight(3);

        Label t = new Label("Your Most Listened to Song was...");
        t.getStyleClass().add("slide-title");
        t.setWrapText(true);
        t.setMaxWidth(520);
        Label stat = new Label("\"Spoken For\" - Played 33 times this month!");
        stat.getStyleClass().add("slide-stat");

        Button prev = new Button("Previous");
        prev.getStyleClass().add("button-ghost");
        Button next = new Button("Next");
        next.getStyleClass().add("button-ghost");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox nav = new HBox(prev, sp, next);

        VBox body = new VBox(18, t, stat);
        body.setAlignment(Pos.CENTER);
        VBox frame = new VBox(segments, body, nav);
        VBox.setVgrow(body, Priority.ALWAYS);
        frame.setPadding(new Insets(16));

        StackPane slide = new StackPane(g1, g2, frame);
        slide.getStyleClass().add("rewind-slide");
        slide.setPrefHeight(300);
        slide.setStyle("-fx-background-radius: 12;");
        return slide;
    }

    private Circle glow(double r, String color, double x, double y) {
        Circle c = new Circle(r, Color.web(color, 0.35));
        c.setTranslateX(x);
        c.setTranslateY(y);
        c.setEffect(new GaussianBlur(70)); // blur must be done in code
        return c;
    }

    // ---- helpers -------------------------------------------------------------
    private VBox section(String name, javafx.scene.Node node) {
        Label h = new Label(name);
        h.getStyleClass().add("heading");
        return new VBox(10, h, node);
    }

    private Label sectionLabel(String text) {
        Label l = new Label(text);
        l.getStyleClass().add("label-muted");
        return l;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
