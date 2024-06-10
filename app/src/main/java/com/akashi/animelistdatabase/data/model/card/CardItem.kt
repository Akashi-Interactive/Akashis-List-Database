package com.akashi.animelistdatabase.data.model.card

/**
 * Object that represents the CardItem table
 */
class CardItem(
    private var title: String?,
    private var imageUrl: String?,
    private var malId: Int
) {

    fun getTitle(): String? {
        return title
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun getMalId(): Int {
        return malId
    }
}