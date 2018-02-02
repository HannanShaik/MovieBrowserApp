package com.hannan.movieapp.common;

/**
 * Created by hannan on 3/16/17.
 */

interface Presenter<V> {
    void attachView(V view);

    void detachView();
}
