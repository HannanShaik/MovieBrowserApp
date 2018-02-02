package com.hannan.movieapp.movielist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.hannan.movieapp.R;
import com.hannan.movieapp.api.Movie;
import com.hannan.movieapp.common.BaseActivity;
import com.hannan.movieapp.moviedetails.MovieDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListActivity extends BaseActivity implements MovieListView {

    @BindView(R.id.rvMovies)
    RecyclerView movieRecyclerView;
    @BindView(R.id.btnFilterStartDate)
    Button startDateButton;
    @BindView(R.id.btnFilterEndDate)
    Button endDateButton;

    private MovieListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private MovieListPresenter movieListPresenter;
    private List<Movie> movies;
    private Date filterStartDate;
    private Date filterEndDate;
    public boolean isLoading;
    private int totalResultCount;

    @OnClick(R.id.btnFilterStartDate)
    public void onFilterStartDateSelection() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(MovieListActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                filterStartDate = calendar.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                startDateButton.setText(getString(R.string.start_date_label, sdf.format(filterStartDate)));

                //If end date is already applied, call the filter method to update the list.
                if (filterEndDate != null) {
                    if (isInternetConnected()) {
                        movieListPresenter.applyDateFilter(filterStartDate, filterEndDate);
                    } else {
                        showError(getString(R.string.internet_connection_issue));
                    }
                }
            }
        }, year, month, day).show();
    }

    @OnClick(R.id.btnFilterEndDate)
    public void onFilterEndDateSelection() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(MovieListActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                filterEndDate = calendar.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                endDateButton.setText(getString(R.string.end_date_label, sdf.format(filterEndDate)));

                //If start date is already applied, call the filter method to update the list.
                if (filterStartDate != null) {
                    if (isInternetConnected()) {
                        movieListPresenter.applyDateFilter(filterStartDate, filterEndDate);
                    } else {
                        showError(getString(R.string.internet_connection_issue));
                    }
                } else {
                    showError(getString(R.string.error_start_date));
                }
            }
        }, year, month, day).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        initializeUI();
        if (isInternetConnected()) {
            movieListPresenter.fetchListOfMovies();
        } else {
            showError(getString(R.string.internet_connection_issue));
        }
    }

    private void initializeUI() {
        movies = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        movieRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MovieListAdapter(movies);
        movieRecyclerView.setAdapter(adapter);

        movieListPresenter = new MovieListPresenter();
        movieListPresenter.attachView(this);

        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int visibleThreshold = 3;

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount < totalResultCount) {
                    if (isInternetConnected()) {
                        movieListPresenter.fetchListOfMovies();
                        isLoading = true;
                    } else {
                        showError(getString(R.string.internet_connection_issue));
                    }
                }
            }
        });

        //set on click listener for the item click on movie listing.
        adapter.setMovieItemClickListener(new MovieListAdapter.MovieItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie", movies.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void populateList(int page, int totalCount, List<Movie> movies) {
        //If the first page is populated, clear the old data.
        //this is applicable when you apply filter dates
        this.totalResultCount = totalCount;
        if (page == 1) {
            this.movies.clear();
        }
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog(String message) {
        super.showProgressDialog(message);
    }

    @Override
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
