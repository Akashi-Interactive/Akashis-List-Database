package com.akashi.animelistdatabase.data.model;

public class CardItem {
    private final String text; // This is the title of the result
    private final String imageUrl; // This is the image of the result
    private final int malId; // This is the malId of the result

    public CardItem(String text, String imageUrl, int malId) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.malId = malId;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getMalId() {
        return malId;
    }
}
