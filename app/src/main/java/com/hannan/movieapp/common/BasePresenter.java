package com.hannan.movieapp.common;

/**
 * Created by hannan on 3/16/17.
 */

public class BasePresenter<V extends BaseView> implements Presenter<V>{

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) {
            view = null;
        }
    }

    public V getView() {
        return view;
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    private boolean isViewAttached() {
        return view != null;
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
