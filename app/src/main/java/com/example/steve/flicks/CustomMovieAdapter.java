package com.example.steve.flicks;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Steve on 7/14/2016.
 */
public class CustomMovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private LayoutInflater inflater;

    public static class ViewHolder
    {
        TextView movieDesc;
        TextView movieTitle;
        ImageView moviePoster;

    }

    public CustomMovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_layout, parent, false);
            convertView = inflater.inflate(R.layout.movie_layout, parent, false);
        }

        // Lookup view for data population

        //String imageUrl = "https://i.imgur.com/tGbaZCY.jpg";
        //setup an imageview, then load picasso into it.
        TextView movieDesc = (TextView) convertView.findViewById(R.id.movieDesc);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
        ImageView moviePoster = (ImageView) convertView.findViewById(R.id.moviePoster);

        // Populate the data into the template view using the data object
        movieDesc.setText(movie.description);
        movieTitle.setText(movie.title);
        Picasso
                .with(context)
                .load(movie.posterURL)
                .fit()
                .into(moviePoster);
        //moviePoster.setText(user.hometown);



        // Return the completed view to render on screen
        return convertView;

    }

}


