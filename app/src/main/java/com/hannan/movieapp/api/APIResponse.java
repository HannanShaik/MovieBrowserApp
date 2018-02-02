package com.hannan.movieapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class APIResponse {
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("results")
    private List<Movie> movies;
    @SerializedName("status_message")
    private String errorMessage;

    public int getTotalResults() {
        return totalResults;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
