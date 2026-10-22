package com.djrs.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Song {
    // Has info on the song like title, artist, album, genre
    private String title;
    private ArtistManager artists;
    private Album album;
    private GenreManager genres;
    private int plays;
    private LocalDate dateListened;

    public Song() {
        title = "";
        artists = new ArtistManager();
        album = new Album();
        genres = new GenreManager();
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String title, ArtistManager artists, Album album, GenreManager genres) {
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        plays = 0;
        dateListened = LocalDate.now();
    }

    public Song(String title, ArtistManager artists, Album album, GenreManager genres, int plays,
            LocalDate dateListened) {
        this.title = title;
        this.artists = artists;
        this.album = album;
        this.genres = genres;
        this.plays = plays;
        this.dateListened = dateListened;
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

    public void setArtists(ArtistManager artists) {
        this.artists = artists;
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

    public void setGenres(GenreManager genres) {
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

    public Artist findArtist(String artistName) {
        Artist artist = artists.findArtist(artistName);

        if (artist != null)
            return artist;

        return null;
    }
}
