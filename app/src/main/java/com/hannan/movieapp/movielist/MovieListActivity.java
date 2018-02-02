package com.hannan.movieapp.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hannan.movieapp.R;
import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseActivity;
import com.hannan.movieapp.moviedetails.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends BaseActivity implements MovieListView {

    @BindView(R.id.rvMovies)
    RecyclerView movieRecyclerView;

    private List<Movie> movies;
    private MovieListAdapter adapter;
    private int totalItemCount;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    public boolean isLoading;
    private int visibleThreshold = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        adapter = new MovieListAdapter(this, movies);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        movieRecyclerView.setAdapter(adapter);

        final MovieListPresenter movieListPresenter = new MovieListPresenter();
        movieListPresenter.attachView(this);


        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                System.out.println(totalItemCount);
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    movieListPresenter.fetchListOfMovies();
                    isLoading = true;
                }
            }
        });

        adapter.setMovieItemClickListener(new MovieListAdapter.MovieItemClickListener() {
            @Override
            public void onClick(int position) {
                //move to new screen
                Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie", movies.get(position));
                startActivity(intent);
            }
        });

        movieListPresenter.fetchListOfMovies();
    }

    @Override
    public void populateList(List<Movie> movies) {
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog(String message) {
        super.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }
}
