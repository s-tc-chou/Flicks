package com.example.steve.flicks;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.steve.flicks.databinding.ActivityMovieDetailBinding;
import com.squareup.picasso.Picasso;

//implementing databinding for movie details.

public class movieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding movieDetailBinding;
    Movie movieDetail = new Movie();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_movie_detail);
        movieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);


        Bundle myBundle = getIntent().getExtras();

        if (myBundle != null) {initMovieObject(myBundle);}

        movieDetailBinding.movieTitle.setText(movieDetail.title);
        movieDetailBinding.movieSynopsis.setText(movieDetail.description);
        movieDetailBinding.movieReleaseDate.setText(movieDetail.releaseDate);
        Log.d("ratings", (float)movieDetail.rating + "");
        movieDetailBinding.ratingBar.setRating((float)movieDetail.rating);
        Picasso
                .with(this)
                .load(movieDetail.backdropURL)
                .fit()
                .placeholder(R.drawable.video_placeholder_640).error(R.drawable.notification_error)
                .into(movieDetailBinding.movieImage);
    }

    private void initMovieObject(Bundle myBundle)
    {
        String backdropURL = myBundle.getString("movie_poster");
        String movieTitle = myBundle.getString("movie_title");
        String movieDesc = myBundle.getString("movie_description");
        String movieReleaseDate = myBundle.getString("movie_release_date");
        String movieTrailerURL = myBundle.getString("movie_trailer");

        //set stuff if it's not null
        if (backdropURL != null) {
            movieDetail.backdropURL = backdropURL;
        }
        if (movieTitle != null) {
            movieDetail.title = movieTitle;
        }
        if (movieDesc != null) {
            movieDetail.description = movieDesc;
        }
        if (movieReleaseDate != null) {
            movieDetail.releaseDate = movieReleaseDate;
        }
        if (movieTrailerURL != null) {
            movieDetail.trailerURL = movieTrailerURL;
        }
        movieDetail.rating = myBundle.getDouble("movie_rating");
    }
}
