package com.example.steve.flicks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    public String title;
    public String description;
    public String posterURL;
    public String trailer;
    public String backdropURL;
    public String rating;

    //images have https://image.tmdb.org/t/p/ prefixed.

    /*
    "poster_path": "/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg",
    "adult": false,
    "overview": "Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.",
    "release_date": "2016-06-29",
    "genre_ids": [
        28,
        12
    ],
    "id": 258489,
    "original_title": "The Legend of Tarzan",
    "original_language": "en",
    "title": "The Legend of Tarzan",
    "backdrop_path": "/75GFqrnHMKqkcNZ2wWefWXfqtMV.jpg",
    "popularity": 35.038312,
    "vote_count": 356,
    "video": false,
    "vote_average": 4.48
     */

    public Movie (String title, String description, String posterURL)
    {
        this.title = title;
        this.description = description;
        this.posterURL = posterURL; //used only for landscape

    }

    public Movie(JSONObject object){
        try {
            this.title = object.getString("title");
            this.posterURL = object.getString("poster_path");
            this.description = object.getString("overview");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> fromJson(JSONArray jsonObjects) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                movies.add(new Movie(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }



}
