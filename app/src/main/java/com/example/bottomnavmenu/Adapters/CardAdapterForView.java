package com.example.bottomnavmenu.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/*
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

import com.example.bottomnavmenu.DatabaseHandlers.UserStatsDBHandler;
import com.example.bottomnavmenu.MapsActivity;
import com.example.bottomnavmenu.Model.Card;
import com.example.bottomnavmenu.R;
import com.example.bottomnavmenu.ViewCalories;
import com.example.bottomnavmenu.ViewSleepDuration;
import com.example.bottomnavmenu.ViewWeight;


import java.util.List;

public class CardAdapterForView extends RecyclerView.Adapter<CardAdapterForView.CardViewHolder> {

    private Context context;
    private List<Card> list;
    private UserStatsDBHandler DbHandler;


    public CardAdapterForView(Context context, List<Card> list) {
        this.context = context;
        this.list = list;
        this.DbHandler = new UserStatsDBHandler(context);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_layout , null , false);
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT , FrameLayout.LayoutParams.WRAP_CONTENT));
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {

        Card card = list.get(i);
        cardViewHolder.textView.setText(card.getName());

        switch (card.getName())
        {
            case "Sleep Duration":
                cardViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hdsleep));
                break;

            case "Weight" :
                cardViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.weight));
                break;

            case "Calories" :
                cardViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.calories));
                break;
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CardViewHolder extends  RecyclerView.ViewHolder
    {
        TextView textView;
        ImageView imageView;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (getAdapterPosition())
                    {
                        case 0:
                            showCaloriesStats();
                            break;
                        case 1:
                            showWeightStats();
                            break;
                        case 2:
                            showSleepingStats();
                            break;

                    }
                }
            });



        }


        private void showSleepingStats()
        {
            Intent intent = new Intent(context , ViewSleepDuration.class);
            context.startActivity(intent);

        }


        private void showWeightStats()
        {
            Intent intent = new Intent(context , ViewWeight.class);
            context.startActivity(intent);

        }

        private void showCaloriesStats()
        {
            Intent intent = new Intent(context , ViewCalories.class);
            context.startActivity(intent);


        }


    }
}
