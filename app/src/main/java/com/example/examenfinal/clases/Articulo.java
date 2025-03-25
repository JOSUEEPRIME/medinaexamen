package com.example.examenfinal.clases;

import java.util.List;

public class Articulo {
    private String title;
    private String doi;
    private List<Galleys> galeys;
    private String date_published;
    private List<Autor> authors;

    // Constructor, getters, and setters
    public Articulo(String title, String doi, String date_published, List<Galleys> galeys, List<Autor> authors) {
        this.title = title;
        this.doi = doi;
        this.date_published = date_published;
        this.galeys = galeys;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getDoi() {
        return doi;
    }

    public List<Galleys> getGaleys() {
        return galeys;
    }

    public String getDatePublished() {
        return date_published;
    }

    public List<Autor> getAuthors() {
        return authors;
    }
}
