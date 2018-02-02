package com.hannan.movieapp.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannan.movieapp.R;
import com.hannan.movieapp.api.Constants;
import com.hannan.movieapp.api.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>{

    private List<Movie> movies = new ArrayList<Movie>();
    private MovieItemClickListener movieItemClickListener;

    MovieListAdapter(List<Movie> movieList){
        this.movies = movieList;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieName.setText(movie.getTitle());
        Picasso.with(holder.itemView.getContext()).load(Constants.IMAGE_BASE_URL+movie.getImagePath()).into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void setMovieItemClickListener(MovieItemClickListener movieItemClickListener) {
        this.movieItemClickListener = movieItemClickListener;
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvMovieName)
        TextView movieName;
        @BindView(R.id.ivMoviePoster)
        ImageView moviePoster;

        MovieListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    movieItemClickListener.onClick(position);
                }
            });
        }
    }

    interface MovieItemClickListener {
        void onClick(int position);
    }
}
