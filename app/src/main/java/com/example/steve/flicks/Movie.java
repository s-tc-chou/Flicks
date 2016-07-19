package com.example.steve.flicks;

import android.content.Context;
import android.util.Log;
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
    public String backdropSize;
    public String trailer;
    public String backdropURL;
    public String rating;

    private final String urlPrefix =  "https://image.tmdb.org/t/p/";

    //images have https://image.tmdb.org/t/p/ prefixed.
    //need the image size too.  ex: w300

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

    public Movie (String title, String description, String posterURL, String backdropSize)
    {
        this.title = title;
        this.description = description;
        this.backdropSize = backdropSize;
        this.posterURL = urlPrefix + backdropSize + posterURL; //used only for portrait

    }

    //use backdrop size from initialization maybe... but no results from API call here. So where do we get the size from?
    public Movie(JSONObject object, String size){
        try {
            this.title = object.getString("title");
            this.posterURL = urlPrefix + size + object.getString("poster_path");
            this.description = object.getString("overview");
            Log.d("JSON Movies", posterURL);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> fromJson(JSONArray jsonObjects, String size) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                movies.add(new Movie(jsonObjects.getJSONObject(i),size));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }



}
