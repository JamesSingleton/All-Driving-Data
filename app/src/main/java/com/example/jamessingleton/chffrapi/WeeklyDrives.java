package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by James Singleton on 8/13/2016.
 */

public class WeeklyDrives extends Fragment implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    Map<String, Route> drives;
    private TextView driveNumber;
    private TextView driveDistance;
    private TextView driveTime;
    private TextView driveNumList;
    private TextView driveDistList;
    private TextView driveTimeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.weekly_drives, container, false);
        APIRequestsUtil.setOnNetWorkListener(this);

        return myView;
    }


    private void populateView() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drives = APIRequestsUtil.getRoutes();

                driveNumber = (TextView) myView.findViewById(R.id.Drive_Number);
                driveDistance = (TextView) myView.findViewById(R.id.Drive_Distance);
                driveTime = (TextView) myView.findViewById(R.id.Drive_Time);

                driveNumber.setText("Drive Number");
                driveDistance.setText("Drive Distance");
                driveTime.setText("Drive Time");

                // edit: you need to generate your List data from the entrySet
                // the ArrayAdapter cannot take a Set argument - needs to be a List
                List<Map.Entry> list = new ArrayList<Map.Entry>();
                for (Map.Entry drive : drives.entrySet()) {
                    list.add(drive);
                }

                // populate the ListView
                // may need to change "getActivity()" to something else
                // this constructor needs the "this" context of the activity
                DriveListAdapter drivesListAdapter = new DriveListAdapter(getActivity(), list);
                ListView listView = (ListView)myView.findViewById(R.id.listView);
                listView.setAdapter(drivesListAdapter);
            }
        });
    }

    @Override
    public void onFailure(Request request, Throwable throwable) {

    }

    @Override
    public void onResponse(Response response) {
        populateView();
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            APIRequestsUtil.run();
            populateView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}