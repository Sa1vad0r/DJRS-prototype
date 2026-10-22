package com.djrs.model;

import java.util.ArrayList;

public class ArtistManager {
    private ArrayList<Artist> artists;

    public ArtistManager() {
        artists = new ArrayList<>();
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public void addArtist(Artist artist) {
        if (artist != null && !artists.contains(artist))
        {
            artists.add(artist);
        }
    }

    public void removeArtist(Artist artist) {
        Artist foundArtist = findArtist(artist.getName());

        if (foundArtist != null) {
            artists.remove(foundArtist);
        }
    }

    public Artist findArtist(String name) {
        for (Artist artist : artists) {
            if (artist.getName().equals(name)) {
                return artist;
            }
        }
        return null;
    }
}
