package com.djrs.model;

import java.util.ArrayList;

public class Album {
    private String name;
    private int tracks;
    private int plays;

    public Album() {
        name = "";
        tracks = 0;
        plays = 0;
    }

    public Album(String name) {
        this.name = name;
        tracks = 0;
        plays = 0;
    }

    public Album(String name, int tracks) {
        this.name = name;
        this.tracks = tracks;
        plays = 0;
    }

    public Album(String name, int tracks, int plays) {
        this.name = name;
        this.tracks = tracks;
        this.plays = plays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    @Override
    public String toString() {
        return "Album[" + "name: " + name + ", tracks: " + tracks + ", plays: " + plays + "]";
    }
}
