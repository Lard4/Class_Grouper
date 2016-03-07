package com.dirinc.classgrouper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements NavigationDrawerCallbacks {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEdit;

    private NavigationDrawerFragment navigationDrawerFragment;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        setActionBar(actionBar);

        Switch highContrast = (Switch) findViewById(R.id.HCT_switch);
        setSwitch(highContrast);

        createDrawer(toolbar);
    }

    public void createDrawer(Toolbar toolbar) {
        navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        // Set up the drawer.
        navigationDrawerFragment.setup(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer), toolbar);
        /* Populate the navigation drawer
        navigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com",
                BitmapFactory.decodeResource(getResources(), R.drawable.avatar)); */
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Update the main content by replacing fragments
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            *//* Only show items in the action bar relevant to this screen
             * if the drawer is not showing. Otherwise, let the drawer
             * decide what to show in the action bar.
             *//*
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        switch (item.getItemId()) {
            *//*case android.R.id.home:
                this.finish();
                switchActivities("MainActivity");
                return true;
                *//*
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void setSwitch(Switch highContast) {
        boolean HCTchecked = sharedPreferences.getBoolean("HCTchecked", false);
        highContast.setChecked(HCTchecked);

        // Attach a listener to check for changes in state
        highContast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    prefsEdit = sharedPreferences.edit();
                    prefsEdit.putBoolean("HCTchecked", true);
                } else {
                    prefsEdit = sharedPreferences.edit();
                    prefsEdit.putBoolean("HCTchecked", false);
                }
                prefsEdit.apply();
            }
        });
    }

    public void setActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            // Show the back button in the action bar.
            //actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "MainActivity":
                changeActivities = new Intent(this, MainActivity.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment.isDrawerOpen()) {
            navigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
            finish();
            switchActivities("MainActivity");
        }
    }
}