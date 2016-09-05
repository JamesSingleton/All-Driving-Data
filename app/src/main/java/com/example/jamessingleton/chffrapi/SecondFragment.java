package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class SecondFragment extends Fragment implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    Map<String, Route> drives;
    private ArrayAdapter<Integer> adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        APIRequestsUtil.setOnNetWorkListener(this);
        return myView;
    }

    private void populateView() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drives = APIRequestsUtil.getRoutes();
                Spinner spinner = (Spinner) myView.findViewById(R.id.spinner);
                //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
                int driveNum = 0;
                for (Map.Entry drive : drives.entrySet()) {
                    TableRow tr = new TableRow(getActivity());
                    Route route = (Route) drive.getValue();
                    tr.setId(driveNum++);
                    //tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ArrayAdapter<Integer> aa = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item,driveNum);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(aa);
                    //spinner.setId(driveNum);

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

        try {
            APIRequestsUtil.run();
            populateView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
