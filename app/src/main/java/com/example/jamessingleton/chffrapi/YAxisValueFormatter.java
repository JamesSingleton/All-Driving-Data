package com.example.jamessingleton.chffrapi;

import android.content.SharedPreferences;

import com.github.mikephil.charting.components.AxisBase;

/**
 * Created by frank on 9/10/16.
 */
public class YAxisValueFormatter implements IAxisValueFormatter {
    private SharedPreferences sharedPref;

    public YAxisValueFormatter(SharedPreferences preferences) {
        sharedPref = preferences;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (sharedPref.getString("measurement", null).equals("imperial"))
            return Math.round(value) + " Mph";
        return Math.round(value) + " Km/h";
    }

    @Override
    public int getDecimalDigits() {
        return 1;
    }
}