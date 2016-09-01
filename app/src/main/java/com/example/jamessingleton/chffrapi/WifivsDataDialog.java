package com.example.jamessingleton.chffrapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;



/**
 * Created by James Singleton on 8/9/2016.
 */

public class WifivsDataDialog extends DialogFragment implements View.OnClickListener {

    private CheckBox checkBox;
    private Button button1;
    private Button button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.dialog_box_layout, container);
        checkBox = (CheckBox) mainView.findViewById(R.id.checkBox);
        button1 = (Button) mainView.findViewById(R.id.button1);
        button2 = (Button) mainView.findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Store the isChecked to Preference here
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("DONT_SHOW_DIALOG", isChecked);
                editor.commit();

            }
        });
        return mainView;
    }

    @Override
    public void onClick(View view) {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (view.getId()) {
            case R.id.button1:
                // Do wifi stuff here

                    WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);

                editor.putString("connection", "wifi");
                editor.commit();

                dismiss();
                break;
            case R.id.button2:
                // Do cellular stuff here
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                Boolean isConnected = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

                editor.putString("connection", "mobile");
                editor.commit();

                if(isConnected == true)
                {
                    dismiss();
                }else
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
        }

    }
}



