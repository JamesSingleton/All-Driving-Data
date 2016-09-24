package com.example.jamessingleton.chffrapi;

/**
 * Created by James Singleton on 9/8/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Drive;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import org.joda.time.DateTime;

import java.util.List;

public class DriveURLAdapter extends ArrayAdapter<Drive> {

    public static String DriveURL;
    public static long driveSeconds;
    public static DateTime startTime;
    public static DateTime endTime;
    private final Activity context;
    // you may need to change List to something else (whatever is returned from drives.entrySet())
    private final List<Drive> drives;
    SharedPreferences sharedPref;
    // may also need to change List here (above comment)
    public DriveURLAdapter(Activity context, List<Drive> drives) {
        super(context, R.layout.drive_url_list, drives);
        this.context = context;
        this.drives = drives;
        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.drive_url_list, null, true);

        Drive drive = this.drives.get(position);

        // position is the index of the drives.entrySet() array
        int driveNum = position + 1;

        // need to import your Route class
        Route route = drive.getRoute();

        // need to import your URL class
        DriveURL = route.getUrl();

        startTime = new DateTime(route.getStart_time());
        endTime = new DateTime(route.getEnd_time());
        driveSeconds = ((endTime.getMillis() - startTime.getMillis())/ 1000);
        System.out.println(driveSeconds);

        TextView spinnerItems = (TextView) rowView.findViewById(R.id.Spinner_Items);
        spinnerItems.setText(String.valueOf(driveNum));
        //driveURLList.setText(DriveURL);

        return rowView;
    }


}
