package com.david.bikeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.david.data.Tour;

public class MainActivity extends AppCompatActivity {
    public static final int ACTIVITY_ID = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyApplication myCyclist;

    public Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHtml = findViewById(R.id.tvHtml);
        settings = findViewById(R.id.buttonSettings);

        settings.setOnClickListener(v -> {
            startActivity(new Intent(getBaseContext(), SettingsActivity.class));
        });

        String welcomeStr = getString(R.string.view_results);
        tvHtml.setText(Html.fromHtml(welcomeStr, Html.FROM_HTML_MODE_COMPACT));
        tvHtml.setMovementMethod(LinkMovementMethod.getInstance());

        myCyclist = (MyApplication) getApplication();
        myCyclist.incrementActivity(MyApplication.MAIN);

        if (myCyclist.getCyclist().size() < 100) {
            for (int i = 0; i < 100; i++) {
                myCyclist.getCyclist().addTour(Tour.getRandomTour());
            }
        }

        myCyclist.saveToFile();
    }

    public void onClickExit(View view) {
        myCyclist.saveToFile();
        finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCyclist.saveToFile();
    }

    public void onClickCreateTour(View view) {
        Intent i = new Intent(getBaseContext(), InsertActivity.class).putExtra(InsertActivity.FORM_MODE_ID, InsertActivity.FORM_MODE_INSERT);
        startActivityForResult(i, InsertActivity.ACTIVITY_ID);
    }

    public void onClickInfo(View view) {
        startActivity(new Intent(getBaseContext(), InfoActivity.class));
    }

    public void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == InsertActivity.ACTIVITY_ID) {
            if (resultCode == RESULT_OK) {
                Log.i(TAG, getString(R.string.received_value) + data.getExtras().get(InsertActivity.DATA_ACTION));

                Log.i(TAG, getString(R.string.nr_tours) + myCyclist.getCyclist().size());

                displayToast(getString(R.string.tour_added) + "!");
                displayToast(getString(R.string.nr_tours) + " " + myCyclist.getCyclist().size());
            }
        }
    }

    public void onClickView(View view) {
        startActivity(new Intent(getBaseContext(), ViewActivity.class));
    }
}