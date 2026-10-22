package com.djrs.model;

public class Genre {
    private String name;
    private int plays;

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
        return "Genre[" + "name: " + name + ", plays: " + plays + "]";
    }
}
