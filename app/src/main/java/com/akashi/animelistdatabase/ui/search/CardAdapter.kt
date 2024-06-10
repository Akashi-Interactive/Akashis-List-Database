package com.akashi.animelistdatabase.ui.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashi.animelistdatabase.R
import com.akashi.animelistdatabase.data.model.card.CardItem
import com.akashi.animelistdatabase.ui.anime.AnimeActivity
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class CardAdapter(private val cardItems: List<CardItem>, private val context: Context) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

        /**
         * Function that creates the view
         */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    /**
     * Function that binds the data to the view
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardItem: CardItem = cardItems[position]
        holder.cardText.text = cardItem.getTitle()

        Picasso.get().load(cardItem.getImageUrl()).into(holder.cardImage)

        Log.d("CardAdapter", "onBindViewHolder: ${cardItem.getImageUrl()}")

        holder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, AnimeActivity::class.java)
            intent.putExtra("malId", cardItem.getMalId())
            v.context.startActivity(intent)
        }
    }

    /**
     * Function that returns the number of items in the list
     */
    override fun getItemCount(): Int {
        return cardItems.size
    }

    /**
     * Inner class that represents the CardViewHolder
     */
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardImage: ImageView = itemView.findViewById(R.id.card_image)
        var cardText: TextView = itemView.findViewById(R.id.card_title)
    }
}