package com.david.bikeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private MyApplication myCyclist;

    private EditText email, name, age;
    private RadioButton male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myCyclist = (MyApplication) getApplication();
        myCyclist.incrementActivity(MyApplication.SETTINGS);

        email = findViewById(R.id.editEmail);
        name = findViewById(R.id.editSettingsName);
        age = findViewById(R.id.editAge);
        male = findViewById(R.id.radioButtonMale);
        female = findViewById(R.id.radioButtonFemale);

        loadSettings();
    }

    public void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onClickDelete(View view) {
        myCyclist.deleteSettings();
        displayToast(getString(R.string.settings_deleted));
        onBackPressed();
    }

    public void loadSettings() {
        if (!myCyclist.getEmail().equals(String.valueOf(MyApplication.DEFAULT_VALUE)))
            email.setText(myCyclist.getEmail());

        if (!myCyclist.getName().equals(String.valueOf(MyApplication.DEFAULT_VALUE)))
            name.setText(myCyclist.getName());

        if (myCyclist.getAge() != MyApplication.DEFAULT_VALUE)
            age.setText(String.valueOf(myCyclist.getAge()));

        if (myCyclist.getGender() == MyApplication.DEFAULT_VALUE) {
            male.setChecked(false);
            female.setChecked(false);
        } else if (myCyclist.getGender() == MyApplication.MALE_CODE) {
            male.setChecked(true);
        } else if (myCyclist.getGender() == MyApplication.FEMALE_CODE) {
            female.setChecked(true);
        }
    }

    public void onClickBack(View view) {
        super.onBackPressed();
    }

    public void onClickSave(View view) {
        if (!email.getText().toString().equals(""))
            myCyclist.setEmail(email.getText().toString());

        if (!name.getText().toString().equals(""))
            myCyclist.setName(name.getText().toString());

        if (!age.getText().toString().equals(""))
            myCyclist.setAge(Integer.parseInt(age.getText().toString()));

        if (male.isChecked()) {
            myCyclist.setGender(MyApplication.MALE_CODE);
        } else if (female.isChecked()) {
            myCyclist.setGender(MyApplication.FEMALE_CODE);
        }

        myCyclist.saveSettings();

        displayToast(getString(R.string.settings_saved));
        onBackPressed();
    }
}