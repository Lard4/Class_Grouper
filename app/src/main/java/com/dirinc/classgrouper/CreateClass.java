package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
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
    private SharedPreferences.Editor prefsEdit;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

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
            }
        });
    }

    public void establishClass() {
        // No clue what this is
        className.setError(null);

        String newClass = className.getText().toString();

        Log.d("SHARED_PREFS", "Putting " + newClass);
        prefsEdit = sharedPreferences.edit();
        prefsEdit.putString("Class", newClass);
        prefsEdit.apply();
    }

    public void promptStudents() {
        //persist
        HashMap<String, Integer> counters; //the hashmap you want to save
        SharedPreferences pref = this.getSharedPreferences("Your_Shared_Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        for (String s : counters.keySet()) {
            editor.putInteger(s, counters.get(s));
        }
        editor.commit();
    }
    
    public void establishStudents() {

    }
}