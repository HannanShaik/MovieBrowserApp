package com.hannan.movieapp.moviedetails;

import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BasePresenter;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    public void fetchAndDisplayMovieDetails(Movie movie) {
        getView().populateUI(movie);
    }
}
