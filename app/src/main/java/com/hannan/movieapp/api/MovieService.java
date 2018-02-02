package com.hannan.movieapp.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hannanshaik on 02/02/18.
 */

public class MovieService {
    private Retrofit retrofit;

    public MovieService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Fetch the list of movies from the API
     * @param page - Page Number
     * @param filterStartDate
     * @param filterEndDate
     * @param serviceCallback
     */
    public void fetchMovies(int page,
                            String filterStartDate,
                            String filterEndDate,
                            final ServiceCallback<List<Movie>> serviceCallback){

        API api = retrofit.create(API.class);
        Call<JsonObject> movieAPICall = api.fetchMovies(Constants.API_KEY,filterStartDate, filterEndDate, page);

        movieAPICall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonObject apiResponse = response.body();

                    if(apiResponse.has("results")){
                        Type movieType = new TypeToken<List<Movie>>(){}.getType();
                        List<Movie> movies = new Gson().fromJson(response.body().get("results"), movieType);
                        serviceCallback.onSuccess(movies);
                    } else {
                        //if the results block is missing.
                        serviceCallback.onError(new Exception("Something went wrong"));
                    }
                } else {
                    //if the request failed.
                    if(response.body() != null){
                        JsonObject errorResponse = response.body();
                        serviceCallback.onError(new Exception(errorResponse.get("status_message")
                                .getAsString()));
                    } else {
                        serviceCallback.onError(new Exception("Something went wrong"));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                serviceCallback.onError(new Exception(t.getMessage()));
            }
        });
    }
}
