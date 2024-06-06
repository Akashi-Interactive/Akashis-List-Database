package com.akashi.animelistdatabase.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AnimeSearchResponse {
    @SerializedName("pagination")
    public Pagination pagination;

    @SerializedName("data")
    public List<Anime> data;

    public static class Pagination {
        @SerializedName("last_visible_page")
        public int lastVisiblePage;

        @SerializedName("has_next_page")
        public boolean hasNextPage;

        @SerializedName("current_page")
        public int currentPage;

        @SerializedName("items")
        public Items items;
    }

    public static class Items {
        @SerializedName("count")
        public int count;

        @SerializedName("total")
        public int total;

        @SerializedName("per_page")
        public int perPage;
    }

    public static class Data {
        private String title;
        private Images images;

        public String getTitle() {
            return title;
        }

        public Images getImages() {
            return images;
        }
    }

    public static class Anime {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("url")
        public String url;

        @SerializedName("images")
        public Images images;

        @SerializedName("titles")
        public List<Title> titles;

        @SerializedName("title")
        public String title;

        @SerializedName("title_english")
        public String titleEnglish;

        @SerializedName("title_japanese")
        public String titleJapanese;

        @SerializedName("title_synonyms")
        public List<String> titleSynonyms;

        @SerializedName("type")
        public String type;

        @SerializedName("source")
        public String source;

        @SerializedName("episodes")
        public int episodes;

        @SerializedName("status")
        public String status;

        @SerializedName("airing")
        public boolean airing;

        @SerializedName("aired")
        public Aired aired;

        @SerializedName("duration")
        public String duration;

        @SerializedName("rating")
        public String rating;

        @SerializedName("score")
        public double score;

        @SerializedName("scored_by")
        public int scoredBy;

        @SerializedName("rank")
        public int rank;

        @SerializedName("popularity")
        public int popularity;

        @SerializedName("members")
        public int members;

        @SerializedName("favorites")
        public int favorites;

        @SerializedName("synopsis")
        public String synopsis;

        @SerializedName("background")
        public String background;

        @SerializedName("season")
        public String season;

        @SerializedName("year")
        public int year;

        @SerializedName("broadcast")
        public Broadcast broadcast;

        @SerializedName("producers")
        public List<Producer> producers;

        @SerializedName("licensors")
        public List<Licensor> licensors;

        @SerializedName("studios")
        public List<Studio> studios;

        @SerializedName("genres")
        public List<Genre> genres;

        @SerializedName("explicit_genres")
        public List<String> explicitGenres;

        @SerializedName("themes")
        public List<Theme> themes;

        @SerializedName("demographics")
        public transient List<String> demographics;
    }
    public static class Images {
        @SerializedName("jpg")
        public Image jpg;

        @SerializedName("webp")
        public Image webp;
    }

    public static class Image {
        @SerializedName("image_url")
        public String imageUrl;

        @SerializedName("small_image_url")
        public String smallImageUrl;

        @SerializedName("large_image_url")
        public String largeImageUrl;
    }

    public static class Trailer {
        @SerializedName("youtube_id")
        public String youtubeId;

        @SerializedName("url")
        public String url;

        @SerializedName("embed_url")
        public String embedUrl;

        @SerializedName("images")
        public TrailerImages images;
    }

    public static class TrailerImages {
        @SerializedName("image_url")
        public String imageUrl;

        @SerializedName("small_image_url")
        public String smallImageUrl;

        @SerializedName("medium_image_url")
        public String mediumImageUrl;

        @SerializedName("large_image_url")
        public String largeImageUrl;

        @SerializedName("maximum_image_url")
        public String maximumImageUrl;
    }

    public static class Title {
        @SerializedName("type")
        public String type;

        @SerializedName("title")
        public String title;
    }

    public static class Aired {
        @SerializedName("from")
        public String from;

        @SerializedName("to")
        public String to;

        @SerializedName("prop")
        public AiredProp prop;

        @SerializedName("string")
        public String string;
    }

    public static class AiredProp {
        @SerializedName("from")
        public Date from;

        @SerializedName("to")
        public Date to;
    }

    public static class Date {
        @SerializedName("day")
        public int day;

        @SerializedName("month")
        public int month;

        @SerializedName("year")
        public int year;
    }

    public static class Broadcast {
        @SerializedName("day")
        public String day;

        @SerializedName("time")
        public String time;

        @SerializedName("timezone")
        public String timezone;

        @SerializedName("string")
        public String string;
    }

    public static class Producer {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }

    public static class Licensor {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }

    public static class Studio {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }

    public static class Genre {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }

    public static class Theme {
        @SerializedName("mal_id")
        public int malId;

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        @SerializedName("url")
        public String url;
    }
}
