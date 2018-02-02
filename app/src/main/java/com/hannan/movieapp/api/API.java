package com.hannan.movieapp.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hannanshaik on 02/02/18.
 */

public interface API {

    @GET("discover/movie")
    Call<APIResponse> fetchMovies(@Query("api_key") String apiKey,
                                 @Query("primary_release_date.gte") String filterStartDate,
                                 @Query("primary_release_date.lte") String filterEndDate,
                                 @Query("page") int pageNumber);
}
