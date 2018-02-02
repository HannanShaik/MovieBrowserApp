package com.hannan.movieapp;


import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseView;

import java.util.List;

/**
 * Created by hannanshaik on 02/02/18.
 */

public interface MovieListView extends BaseView {
    void populateList(List<Movie> movies);
    void showError(String message);
    void showProgressDialog(String message);
    void dismissProgressDialog();
}
