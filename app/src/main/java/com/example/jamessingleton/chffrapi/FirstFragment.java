package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by James Singleton on 8/7/2016.
 * Speed Graph
 */

public class FirstFragment extends Fragment implements APIRequestsUtil.APIRequestResponseListener
{
    View myView;
    private int itemCount = 0;
    private BarChart mChart;
    Map<String, Route> drives;
    private SharedPreferences sharedPref;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        APIRequestsUtil.setOnAPIResponseListener(this);
        mChart = new BarChart(getActivity());
        mChart.setDescription("");


        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        Legend l = mChart.getLegend();
        l.setEnabled(true);

        mChart.getAxisRight().setEnabled(true);
        mChart.setPinchZoom(false);

        IAxisValueFormatter custom = new YAxisValueFormatter(sharedPref);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinValue(0f);


        // programatically add the chart
        FrameLayout parent = (FrameLayout) myView.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        if(APIRequestsUtil.isCallBackSuccess())
            populateView();

        return myView;

    }


    private void populateView() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drives = APIRequestsUtil.getRoutes();

                IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(drives);

                mChart.setScaleXEnabled(true);

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawAxisLine(true);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(xAxisFormatter);



                List<Map.Entry> list = new ArrayList<Map.Entry>();

                ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
                ArrayList<BarEntry> entries = new ArrayList<BarEntry>();


                int count = 0;
                for (Map.Entry drive : drives.entrySet()) {

                    String date = drive.getKey().toString();
                    Route route = (Route) drive.getValue();

                    double value = getSpeed(route);
                    float fValue = (float) value;
                    entries.add(new BarEntry(count + 1f, fValue));
                    count++;

                    list.add(drive);
                }

                BarDataSet ds = new BarDataSet(entries, "Drives");
                ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
                sets.add(ds);

                BarData d = new BarData(sets);

                mChart.getAxisRight().setEnabled(false);
                mChart.getLegend().setEnabled(true);
                mChart.getXAxis().setDrawLabels(true);
                mChart.setData(d);
                mChart.invalidate();
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

    private double getSpeed(Route route) {

        DateTime startTime = new DateTime(route.getStart_time());
        DateTime endTime = new DateTime(route.getEnd_time());
        double milliseconds = endTime.getMillis() - startTime.getMillis();
        double distance = (double) Math.round((Float.parseFloat(route.getLen())) * 100.0) / 100.0;


        if (sharedPref.getString("measurement", null).equals("imperial"))
            distance = distance / 1.609344;

        return distance * 3600 / milliseconds;
    }
}
