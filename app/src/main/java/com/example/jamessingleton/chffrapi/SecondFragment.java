package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James Singleton on 8/7/2016.
 * Drive Player
 */

public class SecondFragment extends Fragment
        //implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    //Map<String, Route> drives;
    //private ArrayAdapter<Integer> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        //APIRequestsUtil.setOnNetWorkListener(this);
        return myView;
    }

//    private void populateView() {
//        this.getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                drives = APIRequestsUtil.getRoutes();
//                ArrayList<Integer> IntItems = new ArrayList<>();
//
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//                int driveNum = 0;
//                for (Map.Entry drive : drives.entrySet()) {
//                    TableRow tr = new TableRow(getActivity());
//                    Route route = (Route) drive.getValue();
//                    tr.setId(driveNum++);
//                    DateTime startTime = new DateTime(route.getStart_time());
//                    DateTime endTime = new DateTime(route.getEnd_time());
//
//                    System.out.println("StarTime: " + startTime);
//                    System.out.println("EndTime: " + endTime);
//                    System.out.println("Duration: " + (endTime.getMillis() - startTime.getMillis()));
//
//                    IntItems.add(driveNum++);
//
//                }
//                adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,IntItems);
//                Spinner spinnerDriveNumber;
//                spinnerDriveNumber = (Spinner) myView.findViewById(R.id.spinner);
//                spinnerDriveNumber.setAdapter(adapter);
//            }
//        });
//
//    }
//
//    @Override
//    public void onFailure(Request request, Throwable throwable) {
//
//    }
//
//    @Override
//    public void onResponse(Response response) {
//        populateView();
//    }
}
