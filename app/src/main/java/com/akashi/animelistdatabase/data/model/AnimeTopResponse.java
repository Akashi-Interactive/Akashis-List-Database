package com.akashi.animelistdatabase.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeTopResponse {
    @SerializedName("data")
    public List<AnimeSearchResponse.Anime> data;
}
