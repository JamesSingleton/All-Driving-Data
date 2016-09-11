package com.example.jamessingleton.chffrapi;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.fasterxml.jackson.databind.deser.std.MapEntryDeserializer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter
{

    private LinkedList<String> dates = new LinkedList<>();
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd--HH-mm-ss");
    private DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MM/dd");

    public DayAxisValueFormatter( Map<String, Route> drives) {
        populateDatesList(drives);
    }

    private void populateDatesList(Map<String, Route> drives) {
            dates.add("");
        for (Map.Entry drive : drives.entrySet()){
            DateTime jodatime = dtf.parseDateTime(drive.getKey().toString());
            dates.add(dtfOut.print(jodatime));
        }
            dates.add("");

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        System.out.println("Val " + value);
        return dates.get((int) value);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
