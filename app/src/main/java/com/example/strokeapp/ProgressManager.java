package com.example.strokeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {

    private static ProgressManager self;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final int NUM_LEVELS = 4; // 0,1 = walking; 2,3 = arm
    private boolean [][] starFilled = new boolean [NUM_LEVELS][3];
    private boolean isInitialized;

    private ProgressManager() { }

    public void initialize(Context context) {
        if(!isInitialized) {
            preferences = context.getSharedPreferences("ProgressManager", 0);
            editor = preferences.edit();
            loadPreferences();
            isInitialized = true;
        }
    }

    public static ProgressManager getInstance() {
        if (self == null) {
            self = new ProgressManager();
        }
        return self;
    }

    public void changeStars(boolean add, int index, int level) {
        starFilled[level][index] = add;
        savePreferences();
    }

    public boolean starIsFilled(int level, int index) {
        return starFilled[level][index];
    }

    private void loadPreferences() {
        for(int i = 0; i < NUM_LEVELS; i++) {
            for(int j = 0; j < 3; j++) {
                starFilled[i][j] = preferences.getBoolean("filled" + i + j, false);
            }
        }
    }

    private void savePreferences() {
        for(int i = 0; i < NUM_LEVELS; i++) {
            for(int j = 0; j < 3; j++) {
                editor.putBoolean("filled" + i + j, starFilled[i][j]);
            }
        }
        editor.commit();
    }
}
