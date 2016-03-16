package com.dirinc.classgrouper.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dirinc.classgrouper.Adapter.CardAdapter;
import com.dirinc.classgrouper.Adapter.ListAdapter;
import com.dirinc.classgrouper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateClass extends AppCompatActivity {

    private EditText className;
    private EditText studentOneName;

    private SharedPreferences sharedPreferences;
    private SharedPreferences classPrefs;

    RecyclerView mRecyclerView;
    ListAdapter mAdapter;
    List<String> mDataSet = new ArrayList<>();

    private static final String SHARED_PREFS = "shared_preferences";

    private HashMap<Integer, String> classMap = new HashMap<>();

    private ImageButton createClass;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        classPrefs = getSharedPreferences("class" + classNumber(), 0);

        initRecycler();

        createClass = (ImageButton) findViewById(R.id.createClass);
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (/*TODO checkAllStudentsValidity()*/ true) {
                    establishClass();
                } else {
                    showAlertDialog("Oops!", "One of your students is nameless!", "OK");
                }
            }
        });
    }

    public void initRecycler() {
        mDataSet.add(0, getResources().getString(R.string.prompt_student_name));
        mDataSet.add(1, getResources().getString(R.string.prompt_new_student_name));

        mRecyclerView = (RecyclerView) findViewById(R.id.create_class_recycler);
        mAdapter = new ListAdapter(mDataSet, classPrefs);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

    }

    public int classNumber() {
        return (sharedPreferences.getInt("numberOfClasses", 0));
    }

    public void showAlertDialog(String title, String message, String positiveButton) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, null)
                .show();
    }

    public void establishClass() {
        className.setError(null);

        String newClass = className.getText().toString();

        if (!className.getText().toString().equals("")) {
            Log.d("SHARED_PREFS", "Putting " + newClass);
            SharedPreferences classPrefs = getSharedPreferences("class" + classNumber(), 0);
            SharedPreferences.Editor classPrefsEdit = classPrefs.edit();
            classPrefsEdit.putString("title", newClass);
            classPrefsEdit.apply();

            SharedPreferences.Editor genPrefsEdit = sharedPreferences.edit();
            genPrefsEdit.putInt("numberOfClasses", classNumber() + 1);
            genPrefsEdit.apply();

            switchActivities("Main");
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Name your class!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "CreateClass":
                changeActivities = new Intent(this, CreateClass.class);
                startActivity(changeActivities);
                break;

            case "Settings":
                changeActivities = new Intent(this, Settings.class);
                startActivity(changeActivities);
                break;

            case "Main":
                changeActivities = new Intent(this, Main.class);
                startActivity(changeActivities);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        switchActivities("Main");
    }
}