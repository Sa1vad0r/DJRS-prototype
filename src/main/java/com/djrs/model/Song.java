package com.djrs.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Song {
    // Has info on the song like title, artist, album, genre
    private String songID;
    private String title;
    private ArtistManager artists;
    private Album album;
    private GenreManager genres;
    private int plays;
    private LocalDate dateListened;

    public Song() {
        songID = "";
        title = "";
        artists = new ArtistManager();
        album = new Album();
        genres = new GenreManager();
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String songID, String title, ArtistManager artists, Album album, GenreManager genres) {
        this.songID = songID;
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String songID, String title, ArtistManager artists, Album album, GenreManager genres, int plays,
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

    public ArrayList<Artist> getArtists() {
        return artists.getArtists();
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists.setArtists(artists);
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public ArrayList<Genre> getGenres() {
        return genres.getGenres();
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres.setGenres(genres);
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

    public Artist findArtist(String artistName) {
        Artist artist = artists.findArtist(artistName);

        if (artist != null)
            return artist;

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
