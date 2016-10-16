package com.example.jamessingleton.chffrapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFrag = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        //mapFrag.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Criteria criteria = new Criteria();
            LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            double lat =  location.getLatitude();
            double lng = location.getLongitude();
            LatLng coordinate = new LatLng(lat, lng);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 13);
            mMap.animateCamera(yourLocation);
            lineOptions = new PolylineOptions()
                    .add(new LatLng(33.927085040000001, -118.39129683))
                    .add(new LatLng(33.927085969382027, -118.39129820011991))
                    .add(new LatLng(33.927081024586677, -118.39130352185116))
                    .add(new LatLng(33.927077084498386, -118.39130986277655))
                    .add(new LatLng(33.92707405365519, -118.39131496827862))
                    .add(new LatLng(33.927066722586623, -118.39131750469446))
                    .add(new LatLng(33.927068689880947, -118.39131823397425))
                    .add(new LatLng(33.927068030419839, -118.39131796208994))
                    .add(new LatLng(33.927065982966624, -118.39132090766913))
                    .add(new LatLng(33.927065258415212, -118.3913193654964))
                    .width(20)
                    .color(Color.RED);
            line = mMap.addPolyline(lineOptions);
            drives = APIRequestsUtil.getRoutes();
            List<Drive> list = new ArrayList<Drive>();
            final List<String> URLs = new ArrayList<String>();
            for (Map.Entry drive : drives.entrySet()) {
                Drive d = new Drive(drive.getKey().toString(), (Route) drive.getValue());
                URLs.add(d.getRoute().getUrl()+"/route.coords");
                list.add(d);
            }


            for (int x = 0; x < list.size(); x++) {
                System.out.println(URLs);
            }
//            RouteCoord routeCoord = (RouteCoord) drive.getValue();
//            routeLat = routeCoord.getLat();
//            double routeLatDouble = Double.parseDouble(routeLat);
//            routeLng = routeCoord.getLng();
//            double routeLngDouble = Double.parseDouble(routeLng);
//
//            for(list<500)
//            {
//                lineOptions.add(new LatLng(routeLatDouble, routeLngDouble));
//            }


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

            mapFrag.getMapAsync(this);
        }
        else {
        }
    }

}