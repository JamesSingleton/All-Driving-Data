package com.example.jamessingleton.chffrapi;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.net.wifi.WifiManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by James Singleton on 8/8/2016.
 */

public class SettingsFragment extends Fragment
{
    private static String CONNECTION_STRING = "connection";
    private static String MEASUREMENT_STRING = "measurement";
    private RadioButton rb1 , rb2 , rb3 , rb4 ;

    //Default values
    private String connection_string_value="wifi";
    private String measurement_string_value="imperial";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.settings_layout, container, false);
        Button saveButton= (Button)myView.findViewById(R.id.saveButton);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        RadioGroup radioGroup = (RadioGroup) myView .findViewById(R.id.radioGroup);

        rb1 = (RadioButton) radioGroup.getChildAt(0);
        rb2 = (RadioButton) radioGroup.getChildAt(1);

        //Check for saved values
        if(!rb1.isChecked() || !rb1.isChecked()) {
            rb1.setChecked(sharedPref.getBoolean("rb1", false));
            rb2.setChecked(sharedPref.getBoolean("rb2", false));
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radioButton7:
                        // switch to fragment 1
                        connection_string_value = "mobile";
                        break;
                    case R.id.radioButton6:
                        // Fragment 2
                        connection_string_value = "wifi";
                        break;
                }
            }

        });

        RadioGroup radioGroupMeasurement = (RadioGroup) myView .findViewById(R.id.radioGroupMeasurement);

        rb3 = (RadioButton) radioGroupMeasurement.getChildAt(0);
        rb4 = (RadioButton) radioGroupMeasurement.getChildAt(1);

        if(!rb3.isChecked() || !rb4.isChecked()) {
            rb3.setChecked(sharedPref.getBoolean("rb3", false));
            rb4.setChecked(sharedPref.getBoolean("rb4", false));
        }
        radioGroupMeasurement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radioButtonImperial:
                        // switch to fragment 1
                        measurement_string_value = "imperial";
                        break;
                    case R.id.radioButtonMetric:
                        // Fragment 2
                        measurement_string_value = "metric";
                        break;
                }
            }

        });

        //Save settings
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString(CONNECTION_STRING, connection_string_value);
                editor.putBoolean("rb1", rb1.isChecked());
                editor.putBoolean("rb2", rb2.isChecked());
                editor.putString(MEASUREMENT_STRING, measurement_string_value);
                editor.putBoolean("rb3", rb3.isChecked());
                editor.putBoolean("rb4", rb4.isChecked());

                editor.commit();
                getFragmentManager().popBackStackImmediate();

            }
        });


        return myView;
    }

}
