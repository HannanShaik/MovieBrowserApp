package com.hannan.movieapp;


import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.api.MovieService;
import com.hannan.movieapp.api.ServiceCallback;
import com.hannan.movieapp.common.BasePresenter;

import java.util.List;


/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieListPresenter extends BasePresenter<MovieListView> {

    private int page = 1;
    private String filterStartDate;
    private String filterEndDate;

    public void fetchMovies(int page){
        this.page = page;
        fetchMovies();
    }

    public void applyDateFilter(String startDate, String endDate){
        this.filterStartDate = startDate;
        this.filterEndDate = endDate;
        fetchMovies();
    }

    private void fetchMovies() {
        getView().showProgressDialog("Loading...");
        MovieService movieService = new MovieService();
        movieService.fetchMovies(this.page, this.filterStartDate, this.filterEndDate, new ServiceCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                getView().dismissProgressDialog();
                getView().populateList(movies);
            }

            @Override
            public void onError(Exception e) {
                getView().dismissProgressDialog();
                getView().showError(e.getMessage());
            }
        });
    }

}
