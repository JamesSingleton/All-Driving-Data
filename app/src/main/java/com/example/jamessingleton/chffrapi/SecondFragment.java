package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.google.android.gms.drive.Drive;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James Singleton on 8/7/2016.
 * Drive Player
 */

public class SecondFragment extends Fragment implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    Map<String, Route> drives;
    private ArrayAdapter<Integer> adapter;
    private TextView URLDrive;
    private String DriveURL;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        APIRequestsUtil.setOnAPIResponseListener(this);
        return myView;
    }

    private void populateView() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drives = APIRequestsUtil.getRoutes();


                int driveNum = 0;
                for (Map.Entry drive : drives.entrySet()) {
                    TableRow tr = new TableRow(getActivity());
                    Route route = (Route) drive.getValue();
                    tr.setId(driveNum++);
                    //tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //https://s3-us-west-2.amazonaws.com/chffrprivate/comma-c1c0ffe42c459a06/71de9c515e03a7616cd69cf6013878b0_2016-07-12--19-04-34/sec600.jpg gets an image
                    //we would have to cycle through the following parts of it depending on what drive they pick '2016-07-12--19-04-34' and '600'
                    DriveURL = route.getUrl();

                    URLDrive = (TextView) myView.findViewById(R.id.textView3);
                    URLDrive.setText(URLDrive.getText().toString()+ DriveURL + System.getProperty("line.separator"));


                }
            }
        });
    }

    //@Override
    public void onFailure(Request request, Throwable throwable) {

    }

    //@Override
    public void onResponse(Response response) {
        populateView();
    }

    @Override
    public void onResume() {
        super.onResume();

//        try {
//            APIRequestsUtil.run();
//            populateView();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
