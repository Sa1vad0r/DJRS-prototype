package com.djrs.model;

public class Artist {
    private String name;
    private int plays;

    public Artist(String name) {
        this.name = name;
        plays = 0;
    }

    public Artist(String name, int plays) {
        this.name = name;
        this.plays = plays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    @Override
    public String toString() {
        return "Artist[" + "name: " + name + ", plays: " + plays + "]";
    }
}
