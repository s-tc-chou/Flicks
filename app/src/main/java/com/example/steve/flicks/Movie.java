/***************************************************************************************************
 Movie.java
 Last updated: Steve Chou 7/19/2016

 Helper class to set various variables pulled from the movie listing API.

 **************************************************************************************************/

package com.example.steve.flicks;

import android.content.res.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {

    public String title;
    public String description;
    public String posterURL;
    public String trailerURL;
    public String backdropURL;
    public double rating; //out of 10
    public String popularity; //out of 50?
    public String releaseDate;

    public  String urlPrefix;

    static final String BACKDROP_SIZE = "w300"; //portrait
    static final String POSTER_SIZE = "w342"; //landscape
    static final String FULL_BACKDROP_SIZE = "w1280";

    /*
    API results:
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

    //blank constructor
    public Movie ()
    {

    }


    //sample constructor.
    public Movie (String title, String description, String posterURL, String posterSize)
    {
        this.title = title;
        this.description = description;
        this.posterURL = urlPrefix + posterSize + posterURL;
    }

    //variable setter.  Orientation size is set here.
    /*TODO: need to somehow determine the proper size to use.  How can we pick out the correct "String" variable to use?
    Why even get the configuration if we'd need to hardcode this? */
    public Movie(JSONObject object, String url, int orientation){
        try {
            this.urlPrefix = url;
            this.title = object.getString("title");
            String size;

            //portrait = poster, landscape = backdrop. popular = full backdrop.
            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                size = POSTER_SIZE;
            }
            else
            {
                size = BACKDROP_SIZE;
            }

            this.posterURL = urlPrefix + size + object.getString("poster_path");
            this.backdropURL = urlPrefix + size + object.getString("backdrop_path");
            this.description = object.getString("overview");
            this.rating = object.getDouble("vote_average");
            this.releaseDate = object.getString("release_date");
            //Log.d("JSON Movies", posterURL);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    //Set variables using the whole array of movies returned.
    public static ArrayList<Movie> fromJson(JSONArray jsonObjects, String url, int orientation) {

        ArrayList<Movie> movies = new ArrayList<Movie>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                movies.add(new Movie(jsonObjects.getJSONObject(i), url, orientation));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
