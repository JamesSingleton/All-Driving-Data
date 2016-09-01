package com.example.jamessingleton.chffrapi;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;





public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //.setAction("Action", null).show();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                }
            });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_drive_layout));
        navigationView.setCheckedItem(R.id.nav_drive_layout);
        navigationView.getMenu().performIdentifierAction(R.id.nav_drive_layout, 0);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentManager fragmentSettingsManager = getFragmentManager();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentSettingsManager.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
            setTitle(R.string.action_settings);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
            setTitle(R.string.speed_graph);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SecondFragment()).commit();
            setTitle(R.string.drive_player);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_third_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ThirdFragment()).commit();
            setTitle(R.string.google_maps);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);

        }else if (id == R.id.nav_drive_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new WeeklyDrives()).commit();
            setTitle(R.string.weekly_drives);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);

        }else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Go Check Out All Driving Data in the Play Store!");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
