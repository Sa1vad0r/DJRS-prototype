package com.djrs.model;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Song {
    // Has info on the song like title, artist, album, genre
    private String songID;
    private String title;
    private ArrayList<String> artists;
    private Album album;
    private ArrayList<String> genres;
    private int plays;
    private LocalDate dateListened;

    public Song() {
        songID = "";
        title = "";
        artists = new ArrayList<>();
        album = new Album();
        genres = new ArrayList<>();
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String songID, String title, ArrayList<String> artists, Album album, ArrayList<String> genres) {
        this.songID = songID;
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String songID, String title, ArrayList<String> artists, Album album, ArrayList<String> genres, int plays,
            LocalDate dateListened) {
        this.songID = songID;
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        this.plays = plays;
        this.dateListened = dateListened;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<String> artists) {
        this.artists = artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public LocalDate getDateListened() {
        return dateListened;
    }

    public void setDateListened(LocalDate dateListened) {
        this.dateListened = dateListened;
    }

    public String findArtist(String artistName) {
        for (String artist : artists) {
            if (artist.equals(artistName))
                return artist;
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Song[").append("songID: ").append(songID).append(", title: ").append(title)
                .append("\n").append(artists).append(", album: ").append(album).append("\n").append(genres)
                .append(", plays: ").append(plays).append(", dateListened: ").append(dateListened)
        .append("]");
        return sb.toString();
    }
}
