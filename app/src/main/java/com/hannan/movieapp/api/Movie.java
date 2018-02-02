package com.hannan.movieapp.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class Movie implements Serializable{

    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String imagePath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
