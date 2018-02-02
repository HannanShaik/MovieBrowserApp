package com.hannan.movieapp.api;

/**
 * Created by hannanshaik on 02/02/18.
 */

public interface ServiceCallback<T> {

    void onSuccess(T t);
    void onError(Exception e);
}
