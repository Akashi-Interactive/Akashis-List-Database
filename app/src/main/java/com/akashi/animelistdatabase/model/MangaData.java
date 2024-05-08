package com.akashi.animelistdatabase.model;

import java.util.List;

public class MangaData {
    private MangaInfo data;

    // Getter y setter
    // ...
}

class MangaInfo {
    private int malId;
    private String url;
    private Images images;
    private boolean approved;
    private List<Title> titles;
    private String title;
    private String titleEnglish;
    private String titleJapanese;
    private String type;
    private int chapters;
    private int volumes;
    private String status;
    private boolean publishing;
    private Published published;
    private int score;
    private int scoredBy;
    private int rank;
    private int popularity;
    private int members;
    private int favorites;
    private String synopsis;
    private String background;
    private List<Author> authors;
    private List<Serialization> serializations;
    private List<Genre> genres;
    private List<Genre> explicitGenres;
    private List<Theme> themes;
    private List<Demographic> demographics;

    // Getters y setters
    // ...
}

class Published {
    private String from;
    private String to;
    private PublishedProp prop;

    // Getters y setters
    // ...
}

class PublishedProp {
    private PublishedDate from;
    private PublishedDate to;
    private String string;

    // Getters y setters
    // ...
}

class PublishedDate {
    private int day;
    private int month;
    private int year;

    // Getters y setters
    // ...
}

class Author {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Serialization {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}


