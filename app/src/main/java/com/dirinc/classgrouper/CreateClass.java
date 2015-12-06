package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class CreateClass extends AppCompatActivity {

    private final Context context = this;
    private EditText className;
    private Button createClass;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesClassOne;
    private SharedPreferences.Editor prefsEdit;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";

    private HashMap <Integer, String> classOne = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesClassOne = getSharedPreferences(SHARED_PREFS_CLASS1,0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        className = (EditText) findViewById(R.id.className);
        createClass = (Button) findViewById(R.id.createClass);

        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Class Created", Snackbar.LENGTH_SHORT)
                        .show();

                establishClass();
                promptStudents();
                establishStudents();
                switchActivities("MainActivity");
            }
        });
    }

    public void establishClass() {
        // No clue what this is
        className.setError(null);

        String newClass = className.getText().toString();

        Log.d("SHARED_PREFS", "Putting " + newClass);
        prefsEdit = sharedPreferences.edit();
        prefsEdit.putString("class1", newClass);
        prefsEdit.apply();
    }

    public void promptStudents() {
        classOne.put(1, "Test");
        classOne.put(2, "Testing");
    }
    
    public void establishStudents() {
        // Save classOne HashMap of students to shared_prefs/class1.xml
        SharedPreferences.Editor editor = sharedPreferencesClassOne.edit();

        for (int s : classOne.keySet()) {
            editor.putString(String.valueOf(s), classOne.get(s));
        }
        editor.apply();
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "CreateClass":
                changeActivities = new Intent(this, CreateClass.class);
                Log.d("ActivitySwitch", "Switching to CreateClass Activity");
                startActivity(changeActivities);
                break;

            case "SettingsActivity":
                changeActivities = new Intent(this, SettingsActivity.class);
                Log.d("ActivitySwitch", "Switching to Settings Activity");
                startActivity(changeActivities);
                break;
        }
    }
}