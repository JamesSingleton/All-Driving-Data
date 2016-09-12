package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by James Singleton on 8/7/2016.
 */

public class  SecondFragment extends Fragment implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    Map<String, Route> drives;
    ImageView imageView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        APIRequestsUtil.setOnAPIResponseListener(this);
        imageView = (ImageView) myView.findViewById(R.id.driveImageView);
        //Glide.with(this).load("https://s3-us-west-2.amazonaws.com/chffrprivate/comma-c1c0ffe42c459a06/0d69245e9ec3dcabd08c3f2145fe0c94_2016-08-16--09-08-56/sec50.jpg").into(imageView);
        return myView;
    }

    @Nullable
    private void populateView() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drives = APIRequestsUtil.getRoutes();

                // edit: you need to generate your List data from the entrySet
                // the ArrayAdapter cannot take a Set argument - needs to be a List
                List<Map.Entry> list = new ArrayList<Map.Entry>();
                for (Map.Entry drive : drives.entrySet()) {
                    list.add(drive);
                }

                // populate the ListView
                // may need to change "getActivity()" to something else
                // this constructor needs the "this" context of the activity
                DriveURLAdapter drivesURLAdapter = new DriveURLAdapter(getActivity(), list);
                Spinner spinner = (Spinner)myView.findViewById(R.id.spinner);
                drivesURLAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(drivesURLAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                        String url = DriveURLAdapter.DriveURL;
                        //System.out.println(url);
                        Glide.with(getActivity()).load(url+"/sec500.jpg").into(imageView);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }


                });

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