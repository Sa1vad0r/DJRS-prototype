package com.djrs.model;

import java.util.ArrayList;

public class GenreManager {
    private ArrayList<Genre> genres;

    public GenreManager() {
        genres = new ArrayList<>();
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        if (genre != null && !genres.contains(genre))
        {
            genres.add(genre);
        }
    }

    public void removeGenre(Genre genre) {
        Genre foundGenre = findGenre(genre.getName());

        if (foundGenre != null) {
            genres.remove(foundGenre);
        }
    }

    public Genre findGenre(String name) {
        for (Genre genre : genres) {
            if (genre.getName().equals(name)) {
                return genre;
            }
        }
        return null;
    }
}
