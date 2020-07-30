package com.example.bottomnavmenu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

import com.example.bottomnavmenu.R;
import com.example.bottomnavmenu.SupportClasses.SuggestionStructure;


import java.util.List;

public class CardAdapterForSuggestion extends RecyclerView.Adapter<CardAdapterForSuggestion.CardViewHolder> {

    private Context context;
    private List<SuggestionStructure> list;


    public CardAdapterForSuggestion(Context context, List<SuggestionStructure> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.suggestions_layout , null , false);
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT , FrameLayout.LayoutParams.WRAP_CONTENT));
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {

        SuggestionStructure card = list.get(i);
        cardViewHolder.title.setText(card.getTitle());
        cardViewHolder.matter.setText(card.getMatter());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CardViewHolder extends  RecyclerView.ViewHolder
    {
        TextView title;
        TextView matter;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            matter = itemView.findViewById(R.id.matter);

        }

    }
}
