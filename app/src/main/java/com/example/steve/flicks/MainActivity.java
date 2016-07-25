/***************************************************************************************************
 MainActivity.java
 Last updated: Steve Chou 7/19/2016

 Main activity.

 Pull back list of movies using the api.

 Some questions:

 1) How to pull back all the movies (there's 35 pages), and do we need to pull back all of the results?
 2) Do I need to use GSON?  Why use it?

 To do:

 Advanced: Improve the user interface through styling and coloring (1 to 5 points depending on the difficulty of UI improvements)
 Bonus: Allow video posts to be played in full-screen using the YouTubePlayerView (2 points)
     - When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
     - Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
 Bonus: Add a play icon overlay to popular movies to indicate that the movie can be played (1 point).

 Bonus: Add a rounded corners for the images using the Picasso transformations. (1 point)

 **************************************************************************************************/

package com.example.steve.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

//Async HTTP Client libraries

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> arrayOfMovies;
    CustomMovieAdapter movieAdapter;
    @BindView(R.id.movieListViewVertical) ListView verticalListView;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout movieListRefreshSwipe;
    //@OnClick(R.id.submit)

    //API Urls and API Keys
    static final String apiKey = "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    static final String movieListUrl = "https://api.themoviedb.org/3/movie/now_playing";
    static final String configURL = "https://api.themoviedb.org/3/configuration";

    static final String backDropSizeUrl = configURL + apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding event for butterknife
        ButterKnife.bind(this);

        arrayOfMovies = new ArrayList<>();
        movieAdapter = new CustomMovieAdapter(this, arrayOfMovies);
        verticalListView.setAdapter(movieAdapter);

        //set specific orientation image sizes
        int orientation = getResources().getConfiguration().orientation;
        getImageSizes(orientation);
        initSwipeRefresh(orientation);
    }

    /*
    *  Gets the movie listing via API URL.
    *
    * */
    private void  getMovieListing(String url, Config apiConfig, final int orientation)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<Movie> newMovies;

        final String secureURL = apiConfig.secureBaseUrl;

        client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                        try {

                            JSONArray result = json.getJSONArray("results");

                            //arrange based on size.  By default the vertical view will use backdrop image:
                            arrayOfMovies.addAll(Movie.fromJson(result, secureURL, orientation));
                            movieAdapter.notifyDataSetChanged();
                            movieListRefreshSwipe.setRefreshing(false);
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

    /*
    * Gets the image sizes from the configuration API URL.  Biggest problem is determining the "correct" image size to use.
    * If we have to compare the results of what we got to a hardcoded value, how is it any different from hardcoding the value to start with?
    * */
    private void getImageSizes(final int orientation)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        //protrait mode: use poster image
        //landscape mode: use backdrop image
        //for popular movies: full backdrop image.
        final Config apiConfig = new Config();

        final String MovieListurl = movieListUrl + apiKey;

        client.get(backDropSizeUrl, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    try {
                        //set all the config objects
                        JSONObject images = json.getJSONObject("images");
                        apiConfig.setAll(images);
                        getMovieListing(MovieListurl, apiConfig, orientation);
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

    private void initSwipeRefresh(final int orientation)
    {
        movieListRefreshSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieAdapter.clear();
                movieAdapter.notifyDataSetChanged();
                getImageSizes(orientation);
            }
        });
    }

}
