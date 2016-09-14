package com.example.jamessingleton.chffrapi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.RouteCoord;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * Created by James Singleton on 9/13/2016.
 */

public class RouteAdapter extends ArrayAdapter<Map.Entry> {

    private final Activity context;
    // you may need to change List to something else (whatever is returned from drives.entrySet())
    private final List<Map.Entry> drives;
    public static String DriveURL;
    public static String routeLat;
    public static String routeLng;


    SharedPreferences sharedPref;
    // may also need to change List here (above comment)
    public RouteAdapter(Activity context, List<Map.Entry> drives) {
        super(context, R.layout.drive_url_list, drives);
        this.context = context;
        this.drives = drives;
        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.third_layout, null, true);

        Map.Entry drive = this.drives.get(position);

        // position is the index of the drives.entrySet() array
        int driveNum = position + 1;

        // need to import your Route class
        Route route = (Route) drive.getValue();
        RouteCoord routeCoord = (RouteCoord) drive.getValue();
        routeLat = routeCoord.getLat();
        routeLng = routeCoord.getLng();

        // need to import your URL class
        DriveURL = route.getUrl();
        System.out.println(DriveURL+"/route.coords");

        return rowView;
    }
}
