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
        songs = new ArrayList<>();
    }

    public Rewind(String rewindId, String title, DateRange dateRange) {
        this.rewindId = rewindId;
        this.title = title;
        this.dateRange = dateRange;
        songs = new ArrayList<>();
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

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setRewindId(String rewindId) {
        this.rewindId = rewindId;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public boolean addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            // For now, song ID is the index of the song in the list + 1
            song.setSongID(String.format("%d",songs.size() + 1));
            songs.add(song);
            return true;
        }
        return false;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public ArrayList<Song> searchSong(String query) {
        // Find song through title first, then through artist
        ArrayList<Song> initialQuery = new ArrayList<>();

        initialQuery = searchSongByTitle(query);
        Set<Song> songsSet = new LinkedHashSet<>(initialQuery);

        songsSet.addAll(searchSongByArtist(query));
        ArrayList<Song> finalQuery = new ArrayList<>(songsSet);
        return finalQuery;
    }

    public Song findSong(Song song) {
        for (Song track : songs) {
            if (track.getSongID().equals(song.getSongID())) {
                return track;
            }
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

    public boolean updateSong(Song song) {
        Song foundSong = findSong(song);

        if (foundSong == null)
            return false;

        // Update song info
        foundSong.setTitle(song.getTitle());
        foundSong.setArtists(song.getArtists());
        foundSong.setAlbum(song.getAlbum());
        foundSong.setGenres(song.getGenres());
        foundSong.setDateListened(song.getDateListened());

        return true;
    }
}
