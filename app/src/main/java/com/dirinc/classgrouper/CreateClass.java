package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

public class CreateClass extends AppCompatActivity {

    private final Context context = this;
    private EditText className;
    private EditText studentOneName;
    private EditText addNewStudent;
    private EditText studentNamex;
    private Button createClass;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesclass1;
    private SharedPreferences.Editor prefsEdit;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";

    private HashMap <Integer, String> class1 = new HashMap<>();
    private int numberOfClasses = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesclass1 = getSharedPreferences(SHARED_PREFS_CLASS1, 0);

        if (sharedPreferences.contains("numberOfClasses")) {
            numberOfClasses = sharedPreferences.getInt("numberOfClasses", 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        className = (EditText) findViewById(R.id.className);
        studentOneName = (EditText) findViewById(R.id.student_name);
        addNewStudent = (EditText) findViewById(R.id.prompt_student_name);
        createClass = (Button) findViewById(R.id.createClass);

        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Hide keyboard
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
        numberOfClasses++;

        String newClass = className.getText().toString();

        Log.d("SHARED_PREFS", "Putting " + newClass);
        prefsEdit = sharedPreferences.edit();
        prefsEdit.putString(("class" + numberOfClasses), newClass);
        prefsEdit.putInt("numberOfClasses", numberOfClasses);
        prefsEdit.apply();
    }

    public void promptStudents() {
        class1.put(1, studentOneName.getText().toString());
    }
    
    public void establishStudents() {
        // Save class1 HashMap of students to shared_prefs/class1.xml
        SharedPreferences.Editor editor = sharedPreferencesclass1.edit();

        for (int s : class1.keySet()) {
            editor.putString(String.valueOf(s), class1.get(s));
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

            case "MainActivity":
                changeActivities = new Intent(this, MainActivity.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switchActivities("MainActivity");
    }
}