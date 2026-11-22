package com.djrs.controller;

import com.djrs.io.InputValidator;
import com.djrs.model.*;
import com.djrs.view.gui.Navigator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CreateEditController {

    public enum PageState {
        CREATE, EDIT
    }

    private UserSession currentSession;
    private Rewind currentRewind;
    private PageState pageState;

    @FXML private Label pageTitleLabel;
    @FXML private TextField titleField;
    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;
    @FXML private Label errorLabel;
    @FXML private Label tracksLabel;
    @FXML private TableView<Song> tracksTable;
    @FXML private TableColumn<Song, String> titleColumn;
    @FXML private TableColumn<Song, String> artistColumn;
    @FXML private TableColumn<Song, String> albumColumn;
    @FXML private TableColumn<Song, String> genreColumn;
    @FXML private TableColumn<Song, String> dateColumn;
    @FXML private TableColumn<Song, String> playsColumn;

    public CreateEditController() {
        this(PageState.CREATE);
    }

    public CreateEditController(PageState pageState) {
        this.currentSession = UserSession.getInstance();
        this.pageState = pageState;
    }

    public CreateEditController(PageState pageState, Rewind rewind) {
        this.currentSession = UserSession.getInstance();
        this.pageState = pageState;
        this.currentRewind = rewind;   // null when creating
    }

    public void initialize() {

        System.out.println("[Controller]: Loading system configuration data...");

        if (pageState == PageState.EDIT) {
            currentRewind = buildMockSpringRewind();
        } else {
            currentRewind = new Rewind();
        }

        if (tracksTable != null) {
            setupGui();
        }
    }

    private void addMockSong(Rewind rewind, String id, String title, String artist,
                             String album, String genre, int plays, LocalDate dateListened) {
        rewind.addSong(new Song(id, title,
                new ArrayList<>(List.of(artist)),
                new Album(album),
                new ArrayList<>(List.of(genre)),
                plays, dateListened));
    }

    private Rewind buildMockSpringRewind() {
        DateRange range = new DateRange(
                LocalDate.of(2026, 1, 15).atStartOfDay(),
                LocalDate.of(2026, 5, 12).atStartOfDay());

        Rewind rewind = new Rewind("1", "Spring 2026", range);

        addMockSong(rewind, "1", "In the Court of the Crimson King", "King Crimson",
                "In the Court of the Crimson King", "Progressive Rock", 87, LocalDate.of(2026, 3, 2));

        addMockSong(rewind, "2", "Do I Wanna Know?", "Arctic Monkeys",
                "AM", "Indie Rock", 64, LocalDate.of(2026, 1, 28));
        addMockSong(rewind, "3", "Bohemian Rhapsody", "Queen",
                "A Night at the Opera", "Rock", 58, LocalDate.of(2026, 2, 14));
        addMockSong(rewind, "4", "Paranoid Android", "Radiohead",
                "OK Computer", "Alternative Rock", 51, LocalDate.of(2026, 3, 19));
        addMockSong(rewind, "5", "Nights", "Frank Ocean",
                "Blonde", "R&B", 47, LocalDate.of(2026, 4, 5));
        addMockSong(rewind, "6", "Get Lucky", "Daft Punk",
                "Random Access Memories", "Electronic", 39, LocalDate.of(2026, 2, 22));
        addMockSong(rewind, "7", "Redbone", "Childish Gambino",
                "Awaken, My Love!", "Funk", 33, LocalDate.of(2026, 4, 21));
        addMockSong(rewind, "8", "Everything In Its Right Place", "Radiohead",
                "Kid A", "Experimental", 28, LocalDate.of(2026, 5, 3));
        addMockSong(rewind, "9", "Gimme Shelter", "The Rolling Stones",
                "Let It Bleed", "Rock", 21, LocalDate.of(2026, 1, 20));
        addMockSong(rewind, "10", "Alright", "Kendrick Lamar",
                "To Pimp a Butterfly", "Hip-Hop", 17, LocalDate.of(2026, 3, 30));

        return rewind;
    }

    private <T> Callback<TableColumn<T, String>, TableCell<T, String>> styledCell(String... styles) {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    getStyleClass().removeAll(styles);
                } else {
                    setText(item);

                    for (String style : styles) {
                        if (!getStyleClass().contains(style)) {
                            getStyleClass().add(style);
                        }
                    }
                }
            }
        };
    }

    private void setupGui() {
        pageTitleLabel.setText(pageState == PageState.EDIT ? "Edit Rewind" : "Create Rewind");

        if (pageState == PageState.EDIT) {
            titleField.setText(currentRewind.getTitle());
            startPicker.setValue(currentRewind.getDateRange().getStart().toLocalDate());
            endPicker.setValue(currentRewind.getDateRange().getEnd().toLocalDate());
        }

        titleColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getTitle()));
        artistColumn.setCellValueFactory(d -> new SimpleStringProperty(String.join(", ", d.getValue().getArtists())));
        albumColumn.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getAlbum() != null ? d.getValue().getAlbum().getName() : "-"));
        genreColumn.setCellValueFactory(d -> new SimpleStringProperty(String.join(", ", d.getValue().getGenres())));
        dateColumn.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getDateListened() != null ? d.getValue().getDateListened().toString() : "-"));
        playsColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPlays())));

        // Style classes
        titleColumn.setCellFactory(styledCell("app-logo"));
        dateColumn.setCellFactory(styledCell("text-muted"));
        playsColumn.setCellFactory(styledCell("text-primary"));

        tracksTable.setPlaceholder(new Label("No songs yet."));
        refreshTracks();
    }

    private void refreshTracks() {
        tracksTable.setItems(FXCollections.observableArrayList(currentRewind.getSongs()));
        tracksLabel.setText("Tracks (" + currentRewind.getSongs().size() + ")");
    }

    private ArrayList<String> splitList(String text) {
        ArrayList<String> items = new ArrayList<>();
        if (text == null) return items;
        for (String part : text.split(",")) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) items.add(trimmed);
        }
        return items;
    }

    @FXML
    private void onAddSong() {
        if (startPicker.getValue() == null || endPicker.getValue() == null) {
            errorLabel.setText("Pick the start and end dates before adding songs.");
            return;
        }

        DateRange range;
        try {
            range = new DateRange(startPicker.getValue().atStartOfDay(), endPicker.getValue().atStartOfDay());
        } catch (IllegalArgumentException e) {
            errorLabel.setText("End date must not be before the start date.");
            return;
        }
        errorLabel.setText("");
        currentRewind.setDateRange(range);

        Dialog<Song> dialog = new Dialog<>();
        dialog.setTitle("Manually Add Song");
        ButtonType addType = new ButtonType("Add Song", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addType, ButtonType.CANCEL);

        URL css = getClass().getResource("/DRJS-prototype/style/theme.css");
        if (css != null) {
            dialog.getDialogPane().getStylesheets().add(css.toExternalForm());
        }

        TextField songTitle = new TextField();
        TextField artists = new TextField();
        artists.setPromptText("Separate with commas");
        TextField album = new TextField();
        TextField genres = new TextField();
        genres.setPromptText("Separate with commas");
        DatePicker listened = new DatePicker(startPicker.getValue());
        TextField plays = new TextField("1");
        Label dialogError = new Label();
        dialogError.getStyleClass().add("text-danger");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(16));
        grid.addRow(0, new Label("Title"), songTitle);
        grid.addRow(1, new Label("Artist(s)"), artists);
        grid.addRow(2, new Label("Album"), album);
        grid.addRow(3, new Label("Genre(s)"), genres);
        grid.addRow(4, new Label("Date listened"), listened);
        grid.addRow(5, new Label("Play count"), plays);
        grid.add(dialogError, 0, 6, 2, 1);
        dialog.getDialogPane().setContent(grid);

        Button addButton = (Button) dialog.getDialogPane().lookupButton(addType);
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            String error = null;
            int playCount = -1;
            try {
                playCount = Integer.parseInt(plays.getText().trim());
            } catch (NumberFormatException ignored) {
            }

            if (songTitle.getText().trim().isEmpty()) {
                error = "Title is required.";
            } else if (splitList(artists.getText()).isEmpty()) {
                error = "At least one artist is required.";
            } else if (listened.getValue() == null) {
                error = "Pick the date listened.";
            } else if (!range.isInRange(listened.getValue().atStartOfDay())) {
                error = "Date must be within the rewind's date range.";
            } else if (playCount < 0) {
                error = "Play count must be a whole number.";
            }

            if (error != null) {
                dialogError.setText(error);
                event.consume();
            }
        });

        dialog.setResultConverter(button -> {
            if (button != addType) return null;
            return new Song("-1", songTitle.getText().trim(), splitList(artists.getText()),
                    new Album(album.getText().trim()), splitList(genres.getText()),
                    Integer.parseInt(plays.getText().trim()), listened.getValue());
        });

        dialog.showAndWait().ifPresent(song -> {
            currentRewind.addSong(song);
            refreshTracks();
        });
    }

    @FXML
    private void onCancel() {
        Navigator.show(Navigator.DASHBOARD);
    }

    @FXML
    private void onSave() {
        String title = titleField.getText() == null ? "" : titleField.getText().trim();
        if (title.isEmpty()) {
            errorLabel.setText("Please enter a rewind title.");
            return;
        }
        if (startPicker.getValue() == null || endPicker.getValue() == null) {
            errorLabel.setText("Please pick a start and end date.");
            return;
        }

        DateRange range;
        try {
            range = new DateRange(startPicker.getValue().atStartOfDay(), endPicker.getValue().atStartOfDay());
        } catch (IllegalArgumentException e) {
            errorLabel.setText("End date must not be before the start date.");
            return;
        }

        currentRewind.setTitle(title);
        currentRewind.setDateRange(range);

        if (pageState == PageState.CREATE) {
            currentRewind.setRewindId(String.valueOf(currentSession.getUserRewinds().size() + 1));
            currentSession.addRewind(currentRewind);
        }

        Navigator.show(Navigator.DASHBOARD);
    }

    public void showBasicRewindInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL dd yyyy");
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("REWIND TITLE: " + currentRewind.getTitle());
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
        System.out.println("Start Date: " + currentRewind.getDateRange().getStart().format(formatter));
        System.out.println("End Date: " + currentRewind.getDateRange().getEnd().format(formatter));
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
    }

    public void showTracks(ArrayList<Song> tracks) {
        if (tracks == null || tracks.isEmpty()) {
            tracks = currentRewind.getSongs();

            if (tracks == null || tracks.isEmpty()) {
                System.out.println("No songs found. Add songs first.");
                return;
            }
        }

        System.out.println("Tracks (" + tracks.size() + ")");
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
        System.out.printf("| %-10s", "#");
        System.out.printf("| %-30s", "Title");
        System.out.printf("| %-25s", "Artist");
        System.out.printf("| %-30s", "Album");
        System.out.printf("| %-20s", "Genre");
        System.out.printf("| %-15s", "Date Listened");
        System.out.printf("| %-25s\n", "Play Count");
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");

        int i = 1;
        for (Song song : tracks) {
            System.out.printf("| %-10s", i);
            printRecordLine(song);
            System.out.println();
            i++;
        }

        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
    }

    private void printRecordLine(Song song) {
        String title = song.getTitle();
        String artist = (!song.getArtists().isEmpty()) ? song.getArtists().get(0) : " - ";
        String album  = song.getAlbum() != null ? song.getAlbum().getName() : " - ";
        String genre  = (!song.getGenres().isEmpty()) ? song.getGenres().get(0)
                : " - ";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateListened = (song.getDateListened() != null) ? song.getDateListened().format(formatter) : " - ";

        System.out.printf("| %-30s", title);
        System.out.printf("| %-25s", artist);
        System.out.printf("| %-30s", album);
        System.out.printf("| %-20s", genre);
        System.out.printf("| %-15s", dateListened);
        System.out.printf("| %-25s", song.getPlays());
    }
    public PageState getPageState() {
        return pageState;
    }

    public void setPageState(PageState state) {
        pageState = state;
    }

    public void readBasicRewindInfo() {
        System.out.println("Please enter the rewind name: ");
        String rewindTitle = InputValidator.readString();

        DateRange dateRange = null;
        do {
            try {
                System.out.println("Please enter the start date (YYYY-MM-DD): ");
                String startDate = InputValidator.readString();
                System.out.println("Please enter the end date (YYYY-MM-DD): ");
                String endDate = InputValidator.readString();
                dateRange = new DateRange(LocalDate.parse(startDate).atStartOfDay(),
                        LocalDate.parse(endDate).atStartOfDay());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format: " + e.getMessage() + ". Please enter the date again.");
            }
        } while (dateRange == null);

        if (pageState == PageState.CREATE) {
            currentRewind = new Rewind("0", rewindTitle, dateRange);
            return;
        } else {
            currentRewind.setTitle(rewindTitle);
            currentRewind.setDateRange(dateRange);
        }

        System.out.println("Succefully updated rewind info.");
    }

    public int readPageOptions() {
        int choice;
        choice = InputValidator.readIntNumberBetween(1, 5, "Invalid input. Please enter a number between 1 and 5.");

        return choice;
    }

    public Song searchSong() {
        if (currentRewind.getSongs() == null || currentRewind.getSongs().isEmpty()) {
            System.out.println("No songs found in the current rewind. Add songs first.");
            return null;
        }

        ArrayList<Song> foundSongs;
        do {
            System.out.println("Please enter song's title or artist: ");
            String query = InputValidator.readString();
            foundSongs = currentRewind.searchSong(query);

            if (foundSongs == null || foundSongs.isEmpty()) {
                System.out.println("No songs found. Please try again.");
                showTracks(null);
            }
        } while (foundSongs == null || foundSongs.isEmpty());

        int songCount = foundSongs.size();
        if (songCount > 1) {
            int choice;
            String errorString = String.format("Invalid input. Please enter a number between %d to %d.", 1, songCount);

            showTracks(foundSongs);
            System.out.println("Enter the number of the song (inside []): ");
            choice = InputValidator.readIntNumberBetween(1, songCount, errorString);

            return foundSongs.get(choice - 1);
        } else {
            return foundSongs.get(0);
        }
    }

    public void editSongInfo() {
        Song foundSong = searchSong();

        if (foundSong == null || currentRewind.getSongs().isEmpty()) {
            System.out.println("No songs found. Add songs first.");
            return;
        }

        System.out.println("Song found. Editing song info...");
        addEditSongInfo(foundSong);
    }

    public void addSongInfo() {
        System.out.println("Adding new song...");
        addEditSongInfo(null);
    }

    public void addEditSongInfo(Song song) {
       Song updatedSong = readSongInfo(song);

        if (song == null) {
            currentRewind.addSong(updatedSong);
        } else {
            if (currentRewind.updateSong(updatedSong)) {
                System.out.println("Song updated successfully.");
            } else {
                System.out.println("Failed to update song.");
            }
        }
    }

    private <T> ArrayList<T> readItems(Function<String, T> itemReader, String endMarker) {
        ArrayList<T> items = new ArrayList<>();

        String input = InputValidator.readString();
        while (!input.equals(endMarker)) {
            items.add(itemReader.apply(input));
            input = InputValidator.readString();
        }

        return items;
    }

    private ArrayList<String> readStringList() {
        return readItems(String::new, "-1");
    }

    private String readSongDateListened() {
        String dateListened;
        LocalDateTime date;

        do {
            System.out.println("Enter the new date listened (YYYY-MM-DD): ");
            dateListened = InputValidator.readString();
            date = LocalDate.parse(dateListened).atStartOfDay();

            if (!currentRewind.getDateRange().isInRange(date)) {
                System.out.println("Invalid date. Please enter a date within the rewind's date range.");
            }
        } while (!currentRewind.getDateRange().isInRange(date));

        return dateListened;
    }

    private Song readSongInfo(Song song) {
        String title;
        String album;
        int totalPlays;

        System.out.println("Enter the new song title: ");
        title = InputValidator.readString();

        System.out.println("Enter the new song artist(s) (type -1 to finish): ");
        ArrayList<String> artists = readStringList();

        System.out.println("Enter the new song album: ");
        album = InputValidator.readString();

        System.out.println("Enter the new song genre(s) (type -1 to finish): ");
        ArrayList<String> genres = readStringList();

        System.out.println("Enter the new total plays: ");
        totalPlays = InputValidator.readIntNumber("Invalid input. Please enter a number.");

        String dateListened = readSongDateListened();


        return new Song((song != null) ? song.getSongID() : "-1", title,
                new ArrayList<>(artists), new Album(album),
                new ArrayList<>(genres), totalPlays,
                LocalDate.parse(dateListened));
    }
}