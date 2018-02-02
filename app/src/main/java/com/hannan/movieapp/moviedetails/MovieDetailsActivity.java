package com.hannan.movieapp.moviedetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannan.movieapp.R;
import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsView {

    @BindView(R.id.ivMovieBackdrop)
    ImageView movieBackdrop;
    @BindView(R.id.tvMovieOverview)
    TextView movieOverview;
    @BindView(R.id.tvReleaseDate)
    TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MovieDetailsPresenter movieDetailsPresenter = new MovieDetailsPresenter();
        movieDetailsPresenter.attachView(this);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        movieDetailsPresenter.fetchAndDisplayMovieDetails(movie);
    }


    @Override
    public void showProgressDialog(String message) {
        super.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void populateUI(Movie movie) {
        getSupportActionBar().setTitle(movie.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/"+movie.getBackdropPath()).into(movieBackdrop);
        movieOverview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate());
    }
}
