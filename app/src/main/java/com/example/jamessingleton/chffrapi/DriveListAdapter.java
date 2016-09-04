package com.example.jamessingleton.chffrapi;

/**
 * Created by James Singleton on 9/2/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import org.joda.time.DateTime;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DriveListAdapter extends ArrayAdapter<Map.Entry> {

    private final Activity context;
    // you may need to change List to something else (whatever is returned from drives.entrySet())
    private final List<Map.Entry> drives;

    SharedPreferences sharedPref;
    // may also need to change List here (above comment)
    public DriveListAdapter(Activity context, List<Map.Entry> drives) {
        super(context, R.layout.drive_list_item, drives);
        this.context = context;
        this.drives = drives;
        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.drive_list_item, null, true);

        Map.Entry drive = this.drives.get(position);

        // position is the index of the drives.entrySet() array
        int driveNum = position + 1;

        // need to import your Route class
        Route route = (Route) drive.getValue();

        // need to import your DateTime class
        DateTime startTime = new DateTime(route.getStart_time());
        DateTime endTime = new DateTime(route.getEnd_time());

        TextView driveNumList = (TextView) rowView.findViewById(R.id.Drive_Number_List);
        TextView driveDistList = (TextView) rowView.findViewById(R.id.Drive_Distance_List);
        TextView driveTimeList = (TextView) rowView.findViewById(R.id.Drive_Time_List);

        driveNumList.setText(String.valueOf(driveNum));
        if(sharedPref.getString("measurement", null).equals("imperial"))
            driveDistList.setText((double)Math.round((Float.parseFloat(route.getLen()) * 0.000621371192) * 100.0)/100.0+ " miles");
        else
            driveDistList.setText((double)Math.round((Float.parseFloat(route.getLen()) / 1000)*100.0)/100.0 + " km");

        driveTimeList.setText(((endTime.getMillis() - startTime.getMillis())/ 1000)/60 + " mins");

        return rowView;
    }

}
