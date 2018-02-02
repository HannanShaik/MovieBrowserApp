package com.hannan.movieapp.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class Movie implements Serializable{

    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String imagePath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("original_language")
    private String language;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public int getVoteCount() {
        return voteCount;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getLanguage() {
        return language;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "voteCount=" + voteCount +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", language='" + language + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
