package com.djrs.controller;

import com.djrs.io.InputValidator;
import com.djrs.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Function;

public class CreateEditController {

    public enum PageState {
        CREATE, EDIT
    }

    private UserSession currentSession;
    private Rewind currentRewind;
    private PageState pageState;

    public CreateEditController(PageState pageState) {
        this.currentSession = UserSession.getInstance();
        this.pageState = pageState;
    }

    public void initialize() {
        // --- Setup tasks go here ---

        // Load system configuration data:
        System.out.println("[Controller]: Loading system configuration data...");

        // Load user data:
        if (pageState == PageState.EDIT) {
            // currentSession.load;
        } else {
            currentRewind = new Rewind();
        }
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
        // If tracks is empty/null, default to all songs in the current rewind
        if (tracks == null || tracks.isEmpty()) {
            tracks = currentRewind.getSongs();

            // If the current rewind does not have any songs, notify user and return
            if (tracks == null || tracks.isEmpty()) {
                System.out.println("No songs found. Add songs first.");
                return;
            }
        }

        System.out.println("Tracks (" + tracks.size() + ")");
        // -----------------------------------------------------------------------
        // 1️⃣ Print a header that explains what we are going to display
        // -----------------------------------------------------------------------
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
        System.out.printf("| %-10s", "#");
        System.out.printf("| %-30s", "Title");
        System.out.printf("| %-25s", "Artist");
        System.out.printf("| %-30s", "Album");
        System.out.printf("| %-20s", "Genre");
        System.out.printf("| %-15s", "Date Listened");
        System.out.printf("| %-25s\n", "Play Count");
        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");

        // -----------------------------------------------------------------------
        // 2️⃣ Print each song on its own row
        // -----------------------------------------------------------------------
        int i = 1;
        for (Song song : tracks) {
            // Using printf lets us keep the columns nicely aligned
            System.out.printf("| %-10s", i);
            printRecordLine(song);
            System.out.println();
            i++;
        }

        System.out.printf("%s\n", "____________________________________________________________________________________________________________________________________________________________________________________");
    }

    private void printRecordLine(Song song) {
        // Format data
        String title = song.getTitle();
        String artist = (!song.getArtists().isEmpty()) ? song.getArtists().get(0).getName() : " - ";
        String album  = song.getAlbum() != null ? song.getAlbum().getName() : " - ";
        String genre  = (!song.getGenres().isEmpty()) ? song.getGenres().get(0).getName()
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

            // Show results
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

        if (song == null) { // Song is null = adding new song
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

    private ArrayList<Artist> readSongArtists() {
        return readItems(Artist::new, "-1");
    }

    private ArrayList<Genre> readSongGenres() {
        return readItems(Genre::new, "-1");
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
        // Song info
        String title;
        String album;
        int totalPlays;

        System.out.println("Enter the new song title: ");
        title = InputValidator.readString();

        System.out.println("Enter the new song artist(s) (type -1 to finish): ");
        ArrayList<Artist> artists = readSongArtists();

        System.out.println("Enter the new song album: ");
        album = InputValidator.readString();

        System.out.println("Enter the new song genre(s) (type -1 to finish): ");
        ArrayList<Genre> genres = readSongGenres();

        System.out.println("Enter the new total plays: ");
        totalPlays = InputValidator.readIntNumber("Invalid input. Please enter a number.");

        String dateListened = readSongDateListened();


        // Return song object
        return new Song((song != null) ? song.getSongID() : "-1", title,
                new ArtistManager(artists), new Album(album),
                new GenreManager(genres), totalPlays,
                LocalDate.parse(dateListened));
    }
}