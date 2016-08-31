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


/**
 * Created by James Singleton on 8/13/2016.
 */

public class WeeklyDrives extends Fragment
{
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.weekly_drives, container, false);
        TableLayout tl;
        tl = (TableLayout) myView.findViewById(R.id.fragment1_tlayout);


        for (int i = 0; i < 30; i++) {

            TableRow tr = new TableRow(getActivity());

            tr.setId(i);
            tr.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //TEXTVIEWS********
            TextView tv1 = new TextView(getActivity());
            tv1.setText("Drive Number");
            tv1.setId(i);
            tv1.setTextColor(Color.RED);
            tv1.setTextSize(20);
            tv1.setPadding(5, 5, 5, 5);
            tr.addView(tv1);

            TextView tv2 = new TextView(getActivity());

            tv2.setText("Drive: "+ i);
            tv2.setId(i+i);
            tv2.setTextColor(Color.RED);
            tv1.setTextSize(20);
            tv2.setPadding(5, 5, 5, 5);
            tr.addView(tv2);

            tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }
        return myView;
    }
}
