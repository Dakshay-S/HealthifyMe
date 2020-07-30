package com.example.bottomnavmenu.Adapters;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
/*
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;*/

import com.example.bottomnavmenu.DatabaseHandlers.UserStatsDBHandler;
import com.example.bottomnavmenu.Model.Card;
import com.example.bottomnavmenu.R;


import java.util.List;

public class CardAdapterForAdd extends RecyclerView.Adapter<CardAdapterForAdd.CardViewHolder> {

    private Context context;
    private List<Card> list;
    private UserStatsDBHandler DbHandler;


    public CardAdapterForAdd(Context context, List<Card> list) {
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
                            addSleepPopup();
                            break;
                        case 1:
                            addWeightPopup();
                            break;
                        case 2:
                            addCaloriesPopup();
                            break;

                    }
                }
            });



        }


        private void addSleepPopup()
        {
            final TextView displayText;
            Button addButton;
            SeekBar seekBar;

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_sleep_dialog);


            displayText = dialog.findViewById(R.id.displayText);

            addButton =  dialog.findViewById(R.id.addButton);
            seekBar = dialog.findViewById(R.id.seekBar);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                float progressSave;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    progressSave = (float) progress/2;
                    displayText.setText(String.valueOf(progressSave));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    displayText.setText(String.valueOf(progressSave));
                }
            });


            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, " Sleep duration added = " + displayText.getText() +" hrs", Toast.LENGTH_SHORT).show();


                    DbHandler.addSleep(Float.parseFloat(displayText.getText().toString()));

                    dialog.dismiss();
                }
            });

            seekBar.setProgress(16);

            dialog.show();

        }


        private void addWeightPopup()
        {
            final EditText displayText;
            Button addButton;

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_weight_dialog);


            displayText = dialog.findViewById(R.id.displayText);

            addButton =  dialog.findViewById(R.id.addButton);



            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "weight added = "+ displayText.getText()+ " kg" , Toast.LENGTH_SHORT).show();

                    DbHandler.addWeight(Float.parseFloat(displayText.getText().toString()));

                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        private void addCaloriesPopup()
        {
            final EditText displayText;
            Button addButton;

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.add_calories_dialog);


            displayText = dialog.findViewById(R.id.displayText);

            addButton =  dialog.findViewById(R.id.addButton);



            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Calories added = "+ displayText.getText()+ " Cal" , Toast.LENGTH_SHORT).show();
                    DbHandler.addCalories(Float.parseFloat(displayText.getText().toString()));
                    dialog.dismiss();
                }
            });

            dialog.show();

        }


    }
}
