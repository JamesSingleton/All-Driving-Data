package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.io.IOException;
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

//                TableLayout tl;
//                tl = (TableLayout) myView.findViewById(R.id.fragment1_tlayout);
                driveNumber = (TextView) myView.findViewById(R.id.Drive_Number);
                driveDistance = (TextView) myView.findViewById(R.id.Drive_Distance);
                driveTime = (TextView) myView.findViewById(R.id.Drive_Time);

                driveNumber.setText("Drive Num.");
                driveDistance.setText("Distance");
                driveTime.setText("Time");
                int driveNum = 0;
                for (Map.Entry drive : drives.entrySet()) {
                    TableRow tr = new TableRow(getActivity());
                    Route route = (Route) drive.getValue();
                    tr.setId(driveNum++);
                    //tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    DateTime startTime = new DateTime(route.getStart_time());
                    DateTime endTime = new DateTime(route.getEnd_time());

//                    System.out.println("StarTime: " + startTime);
//                    System.out.println("EndTime: " + endTime);
//                    System.out.println("Duration: " + (endTime.getMillis() - startTime.getMillis()));
//                    TextView tv1 = new TextView(getActivity());
//                    tv1.setText("Drive Number: " + driveNum + "" +
//                                "\nDistance: " + Double.parseDouble(route.getLen()) / 1000 + " km" +
//                                "\nTime: " + (endTime.getMillis() - startTime.getMillis())/ 1000 + " s");
//                    tv1.setId(driveNum);
//                    tv1.setTextColor(Color.RED);
//                    tv1.setTextSize(20);
//                    tv1.setPadding(5, 5, 5, 5);
//                    tr.addView(tv1);
//
//                    tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//


                    driveNumList = (TextView) myView.findViewById(R.id.Drive_Number_List);
                    driveDistList = (TextView) myView.findViewById(R.id.Drive_Distance_List);
                    driveTimeList = (TextView) myView.findViewById(R.id.Drive_Time_List);

                    driveNumList.setText(driveNumList.getText().toString() + String.valueOf(driveNum) + System.getProperty("line.separator"));
                    driveDistList.setText(driveDistList.getText().toString() + Float.parseFloat(route.getLen()) / 1000 + " km" + System.getProperty("line.separator"));
                    driveTimeList.setText(driveTimeList.getText().toString() + ((endTime.getMillis() - startTime.getMillis())/ 1000)/60 + " min" + System.getProperty("line.separator"));
                }
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
}