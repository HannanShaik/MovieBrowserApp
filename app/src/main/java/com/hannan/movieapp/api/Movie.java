package com.hannan.movieapp.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class Movie {

    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String imagePath;
    @SerializedName("original_language")
    private String language;
    @SerializedName("overview")
    private String overview;

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
}
