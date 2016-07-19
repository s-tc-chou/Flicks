package com.example.steve.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

//Async HTTP Client libraries
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.loopj.android.http.*;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> arrayOfMovies;
    CustomMovieAdapter movieAdapter;
    ListView verticalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayOfMovies = new ArrayList<>();
        movieAdapter = new CustomMovieAdapter(this, arrayOfMovies);
        verticalListView = (ListView) findViewById(R.id.movieListViewVertical);

        verticalListView.setAdapter(movieAdapter);




        String MovieListurl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        //maybe do something with the backdrop size... ?
        String backDropSizeUrl = "https://api.themoviedb.org/3/configuration?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        getMovieListing(MovieListurl);

    }

    private void  getMovieListing(String url)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        //RequestParams params = new RequestParams();

        //params.put("key", "value");
        //params.put("more", "data");

        ArrayList<Movie> newMovies;


        client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                        Log.d("success", "***********************************");
                        Log.d("success", json.toString());
                        try {

                            JSONArray result = json.getJSONArray("results");
                            //arrayOfMovies.clear();

                            //arrange based on size.  By default the vertical view will use backdrop image:
                            //do we pull backdrop size?...
                            arrayOfMovies.addAll(Movie.fromJson(result, "w300"));
                            movieAdapter.notifyDataSetChanged();

                            //debug only.
/*                            for (int i = 0; i < result.length(); i++)
                            {
                                try {
                                    String rec = result.getJSONObject(i).toString();
                                    Log.d("JSON Movies", rec);
                                }
                                catch(JSONException e)
                                {
                                    Log.e("ERROR", e.toString());
                                }
                            }*/
                        }

                        catch(JSONException e)
                        {
                            Log.e("ERROR", e.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {
                        Log.e("ERROR", e.toString());
                    }
                }
        );
    }
}
