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
                            final ServiceCallback<APIResponse> serviceCallback){

        API api = retrofit.create(API.class);
        Call<APIResponse> movieAPICall = api.fetchMovies(Constants.API_KEY,filterStartDate, filterEndDate, page);

        movieAPICall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if(response.isSuccessful()){
                    APIResponse apiResponse = response.body();
                    serviceCallback.onSuccess(apiResponse);
                } else {
                    //if the request failed.
                    APIResponse errorResponse = response.body();
                    if(errorResponse != null){
                        serviceCallback.onError(new Exception(errorResponse.getErrorMessage()));
                    } else {
                        serviceCallback.onError(new Exception("Something went wrong"));
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                serviceCallback.onError(new Exception(t.getMessage()));
            }

        });
    }
}
