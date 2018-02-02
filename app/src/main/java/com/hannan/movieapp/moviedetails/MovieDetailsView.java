package com.hannan.movieapp.moviedetails;

import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseView;

/**
 * Created by hannanshaik on 02/02/18.
 */

public interface MovieDetailsView extends BaseView {
    void populateUI(Movie movie);
}
