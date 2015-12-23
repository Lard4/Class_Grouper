package com.dirinc.classgrouper;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.zip.Inflater;

import javax.xml.datatype.Duration;

public class CreateClass extends AppCompatActivity {

    private final Context context = this;
    private EditText className;
    private EditText studentOneName;
    private ImageButton addNewStudent;
    private FloatingActionButton createClass;
    private RelativeLayout relativeLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesclass1;
    private SharedPreferences.Editor prefsEdit;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";

    private HashMap <Integer, String> class1 = new HashMap<>();
    private int numberOfClasses = 0;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        relativeLayout = new RelativeLayout(this);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesclass1 = getSharedPreferences(SHARED_PREFS_CLASS1, 0);

        if (sharedPreferences.contains("numberOfClasses")) {
            numberOfClasses = sharedPreferences.getInt("numberOfClasses", 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        className = (EditText) findViewById(R.id.className);
        studentOneName = (EditText) findViewById(R.id.student_name);
        addNewStudent = (ImageButton) findViewById(R.id.prompt_student_name);
        createClass = (FloatingActionButton) findViewById(R.id.createClass);

        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                // TODO: Hide keyboard
                establishClass();
            }
        });

        addNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                addEditText();
            }
        });
    }

    public void addEditText() {
        id++;
        RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.scrollView_layout);
        EditText newStudent = new EditText((new ContextThemeWrapper(this, R.style.new_student)));

        newStudent.setId(id);
        newStudent.setTextColor(Color.parseColor("#FFFFFF"));
        newStudent.setWidth(
                (int) getResources().getDimension(R.dimen.newStudentWidth)
        );

        RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        EditLayoutParams.setMargins(
                (int) getResources().getDimension(R.dimen.newStudentMarginLeft), 0, 0, 0
        );

        if (id == 1) {
            EditLayoutParams.addRule(RelativeLayout.BELOW, R.id.student_name);
        } else {
            EditLayoutParams.addRule(RelativeLayout.BELOW, (id - 1));
        }

        newStudent.setLayoutParams(EditLayoutParams);

        (thisLayout).addView(newStudent);

        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
        sv.scrollTo(0, sv.getBottom());

        // Move cursor to new edit text
        class1.put(id, newStudent.getText().toString());
    }

    public void establishClass() {
        // No clue what this is
        className.setError(null);
        numberOfClasses++;

        String newClass = className.getText().toString();

        if (!className.getText().toString().equals("")) {
            Log.d("SHARED_PREFS", "Putting " + newClass);
            prefsEdit = sharedPreferences.edit();
            prefsEdit.putString(("class" + numberOfClasses), newClass);
            prefsEdit.putInt("numberOfClasses", numberOfClasses);
            prefsEdit.apply();

            establishStudents();
            switchActivities("MainActivity");
        } else {
            Toast.makeText(getApplicationContext(), "Name your class!", Toast.LENGTH_SHORT)
                    .show();
        }
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
        finish();
        switchActivities("MainActivity");
    }
}