/***************************************************************************************************
 CustomMovieAdapter.java
 Last updated: Steve Chou 7/19/2016

 Custom adapter for displaying movie listing inside a listview.

 **************************************************************************************************/

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


public class CustomMovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private LayoutInflater inflater;

    public static class ViewHolder
    {
        //add additional things like trailer, size, etc.
        TextView movieDesc;
        TextView movieTitle;
        ImageView moviePosterVertical;
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
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movie_layout, parent, false);
            viewHolder.movieDesc = (TextView) convertView.findViewById(R.id.movieDesc);
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
            viewHolder.moviePosterVertical = (ImageView) convertView.findViewById(R.id.moviePoster);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lookup view for data population
        //setup an imageview, then load picasso into it.
        viewHolder.movieDesc.setText(movie.description);
        viewHolder.movieTitle.setText(movie.title);
        Picasso
                .with(context)
                .load(movie.posterURL)
                .fit()
                .into(viewHolder.moviePosterVertical);

        // Return the completed view to render on screen
        return convertView;

    }

}


