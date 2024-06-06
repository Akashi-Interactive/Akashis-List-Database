package com.akashi.animelistdatabase.ui.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.akashi.animelistdatabase.R;
import com.akashi.animelistdatabase.data.model.CardItem;
import com.akashi.animelistdatabase.ui.anime.AnimeActivity;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final List<CardItem> cardItems;
    private final Context context;

    public CardAdapter(List<CardItem> cardItems, Context context) {
        this.cardItems = cardItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem cardItem = cardItems.get(position);
        holder.cardText.setText(cardItem.getText());
        Picasso.get().load(cardItem.getImageUrl()).into(holder.cardImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimeActivity.class);
                intent.putExtra("malId", cardItem.getMalId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImage;
        public TextView cardText;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
            cardText = itemView.findViewById(R.id.card_title);
        }
    }

}
