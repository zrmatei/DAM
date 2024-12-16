package com.example.finala.network;

import android.util.Log;

import com.example.finala.classes.Lab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<Lab> parser(String json) {
        try {
            //am nevoie de lista de labs
            List<Lab> labs = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String labName = jsonObject.getString("labName");
                int classNumber = jsonObject.getInt("classNumber");

                labs.add(new Lab(id, labName, classNumber));
            }
            return labs;

        } catch (JSONException e) {
            Log.e("JsonParser", "Parsing failed");
        }
        return new ArrayList<>();
    }
}
