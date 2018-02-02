package com.hannan.movieapp.movielist;


import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.api.MovieService;
import com.hannan.movieapp.api.ServiceCallback;
import com.hannan.movieapp.common.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieListPresenter extends BasePresenter<MovieListView> {

    private int page = 1;
    private String filterStartDate;
    private String filterEndDate;

    public void fetchListOfMovies(){
        this.page++;
        fetchMovies();
    }

    public void applyDateFilter(Date startDate, Date endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(startDate.after(endDate)){
            getView().showError("Start date cannot be greater than End date");
        } else {
            this.filterStartDate = sdf.format(startDate);
            this.filterEndDate = sdf.format(endDate);
            this.page = 1;
            fetchMovies();
        }
    }

    private void fetchMovies() {
        getView().showProgressDialog("Loading...");
        MovieService movieService = new MovieService();
        movieService.fetchMovies(this.page, this.filterStartDate, this.filterEndDate, new ServiceCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                getView().dismissProgressDialog();
                getView().populateList(page, movies);
            }

            @Override
            public void onError(Exception e) {
                getView().dismissProgressDialog();
                getView().showError(e.getMessage());
            }
        });
    }

}
