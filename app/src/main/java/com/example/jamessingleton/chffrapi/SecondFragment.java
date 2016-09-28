package com.example.jamessingleton.chffrapi;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.NothingSelectedSpinnerAdapter;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Drive;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;

import java.io.File;
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
    SeekBar mySeekBar;
    ProgressBar myProgress;
    int totalImages = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        APIRequestsUtil.setOnAPIResponseListener(this);
        imageView = (ImageView) myView.findViewById(R.id.driveImageView);
        mySeekBar = (SeekBar) myView.findViewById(R.id.seekBar);
        mySeekBar.setMax(1);
        myProgress = (ProgressBar) myView.findViewById(R.id.progress);
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
                List<Drive> list = new ArrayList<Drive>();

                for (Map.Entry drive : drives.entrySet()) {
                    Drive d = new Drive(drive.getKey().toString(), (Route) drive.getValue());
                    list.add(d);
                }

                // populate the ListView
                // may need to change "getActivity()" to something else
                // this constructor needs the "this" context of the activity
                DriveURLAdapter drivesURLAdapter = new DriveURLAdapter(getActivity(), list);
                final Spinner spinner = (Spinner) myView.findViewById(R.id.spinner);
                drivesURLAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(
                        new NothingSelectedSpinnerAdapter(
                                drivesURLAdapter,
                                R.layout.spinner_row_nothing_selected,
                                getActivity()));

                if (spinner.getTag() != null) {
                    spinner.setSelection((int) spinner.getTag());
                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

                        if (spinner.getTag() != null && pos != (int) spinner.getTag()) {
                            spinner.setTag(pos);
                            final String url = DriveURLAdapter.DriveURL;
                            int driveTime = (int) DriveURLAdapter.driveSeconds;

                            final List<String> URLs = new ArrayList<String>();
                            for (int x = 0; x < driveTime; x++) {
                                URLs.add(url + "/sec" + x + ".jpg");
                            }
                            downloadOnlyImages(URLs);


                            mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    Glide.with(getActivity())
                                            .load(URLs.get(progress))
                                            .listener(new RequestListener<String, GlideDrawable>() {
                                                @Override
                                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                    myProgress.setVisibility(View.GONE);
                                                    return false;
                                                }
                                            })
                                            .override(480,270)
                                            .fitCenter()
                                            .dontAnimate()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(imageView);

                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });

                        }
                        spinner.setTag(pos);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getActivity(), "Please select a drive to display.", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadOnlyImages(List<String> URLs){

        totalImages = 0;

        for(final String url: URLs){
            Glide.with(this)
                    .load(url)
                    .downloadOnly(new SimpleTarget<File>(480,270) {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            Log.i("glide", "image downloaded" + url);
                            mySeekBar.setMax(totalImages++);
                        }
                    });
            mySeekBar.setVisibility(View.VISIBLE);
            myProgress.setVisibility(View.VISIBLE);
        }


    }
}
