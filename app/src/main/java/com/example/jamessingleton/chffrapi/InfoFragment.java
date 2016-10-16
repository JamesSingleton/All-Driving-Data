package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by James Singleton on 10/15/2016.
 */

public class InfoFragment extends Fragment {

    public TextView versionText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.info_layout, container, false);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        versionText = (TextView) myView.findViewById(R.id.versionNumber);
        versionText.setText(" "+versionName);
        return myView;
    }

}
