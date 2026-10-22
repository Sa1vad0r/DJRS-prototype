package com.djrs.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnalysisReport {
    // Insights for the actual rewind presentation
    private int totalPlays;
    private ArrayList<Song> topSongs;
    private ArrayList<Artist> topArtists;
    private ArrayList<Genre> topGenres;


    public int calculateTotalPlays(ArrayList<Song> songs) {
        // TODO: Calculate total plays for the rewind
        return totalPlays;
    }

    public ArrayList<Song> getTopSongs() {
        // TODO: Return an arraylist of the top songs sorted from highest to lowest plays
        return topSongs;
    }

    public ArrayList<Artist> getTopArtists() {
        // TODO: Return an arraylist of the top artists sorted from highest to lowest plays
        return topArtists;
    }

   public ArrayList<Genre> getTopGenres() {
        // TODO: Return an arraylist of the top genres sorted from highest to lowest plays
       return topGenres;
   }
}
