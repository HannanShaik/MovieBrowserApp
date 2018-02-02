package com.hannan.movieapp.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showProgressDialog(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void dismissProgressDialog(){
        if(progressDialog!= null && progressDialog.isShowing() && !isFinishing()){
            progressDialog.dismiss();
        }
    }

    protected void setupToolBar() {
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);
    }
}
