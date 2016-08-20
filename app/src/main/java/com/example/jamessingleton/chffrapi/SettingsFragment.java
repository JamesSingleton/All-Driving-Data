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
    View myView;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_layout, container, false);
        button= (Button)getActivity().findViewById(R.id.button3);


        RadioGroup radioGroup = (RadioGroup) myView .findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.radioButton7:
                        // switch to fragment 1
                        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(false);
                        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                        Boolean isConnected = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
                        if(isConnected != true)
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

                            alertDialog.setTitle("Info");
                            alertDialog.setMessage("Mobile Data is not turned on. Please turn Mobile Data on to continue.");
                            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dismiss();
                                    Intent intentSettings = new Intent(Settings.ACTION_SETTINGS);
                                    startActivity(intentSettings);
                                }
                            });

                            alertDialog.show();
                        }
                        break;
                    case R.id.radioButton6:
                        // Fragment 2
                        WifiManager wifiManager1 = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                        wifiManager1.setWifiEnabled(true);

                        break;
                }
            }
//            public void onClick(View v) {
//                private void saveGenderInPreference () {
//                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    RadioGroup radioGroup1 = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
//                    int selectedId = radioGroup1.getCheckedRadioButtonId();
//                    if (selectedId == R.id.radioButton7)
//                        editor.putBoolean("is_Wifi", true);
//                    else
//                        editor.putBoolean("is_Wifi", false);
//
//                    editor.commit();
//                }
//            }
        });

        return myView;
    }
}
