package com.dirinc.classgrouper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ClassRoster extends AppCompatActivity {
    private int classNumber;

    private HashMap<Integer, String> class1 = new HashMap<>();
    private HashMap<Integer, String> class2 = new HashMap<>();
    private HashMap<Integer, String> class3 = new HashMap<>();
    private HashMap<Integer, String> class4 = new HashMap<>();
    private HashMap<Integer, String> class5 = new HashMap<>();
    private HashMap<Integer, String> class6 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);

        Bundle bundle = getIntent().getExtras();
        classNumber = bundle.getInt("huey");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        loadClasses();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadClasses() {
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        try {
            if (pSharedPref != null) {
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    int key = Integer.parseInt(keysItr.next());
                    String value = (String) jsonObject.get(String.valueOf(key));
                    addToMap(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToMap(int key, String value) {
        switch (classNumber) {
            case 1:
                class1.put(key, value);
                break;

            case 2:
                class2.put(key, value);
                break;

            case 3:
                class3.put(key, value);
                break;

            case 4:
                class4.put(key, value);
                break;

            case 5:
                class5.put(key, value);
                break;

            case 6:
                class6.put(key, value);
                break;
        }
    }
}