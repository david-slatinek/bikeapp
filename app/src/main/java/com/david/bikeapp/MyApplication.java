package com.david.bikeapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.david.data.Cyclist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();
    public static final String MY_PREFS_NAME = "config";
    public static final int MALE_CODE = 0;
    public static final int FEMALE_CODE = 1;
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    public static final int DEFAULT_VALUE = -1;
    private static final int APP_DEFAULT_VALUE = 0;

    public static final String INFO = "info";
    public static final String INSERT = "insert";
    public static final String MAIN = "main";
    public static final String SETTINGS = "settings";

    private static final String MY_FILE_NAME = "data.json";
    private File file;
    private Gson gson;

    private Cyclist cyclist;
    private String id;
    private String email;
    private String name;
    private int gender;
    private int age;

    private int info;
    private int insert;
    private int main;
    private int settings;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Cyclist getCyclist() {
        return cyclist;
    }

    public void setCyclist(String name, String lastname) {
        cyclist = new Cyclist(name, lastname);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        readSettings();

        if (getId().equals(String.valueOf(DEFAULT_VALUE))) {
            id = UUID.randomUUID().toString().replace("-", "");
            editor.putString(ID, id);
            editor.apply();
        }

        readFromFile();
    }

    private void readSettings() {
        id = preferences.getString(ID, String.valueOf(DEFAULT_VALUE));
        email = preferences.getString(EMAIL, String.valueOf(DEFAULT_VALUE));
        name = preferences.getString(NAME, String.valueOf(DEFAULT_VALUE));
        gender = preferences.getInt(GENDER, DEFAULT_VALUE);
        age = preferences.getInt(AGE, DEFAULT_VALUE);

        info = preferences.getInt(INFO, APP_DEFAULT_VALUE);
        insert = preferences.getInt(INSERT, APP_DEFAULT_VALUE);
        main = preferences.getInt(MAIN, APP_DEFAULT_VALUE);
        settings = preferences.getInt(SETTINGS, APP_DEFAULT_VALUE);
    }

    public void saveSettings() {
        editor.putString(EMAIL, getEmail());
        editor.putString(NAME, getName());
        editor.putInt(GENDER, getGender());
        editor.putInt(AGE, getAge());
        editor.apply();
    }

    public void deleteSettings() {
        editor.remove(EMAIL).remove(NAME).remove(GENDER).remove(AGE).apply();
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .setPrettyPrinting().create();
        }
        return gson;
    }

    private File getFile() {
        if (file == null) {
            file = new File(getFilesDir(), MY_FILE_NAME);
        }
        return file;
    }

    private void readFromFile() {
        try {
            cyclist = getGson().fromJson(FileUtils.readFileToString(getFile()), Cyclist.class);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(cyclist));
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void incrementActivity(String name) {
        switch (name) {
            case INFO:
                info++;
                editor.putInt(INFO, getInfo());
                editor.apply();
                break;

            case INSERT:
                insert++;
                editor.putInt(INSERT, getInsert());
                editor.apply();
                break;

            case MAIN:
                main++;
                editor.putInt(MAIN, getMain());
                editor.apply();
                break;

            case SETTINGS:
                settings++;
                editor.putInt(SETTINGS, getSettings());
                editor.apply();
                break;
        }
    }

    private int getInfo() {
        return info;
    }

    private int getInsert() {
        return insert;
    }

    private int getMain() {
        return main;
    }

    private int getSettings() {
        return settings;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
