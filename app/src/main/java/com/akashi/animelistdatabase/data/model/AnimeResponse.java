package com.akashi.animelistdatabase.data.model;

import com.google.gson.annotations.SerializedName;

public class AnimeResponse {
    @SerializedName("data")
    public AnimeSearchResponse.Anime data;
}
