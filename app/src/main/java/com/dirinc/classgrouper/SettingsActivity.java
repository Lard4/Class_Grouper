package com.dirinc.classgrouper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEdit;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case android.R.id.home:
                this.finish();
                switchActivities("MainActivity");
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        finish();
        switchActivities("MainActivity");
    }
}