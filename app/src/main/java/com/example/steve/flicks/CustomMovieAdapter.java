/***************************************************************************************************
 CustomMovieAdapter.java
 Last updated: Steve Chou 7/19/2016

 Custom adapter for displaying movie listing inside a listview.

 **************************************************************************************************/

package com.example.steve.flicks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomMovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private LayoutInflater inflater;

    public static class ViewHolder
    {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.movieDesc) TextView movieDesc;
        @BindView(R.id.movieTitle) TextView movieTitle;
        @BindView(R.id.moviePoster) ImageView moviePosterVertical;
        @BindView(R.id.movieListView) RelativeLayout movieListView;
        //add additional things like trailer, size, etc.
 /*       //TextView movieDesc;
        TextView movieTitle;
        ImageView moviePosterVertical;
        RelativeLayout movieListView;*/

    }

    public static class PopularViewHolder
    {
        public PopularViewHolder(View view)  {
            ButterKnife.bind(this, view);
        }
        @BindView(R.id.movieBackdrop) ImageView movieBackdrop;
        @BindView(R.id.movieListView) RelativeLayout movieListView;
    }

    public CustomMovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        final Movie movie = getItem(position);

        //if movie is popular the full backdrop image is displayed
        //w1280
        if (movie.rating > 5)
        {
            PopularViewHolder viewHolder;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null || !(convertView.getTag() instanceof PopularViewHolder)) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.movie_layout_popular, parent, false);
                viewHolder = new PopularViewHolder(convertView);

                //specify click logic to open another activity in fragment
                viewHolder.movieListView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d("test click", "i am clicked");
                        Intent intentMovieDetailActivity = new Intent(context, movieDetailActivity.class);
                        intentMovieDetailActivity.putExtra("movie_poster", movie.backdropURL);
                        intentMovieDetailActivity.putExtra("movie_title", movie.title);
                        intentMovieDetailActivity.putExtra("movie_description", movie.description);
                        intentMovieDetailActivity.putExtra("movie_release_date", movie.releaseDate);
                        intentMovieDetailActivity.putExtra("movie_rating", movie.rating);
                        intentMovieDetailActivity.putExtra("movie_trailer", movie.trailerURL);


                        context.startActivity(intentMovieDetailActivity);
                    }
                });

                convertView.setTag(viewHolder);
            }
            else { viewHolder = (PopularViewHolder) convertView.getTag(); }

            // Lookup view for data population
            //setup an imageview, then load picasso into it.
            Picasso
                    .with(context)
                    .load(movie.backdropURL)
                    .fit()
                    .placeholder(R.drawable.video_placeholder_640).error(R.drawable.notification_error)
                    .into(viewHolder.movieBackdrop);
        }
        //else display normally.
        else {

            ViewHolder viewHolder;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.movie_layout, parent, false);
                viewHolder = new ViewHolder(convertView);

                //specify click logic to open another activity
                viewHolder.movieListView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d("test click", "i am clicked");
                        Intent intentMovieDetailActivity = new Intent(context, movieDetailActivity.class);
                        intentMovieDetailActivity.putExtra("movie_poster", movie.backdropURL);
                        intentMovieDetailActivity.putExtra("movie_title", movie.title);
                        intentMovieDetailActivity.putExtra("movie_description", movie.description);
                        intentMovieDetailActivity.putExtra("movie_release_date", movie.releaseDate);
                        intentMovieDetailActivity.putExtra("movie_rating", movie.rating);
                        intentMovieDetailActivity.putExtra("movie_trailer", movie.trailerURL);


                        context.startActivity(intentMovieDetailActivity);
                    }
                });

                convertView.setTag(viewHolder);
            }
            else { viewHolder = (ViewHolder) convertView.getTag(); }

            // Lookup view for data population
            //setup an imageview, then load picasso into it.
            viewHolder.movieDesc.setText(movie.description);
            viewHolder.movieTitle.setText(movie.title);
            Picasso
                    .with(context)
                    .load(movie.posterURL)
                    .fit()
                    .placeholder(R.drawable.video_placeholder_640).error(R.drawable.notification_error)
                    .into(viewHolder.moviePosterVertical);

            // Return the completed view to render on screen
        }

        return convertView;

    }

}


