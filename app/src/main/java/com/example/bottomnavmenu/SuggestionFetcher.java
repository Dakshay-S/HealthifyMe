package com.example.bottomnavmenu;

import android.content.Context;
import android.widget.Toast;

import com.example.bottomnavmenu.SupportClasses.SuggestionStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SuggestionFetcher {


    public static List<SuggestionStructure> fetch(Context context , InputStream input) throws IOException {
        List<SuggestionStructure> suggestions = new ArrayList<>();
        BufferedReader reader;

        //todo
        System.err.println("Inside fetch");

        reader = new BufferedReader(new InputStreamReader(input));
        String line;
        line = reader.readLine();



        while(line != null) // line will be "Title"
        {


            SuggestionStructure suggestion = new SuggestionStructure();

            suggestion.setTitle(reader.readLine());

            //todo
            System.out.println(reader.readLine());// skip "Matter"


            StringBuilder matter = new StringBuilder();


            while((line = reader.readLine()) != null && line.length() != 0) {
                matter.append("\n").append(line);
            }


            suggestion.setMatter(matter.toString());

            suggestions.add(suggestion);


            //todo : if end of file
            try
            {
                line = reader.readLine(); // "Title"
            }catch (IOException e)
            {
                return suggestions;
            }


        }


        return suggestions;

    }
}
