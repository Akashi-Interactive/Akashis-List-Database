package com.akashi.animelistdatabase.model;

import java.util.List;

public class AnimeData {
    private int malId;
    private String url;
    private Images images;
    private Trailer trailer;
    private boolean approved;
    private List<Title> titles;
    private String title;
    private String titleEnglish;
    private String titleJapanese;
    private List<String> titleSynonyms;
    private String type;
    private String source;
    private int episodes;
    private String status;
    private boolean airing;
    private Aired aired;
    private String duration;
    private String rating;
    private int score;
    private int scoredBy;
    private int rank;
    private int popularity;
    private int members;
    private int favorites;
    private String synopsis;
    private String background;
    private String season;
    private int year;
    private Broadcast broadcast;
    private List<Producer> producers;
    private List<Licensor> licensors;
    private List<Studio> studios;
    private List<Genre> genres;
    private List<Genre> explicitGenres;
    private List<Theme> themes;
    private List<Demographic> demographics;

    // Getters y setters (puedes generarlos automáticamente en Android Studio)
    // ...
}

class Images {
    private Image jpg;
    private Image webp;

    // Getters y setters
    // ...
}

class Image {
    private String imageUrl;
    private String smallImageUrl;
    private String largeImageUrl;

    // Getters y setters
    // ...
}

class Trailer {
    private String youtubeId;
    private String url;
    private String embedUrl;

    // Getters y setters
    // ...
}

class Title {
    private String type;
    private String title;

    // Getters y setters
    // ...
}

class Aired {
    private String from;
    private String to;
    private AiredProp prop;

    // Getters y setters
    // ...
}

class AiredProp {
    private AiredDate from;
    private AiredDate to;
    private String string;

    // Getters y setters
    // ...
}

class AiredDate {
    private int day;
    private int month;
    private int year;

    // Getters y setters
    // ...
}

class Broadcast {
    private String day;
    private String time;
    private String timezone;
    private String string;

    // Getters y setters
    // ...
}

class Producer {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Licensor {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Studio {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Genre {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Theme {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}

class Demographic {
    private int malId;
    private String type;
    private String name;
    private String url;

    // Getters y setters
    // ...
}
