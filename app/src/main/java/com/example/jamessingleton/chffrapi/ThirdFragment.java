package com.example.jamessingleton.chffrapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Drive;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.Route;
import com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data.RouteCoord;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by James Singleton on 8/7/2016.
 * Google Maps
 */

public class ThirdFragment extends Fragment implements OnMapReadyCallback {
    View myView;
    private GoogleMap mMap;
    MapFragment mapFrag;
    private Polyline line;
    private PolylineOptions lineOptions;
    public static String routeLat;
    public static String routeLng;
    Map<String, Route> drives;
    List<RouteCoord[]> routeCoords;
    Double startingLat;
    Double startingLong;
    private ProgressDialog progress;






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFrag = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        progress = new ProgressDialog(getActivity());
        progress.setTitle("Please Wait");
        progress.setMessage("Retrieving Routes Information");

        //mapFrag.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            new routesSyncTask().execute();
            
        } else {
            final AlertDialog alertDialogGPS = new AlertDialog.Builder(getActivity()).create();

            alertDialogGPS.setTitle("Info");
            alertDialogGPS.setMessage("Looks like you have not given GPS permissions. Please give GPS permissions and return back to the app.");
            alertDialogGPS.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialogGPS.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent intentSettings = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                    startActivity(intentSettings);
                    alertDialogGPS.dismiss();
                }

            });

            alertDialogGPS.show();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //mapFrag.getMapAsync(this);
        }
        else {
        }
    }

    private static LatLng calculateCentroid(List<RouteCoord[]> geoCoordinates){
        int count = 0;
        double avgLat = -1;
        double avgLng = -1;
        double totalLat = -1;
        double totalLng = -1;
        for(RouteCoord[] routeCoords : geoCoordinates){
            for(RouteCoord routeCoord : routeCoords){
                count++;
                totalLat += Double.parseDouble(routeCoord.getLat());
                totalLng += Double.parseDouble(routeCoord.getLng());
            }
        }
        avgLat = totalLat / count;
        avgLng = totalLng / count;


        return new LatLng(avgLat, avgLng);
    }

    private class routesSyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            routeCoords = APIRequestsUtil.getRoutesCoords();
            return "";
        }

        @Override
        protected void onPostExecute(String types) {
            progress.dismiss();

            if(routeCoords !=null) {
                PolylineOptions options = null;
                for (RouteCoord[] routeCoordArray : routeCoords) {
                    options = new PolylineOptions();
                    for (RouteCoord rCoord : routeCoordArray) {
                        Double lat = Double.parseDouble(rCoord.getLat());
                        Double lng = Double.parseDouble(rCoord.getLng());
                        options.add(new LatLng(lat, lng));
                        System.out.println("Lat: " + rCoord.getLat() + "\n" + "Long: " + rCoord.getLng());
                    }
                    options.width(10);
                    options.color(Color.RED);

                    mMap.addPolyline(options);

                }
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(calculateCentroid(routeCoords),10);
                mMap.animateCamera(yourLocation);

            }
        }
    }

}