package com.dirinc.classgrouper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.HashMap;

public class CreateClass extends AppCompatActivity {

    private EditText className;
    private EditText studentOneName;
    private ImageButton createClass;
    private FloatingActionButton addNewStudent;
    private RelativeLayout relativeLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesclass1;
    private SharedPreferences sharedPreferencesclass2;
    private SharedPreferences sharedPreferencesclass3;
    private SharedPreferences sharedPreferencesclass4;
    private SharedPreferences sharedPreferencesclass5;
    private SharedPreferences sharedPreferencesclass6;
    private SharedPreferences.Editor prefsEdit;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";
    private static final String SHARED_PREFS_CLASS2 = "class2";
    private static final String SHARED_PREFS_CLASS3 = "class3";
    private static final String SHARED_PREFS_CLASS4 = "class4";
    private static final String SHARED_PREFS_CLASS5 = "class5";
    private static final String SHARED_PREFS_CLASS6 = "class6";


    private HashMap <Integer, String> class1 = new HashMap<>();
    private HashMap <Integer, String> class2 = new HashMap<>();
    private HashMap <Integer, String> class3 = new HashMap<>();
    private HashMap <Integer, String> class4 = new HashMap<>();
    private HashMap <Integer, String> class5 = new HashMap<>();
    private HashMap <Integer, String> class6 = new HashMap<>();

    private int numberOfClasses = 0;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        relativeLayout = new RelativeLayout(this);

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesclass1 = getSharedPreferences(SHARED_PREFS_CLASS1, 0);
        sharedPreferencesclass2 = getSharedPreferences(SHARED_PREFS_CLASS2, 0);
        sharedPreferencesclass3 = getSharedPreferences(SHARED_PREFS_CLASS3, 0);
        sharedPreferencesclass4 = getSharedPreferences(SHARED_PREFS_CLASS4, 0);
        sharedPreferencesclass5 = getSharedPreferences(SHARED_PREFS_CLASS5, 0);
        sharedPreferencesclass6 = getSharedPreferences(SHARED_PREFS_CLASS6, 0);


        if (sharedPreferences.contains("numberOfClasses")) {
            numberOfClasses = sharedPreferences.getInt("numberOfClasses", 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        className = (EditText) findViewById(R.id.className);
        studentOneName = (EditText) findViewById(R.id.student_name);
        createClass = (ImageButton) findViewById(R.id.createClass);
        addNewStudent = (FloatingActionButton) findViewById(R.id.prompt_student_name);

        studentOneName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                switch (getClassCount()) {
                    case 0:
                        class1.put(id, String.valueOf(studentOneName.getText()));
                        break;

                    case 1:
                        class2.put(id, String.valueOf(studentOneName.getText()));
                        break;

                    case 2:
                        class3.put(id, String.valueOf(studentOneName.getText()));
                        break;

                    case 3:
                        class4.put(id, String.valueOf(studentOneName.getText()));
                        break;

                    case 4:
                        class5.put(id, String.valueOf(studentOneName.getText()));
                        break;

                    case 5:
                        class6.put(id, String.valueOf(studentOneName.getText()));
                        break;
                }
            }
        });

        addNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                // TODO: Hide keyboard
                addEditText();
            }
        });

        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                establishClass();
            }
        });
    }

    public void addEditText() {
        id++;
        RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.scrollView_layout);
        final EditText newStudent = new EditText((new ContextThemeWrapper(this, R.style.new_student)));
        newStudent.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

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

        newStudent.setError(null);

        // Get student_card name
        newStudent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (getClassCount()) {
                    case 0:
                        class1.put(id, String.valueOf(newStudent.getText()));
                        break;

                    case 1:
                        class2.put(id, String.valueOf(newStudent.getText()));
                        break;

                    case 2:
                        class3.put(id, String.valueOf(newStudent.getText()));
                        break;

                    case 3:
                        class4.put(id, String.valueOf(newStudent.getText()));
                        break;

                    case 4:
                        class5.put(id, String.valueOf(newStudent.getText()));
                        break;

                    case 5:
                        class6.put(id, String.valueOf(newStudent.getText()));
                        break;
                }
            }
        });

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        sv.scrollTo(0, sv.getBottom());
        newStudent.requestFocus();
    }

    public void establishClass() {
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
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Name your class!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public int getClassCount() {
        return sharedPreferences.getInt("numberOfClasses", 0);
    }
    
    public void establishStudents() {
        SharedPreferences.Editor editor;
        switch (getClassCount()) {
            case 1:
                editor = sharedPreferencesclass1.edit();
                for (int x = 0; x <= class1.size(); x++) {
                    editor.putString(String.valueOf(x), class1.get(x));
                }
                editor.apply();
                break;

            case 2:
                editor = sharedPreferencesclass2.edit();
                for (int x = 0; x <= class2.size(); x++) {
                    editor.putString(String.valueOf(x), class2.get(x));
                }
                editor.apply();
                break;

            case 3:
                editor = sharedPreferencesclass3.edit();
                for (int x = 0; x <= class3.size(); x++) {
                    editor.putString(String.valueOf(x), class3.get(x));
                }
                editor.apply();
                break;

            case 4:
                editor = sharedPreferencesclass4.edit();
                for (int x = 0; x <= class4.size(); x++) {
                    editor.putString(String.valueOf(x), class4.get(x));
                }
                editor.apply();
                break;

            case 5:
                editor = sharedPreferencesclass5.edit();
                for (int x = 0; x <= class5.size(); x++) {
                    editor.putString(String.valueOf(x), class5.get(x));
                }
                editor.apply();
                break;

            case 6:
                editor = sharedPreferencesclass6.edit();
                for (int x = 0; x <= class6.size(); x++) {
                    editor.putString(String.valueOf(x), class6.get(x));
                }
                editor.apply();
                break;
        }
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