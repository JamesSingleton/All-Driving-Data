package com.example.jamessingleton.chffrapi;

import android.app.AlertDialog;
import android.app.Fragment;
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

    //Default values
    private String connection_string_value="wifi";
    private String measurement_string_value="imperial";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.settings_layout, container, false);
        Button saveButton= (Button)myView.findViewById(R.id.saveButton);

        RadioGroup radioGroup = (RadioGroup) myView .findViewById(R.id.radioGroup);

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
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(CONNECTION_STRING, connection_string_value);
                editor.putString(MEASUREMENT_STRING, measurement_string_value);
                editor.commit();
            }
        });

        return myView;
    }

}
