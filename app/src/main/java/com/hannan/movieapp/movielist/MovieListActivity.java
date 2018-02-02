package com.hannan.movieapp.movielist;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListActivity extends BaseActivity implements MovieListView {

    private Date filterStartDate;
    private Date filterEndDate;

    @BindView(R.id.rvMovies)
    RecyclerView movieRecyclerView;
    @BindView(R.id.btnFilterStartDate)
    Button startDateButton;
    @BindView(R.id.btnFilterEndDate)
    Button endDateButton;

    private List<Movie> movies;
    private MovieListAdapter adapter;
    private int totalItemCount;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    public boolean isLoading;
    private int visibleThreshold = 1;
    private MovieListPresenter movieListPresenter;

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

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                startDateButton.setText("Start Date: " + sdf.format(filterStartDate));
                if (filterEndDate != null) {
                    movieListPresenter.applyDateFilter(filterStartDate, filterEndDate);
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

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                endDateButton.setText("End Date: " + sdf.format(filterEndDate));
                if (filterStartDate != null) {
                    movieListPresenter.applyDateFilter(filterStartDate, filterEndDate);
                } else {
                    Toast.makeText(MovieListActivity.this, "Please select the filter from date", Toast.LENGTH_SHORT).show();
                }
            }
        }, year, month, day).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        adapter = new MovieListAdapter(this, movies);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        movieRecyclerView.setAdapter(adapter);

        movieListPresenter = new MovieListPresenter();
        movieListPresenter.attachView(this);

        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                System.out.println(totalItemCount);
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    movieListPresenter.fetchListOfMovies();
                    isLoading = true;
                }
            }
        });

        adapter.setMovieItemClickListener(new MovieListAdapter.MovieItemClickListener() {
            @Override
            public void onClick(int position) {
                //move to new screen
                Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
                intent.putExtra("movie", movies.get(position));
                startActivity(intent);
            }
        });

        movieListPresenter.fetchListOfMovies();
    }

    @Override
    public void populateList(int page, List<Movie> movies) {
        if(page == 1){
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

}
