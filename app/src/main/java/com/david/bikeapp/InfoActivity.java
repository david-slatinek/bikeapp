package com.david.bikeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class InfoActivity extends AppCompatActivity {
    public static final int ACTIVITY_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication myCyclist = (MyApplication) getApplication();
        myCyclist.incrementActivity(MyApplication.INFO);
        setContentView(R.layout.activity_info);
    }

    public void onClickBack(View view) {
        super.onBackPressed();
    }

    public void onClickGithub(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/david-slatinek")));
    }
}