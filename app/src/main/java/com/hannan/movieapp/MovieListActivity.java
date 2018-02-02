package com.hannan.movieapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseActivity;

import java.util.List;

public class MovieListActivity extends BaseActivity implements MovieListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieListPresenter movieListPresenter = new MovieListPresenter();
        movieListPresenter.attachView(this);
        movieListPresenter.fetchMovies(1);
    }

    @Override
    public void populateList(List<Movie> movies) {

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
