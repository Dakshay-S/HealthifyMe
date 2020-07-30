package com.example.bottomnavmenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;*/

import com.example.bottomnavmenu.Adapters.CardAdapterForView;
import com.example.bottomnavmenu.Model.Card;

import java.util.ArrayList;
import java.util.List;

public class ViewFragment extends Fragment {

    RecyclerView recyclerView;
    List<Card> cardList;

    public ViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_view , container , false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cardList = new ArrayList<>();
        cardList.add(new Card("Calories"));
        cardList.add(new Card("Weight"));
        cardList.add(new Card("Sleep Duration"));


        recyclerView.setAdapter(new CardAdapterForView(getContext() , cardList));

        return view;
    }
}
