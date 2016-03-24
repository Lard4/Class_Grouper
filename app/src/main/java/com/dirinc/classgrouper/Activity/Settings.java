package com.dirinc.classgrouper.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.dirinc.classgrouper.Util.NavigationDrawer;
import com.dirinc.classgrouper.R;

public class Settings extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEdit;

    private Switch highContrast;
    private Switch fullNames;

    private NavigationDrawer drawer;

    private static final String SHARED_PREFS = "shared_preferences";
    public static final String HCT = "high_contrast_text_enabled";
    public static final String FN = "full_names_enabled";

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.settings_high_contrast_text_switch:
                prefsEdit = sharedPreferences.edit();
                prefsEdit.putBoolean(HCT, isChecked);
                prefsEdit.apply();
                break;

            case R.id.settings_full_names_switch:
                prefsEdit = sharedPreferences.edit();
                prefsEdit.putBoolean(FN, isChecked);
                prefsEdit.apply();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_high_contrast_text:
                if (highContrast.isChecked()) {
                    setSwitch(highContrast, 0, HCT);
                } else {
                    setSwitch(highContrast, 1, HCT);
                }
                break;

            case R.id.settings_full_names:
                if (fullNames.isChecked()) {
                    setSwitch(fullNames, 0, FN);
                } else {
                    setSwitch(fullNames, 1, FN);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //TODO: Get navigation drawer

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        setActionBar(actionBar);

        highContrast = (Switch) findViewById(R.id.settings_high_contrast_text_switch);
        highContrast.setOnCheckedChangeListener(this);
        RelativeLayout highContrastLayout = (RelativeLayout) findViewById(R.id.settings_high_contrast_text);
        highContrastLayout.setOnClickListener(this);
        setSwitch(highContrast, 3, HCT);

        fullNames = (Switch) findViewById(R.id.settings_full_names_switch);
        fullNames.setOnCheckedChangeListener(this);
        RelativeLayout fullNamesLayout = (RelativeLayout) findViewById(R.id.settings_full_names);
        fullNamesLayout.setOnClickListener(this);
        setSwitch(fullNames, 3, FN);

        Main main = new Main();
        drawer = main.getDrawer(this, toolbar);
        //drawer.removeHamburgerAnimation();
    }

    public void setSwitch(Switch mSwitch, int key, String name) {
        boolean checked;

        if (key == 0) { //Override to off
            checked = false;
            prefsEdit = sharedPreferences.edit();
            prefsEdit.putBoolean(name, false);
            prefsEdit.apply();
        } else if (key == 1) { //Override to on
            checked = true;
            prefsEdit = sharedPreferences.edit();
            prefsEdit.putBoolean(name, true);
            prefsEdit.apply();
        } else { //Differ to shared prefs
            checked = sharedPreferences.getBoolean(name, false);
        }

        mSwitch.setChecked(checked);
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
            case "Main":
                changeActivities = new Intent(this, Main.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        switchActivities("Main");
    }
}