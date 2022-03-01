package com.david.bikeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.david.data.Point;
import com.david.data.Tour;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Vibrator;

public class InsertActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final int ACTIVITY_ID = 102;

    public static final String FORM_MODE_ID = "FORM_MODE_ID";
    public static final int FORM_MODE_INSERT = 0;
    public static final int FORM_MODE_UPDATE = 1;

    public static final String DATA_ACTION = "DATA_ACTION";
    public static final int DATA_INSERTED = 0;
    public static final int DATA_UPDATED = 1;


    private static final String TAG = InsertActivity.class.getSimpleName();
    private int formMode = FORM_MODE_INSERT;
    private EditText name, lastName;
    private TextView startDate, endDate;
    private TextView startTime, endTime;
    private EditText textViewLength;
    private EditText textViewDescription;

    private MyApplication myCyclist;
    LocalDate start_date, end_date;
    LocalTime start_time, end_time;
    double length;
    String description;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        name = findViewById(R.id.editName);
        lastName = findViewById(R.id.editLastName);
        textViewLength = findViewById(R.id.editLength);
        textViewDescription = findViewById(R.id.editDescription);
        startDate = findViewById(R.id.tvStartDate);
        endDate = findViewById(R.id.tvEndDate);
        startTime = findViewById(R.id.tvStartTime);
        endTime = findViewById(R.id.tvEndTime);
        Button buttonCyclist = findViewById(R.id.btnCreateCyclist);
        Button createTour = findViewById(R.id.btnCreateTour);

        myCyclist = (MyApplication) getApplication();
        myCyclist.incrementActivity(MyApplication.INSERT);

        if (myCyclist.getCyclist() != null) {
            name.setText(myCyclist.getCyclist().getName());
            lastName.setText(myCyclist.getCyclist().getSurname());
            buttonCyclist.setText(R.string.update_cy);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            formMode = extras.getInt(FORM_MODE_ID);
            if (formMode == FORM_MODE_UPDATE) {
                id = extras.getInt("ID");
            }
        }

        if (formMode == FORM_MODE_UPDATE) {
            start_date = LocalDate.of(myCyclist.getCyclist().getTourAtPos(id).getStartPoint().getYear(),
                    myCyclist.getCyclist().getTourAtPos(id).getStartPoint().getMonth(),
                    myCyclist.getCyclist().getTourAtPos(id).getStartPoint().getDay());
            start_time = LocalTime.of(myCyclist.getCyclist().getTourAtPos(id).getStartPoint().getHour(),
                    myCyclist.getCyclist().getTourAtPos(id).getStartPoint().getMinute());

            end_date = LocalDate.of(myCyclist.getCyclist().getTourAtPos(id).getEndPoint().getYear(),
                    myCyclist.getCyclist().getTourAtPos(id).getEndPoint().getMonth(),
                    myCyclist.getCyclist().getTourAtPos(id).getEndPoint().getDay());
            end_time = LocalTime.of(myCyclist.getCyclist().getTourAtPos(id).getEndPoint().getHour(),
                    myCyclist.getCyclist().getTourAtPos(id).getEndPoint().getMinute());

            textViewLength.setText(String.valueOf(myCyclist.getCyclist().getTourAtPos(id).getLength()));

            textViewDescription.setText(myCyclist.getCyclist().getTourAtPos(id).getDescription());

            startDate.setText(start_date.toString());
            endDate.setText(end_date.toString());
            startTime.setText(start_time.toString());
            endTime.setText(end_time.toString());

            createTour.setText(R.string.update_tour);
        }
    }

    public void clearFocus(View view) {
        name.clearFocus();
        lastName.clearFocus();
        textViewLength.clearFocus();
        textViewDescription.clearFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void displayToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onCreateCyclist(View view) {
        if (TextUtils.isEmpty(name.getText())) {
            name.setError(getString(R.string.enter_name));
            displayToast(getString(R.string.enter_name));
            return;
        }

        if (TextUtils.isEmpty(lastName.getText())) {
            lastName.setError(getString(R.string.last_name));
            displayToast(getString(R.string.last_name));
            return;
        }

        if (myCyclist.getCyclist() != null) {
            myCyclist.getCyclist().setName(name.getText().toString());
            myCyclist.getCyclist().setSurname(lastName.getText().toString());
            Log.i(TAG, getString(R.string.update_name_surname) + " " + myCyclist.getCyclist().getName() + " " + myCyclist.getCyclist().getSurname());

            clearFocus(view);
            Toast.makeText(this, getString(R.string.update_name_surname) + " " + myCyclist.getCyclist().getName() + " " + myCyclist.getCyclist().getSurname(), Toast.LENGTH_SHORT).show();
        } else {
            myCyclist.setCyclist(name.getText().toString(), lastName.getText().toString());
            Log.i(TAG, getString(R.string.created) + myCyclist.getCyclist());

            clearFocus(view);
            Toast.makeText(this, String.format("Cyclist %s created!", myCyclist.getCyclist().getName()), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateTour(View view) {
        if (myCyclist == null) {
            displayToast(getString(R.string.firstly));
            return;
        }

        if (start_date == null) {
            displayToast(getString(R.string.select_start_date));
            return;
        }

        if (end_date == null) {
            displayToast(getString(R.string.select_end_date));
            return;
        }

        if (start_time == null) {
            displayToast(getString(R.string.select_start_time));
            return;
        }

        if (end_time == null) {
            displayToast(getString(R.string.select_end_time));
            return;
        }

        if (TextUtils.isEmpty(textViewDescription.getText())) {
            textViewDescription.setError(getString(R.string.enter_tour_desc));
            displayToast(getString(R.string.enter_tour_desc));
            return;
        }

        try {
            length = Double.parseDouble(textViewLength.getText().toString());

            if (length == 0) {
                displayToast(getString(R.string.cannot_zero));
                textViewLength.setError(getString(R.string.cannot_zero));
                return;
            }
            description = textViewDescription.getText().toString();


            if (formMode == FORM_MODE_UPDATE) {
                Tour tour = myCyclist.getCyclist().getTourAtPos(id);

                tour.setStartPoint(new Point(LocalDateTime.of(start_date, start_time)));
                tour.setEndPoint(new Point(LocalDateTime.of(end_date, end_time)));
                tour.setLength(length);
                tour.setDescription(description);

                Intent intent = getIntent().putExtra(DATA_ACTION, DATA_UPDATED);
                setResult(RESULT_OK, intent);
                finish();
            }

            Tour tour = new Tour(new Point(LocalDateTime.of(start_date, start_time)), new Point(LocalDateTime.of(end_date, end_time)), length, description);
            myCyclist.getCyclist().addTour(tour);

            Intent intent = getIntent().putExtra(DATA_ACTION, DATA_INSERTED);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            displayToast(getString(R.string.error_try_again));
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    public void onClickBack(View view) {
        super.onBackPressed();
    }

    public void showTimePicker(View view) {
        if (view == findViewById(R.id.btnStartTime)) {
            startTime = findViewById(R.id.tvStartTime);
            endTime = null;
        } else if (view == findViewById(R.id.btnEndTime)) {
            endTime = findViewById(R.id.tvEndTime);
            startTime = null;
        }

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                2,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );

        dialog.show();
    }

    public void showDatePicker(View view) {
        if (view == findViewById(R.id.btnStartDate)) {
            startDate = findViewById(R.id.tvStartDate);
            endDate = null;
        } else if (view == findViewById(R.id.btnEndDate)) {
            endDate = findViewById(R.id.tvEndDate);
            startDate = null;
        }

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "." + (month + 1) + "." + year;

        if (view == findViewById(R.id.btnStartDate)) {
            startDate.setText(date);
            start_date = LocalDate.of(year, month + 1, dayOfMonth);
        } else if (view == findViewById(R.id.btnEndDate)) {
            endDate.setText(date);
            end_date = LocalDate.of(year, month + 1, dayOfMonth);
        }

        if (startDate != null) {
            startDate.setText(date);
            start_date = LocalDate.of(year, month + 1, dayOfMonth);
        } else {
            endDate.setText(date);
            end_date = LocalDate.of(year, month + 1, dayOfMonth);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String min = minute < 10 ? "0" + minute : String.valueOf(minute);
        String time = hourOfDay + "." + min;

        if (startTime != null) {
            startTime.setText(time);
            start_time = LocalTime.of(hourOfDay, Integer.parseInt(min));
        } else {
            endTime.setText(time);
            end_time = LocalTime.of(hourOfDay, Integer.parseInt(min));
        }
    }

    public void onQR(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    public void printQRResults(String result) {
        try {
            JSONObject object = new JSONObject(result);

            start_date = LocalDate.of(object.getInt("start_year"), object.getInt("start_month"), object.getInt("start_day"));
            end_date = LocalDate.of(object.getInt("end_year"), object.getInt("end_month"), object.getInt("end_day"));
            start_time = LocalTime.of(object.getInt("start_hour"), object.getInt("start_minute"));
            end_time = LocalTime.of(object.getInt("end_hour"), object.getInt("end_minute"));
            description = object.getString("description");
            length = object.getDouble("length");

            if (length <= 0) {
                displayToast("Invalid tour length!");
                return;
            }

            startDate.setText(start_date.toString());
            endDate.setText(end_date.toString());
            startTime.setText(start_time.toString());
            endTime.setText(end_time.toString());
            textViewDescription.setText(description);
            textViewLength.setText(Double.toString(length));
        } catch (JSONException e) {
            displayToast("Invalid format!");
            vibrateOnError();
            Log.e(TAG, e.toString());
        }
    }

    public void vibrateOnError() {
        Vibrator vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        vib.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            printQRResults(result.getContents());
        } else {
            displayToast(getString(R.string.invalid_scan));
            vibrateOnError();
        }
    }
}