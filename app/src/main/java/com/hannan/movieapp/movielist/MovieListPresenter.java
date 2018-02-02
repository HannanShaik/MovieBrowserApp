package com.hannan.movieapp.movielist;


import com.hannan.movieapp.api.APIResponse;
import com.hannan.movieapp.api.MovieService;
import com.hannan.movieapp.api.ServiceCallback;
import com.hannan.movieapp.common.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by hannanshaik on 02/02/18.
 */

class MovieListPresenter extends BasePresenter<MovieListView> {

    private int page = 0;
    private String filterStartDate;
    private String filterEndDate;

    void fetchListOfMovies(){
        this.page++;
        fetchMovies();
    }

    void applyDateFilter(Date startDate, Date endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        if(startDate.after(endDate)){
            getView().showError("Start date cannot be greater than End date");
        } else {
            this.filterStartDate = sdf.format(startDate);
            this.filterEndDate = sdf.format(endDate);

            //reset the page count
            this.page = 1;
            fetchMovies();
        }
    }

    private void fetchMovies() {
        getView().showProgressDialog("Loading...");
        MovieService movieService = new MovieService();
        movieService.fetchMovies(this.page, this.filterStartDate, this.filterEndDate, new ServiceCallback<APIResponse>() {
            @Override
            public void onSuccess(APIResponse apiResponse) {
                getView().dismissProgressDialog();
                getView().populateList(page, apiResponse.getTotalResults(), apiResponse.getMovies());
            }

            @Override
            public void onError(Exception e) {
                getView().dismissProgressDialog();
                getView().showError(e.getMessage());
            }
        });
    }

}
