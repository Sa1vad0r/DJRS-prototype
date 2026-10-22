package com.djrs.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Rewind {
    // Holds the rewind data, including the list of songs and metadata.
    private String rewindId;
    private String title; // Title for the rewind
    private DateRange dateRange;

    private ArrayList<Song> songs; // List of songs included in the rewind

    public Rewind() {
        rewindId = "";
        title = "";
        dateRange = new DateRange(LocalDateTime.now(), LocalDateTime.now());
    }

    public Rewind(String rewindId, String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.rewindId = rewindId;
        this.title = title;
        dateRange = new DateRange(startDate, endDate);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String getRewindId() {
        return rewindId;
    }

    public ArrayList<Song> searchSong(String query) {
        // Find song through title first, then thrSough artist
        ArrayList<Song> initialQuery = new ArrayList<>();

        initialQuery = searchSongByTitle(query);
        Set<Song> setSongs = new LinkedHashSet<>(initialQuery);

        if (setSongs.addAll(searchSongByArtist(query))) {
            ArrayList<Song> finalQuery = new ArrayList<>(setSongs);
            return finalQuery;
        }

        return null;
    }

    public ArrayList<Song> searchSongByTitle(String title) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().equals(title)) {
                foundSongs.add(song);
            }
        }
        return foundSongs;
    }

    public ArrayList<Song> searchSongByArtist(String artist) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.findArtist(artist) != null) {
                foundSongs.add(song);
            }
        }
        return foundSongs;
    }
}
