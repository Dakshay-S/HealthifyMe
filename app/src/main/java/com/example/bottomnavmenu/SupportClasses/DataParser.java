package com.example.bottomnavmenu.SupportClasses;

import java.util.HashMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "--NA--";
        String vicinity= "--NA--";
        String latitude= "";
        String longitude="";
        String reference="";

        Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());


        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }


            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");

            reference = googlePlaceJson.getString("reference");

            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("reference", reference);
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("lng", longitude);


        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;

    }
    private List<HashMap<String, String>>getPlaces(JSONArray jsonArray)
    {
        HashMap<String, String> placeMap = null;
        List<HashMap<String, String>> placelist = new ArrayList<>();
        int count = jsonArray.length();



        for(int i = 0; i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
