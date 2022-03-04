package com.david.bikeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyApplication myCyclist;
    public Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = findViewById(R.id.buttonSettings);

        settings.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), SettingsActivity.class)));

        myCyclist = (MyApplication) getApplication();
        myCyclist.incrementActivity(MyApplication.MAIN);
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
                displayToast(getString(R.string.tour_added) + "!");
                displayToast(getString(R.string.nr_tours) + " " + myCyclist.getCyclist().size());
            }
        }
    }

    public void onClickView(View view) {
        startActivity(new Intent(getBaseContext(), ViewActivity.class));
    }
}