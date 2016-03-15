package com.dirinc.classgrouper.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dirinc.classgrouper.R;

import java.util.HashMap;

public class CreateClass extends AppCompatActivity {

    private EditText className;
    private EditText studentOneName;

    private SharedPreferences sharedPreferences;
    private SharedPreferences classPrefs;

    private static final String SHARED_PREFS = "shared_preferences";

    private HashMap<Integer, String> classMap = new HashMap<>();

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

        className = (EditText) findViewById(R.id.className);
        studentOneName = (EditText) findViewById(R.id.student_name);
        ImageButton createClass = (ImageButton) findViewById(R.id.createClass);
        Button addNewStudent = (Button) findViewById(R.id.add_student_create);

        studentOneName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                classMap.put(id, String.valueOf(studentOneName.getText()));
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
                if (/*TODO checkAllStudentsValidity()*/ true) {
                    establishClass();
                } else {
                    showAlertDialog("Oops!", "One of your students is nameless!", "OK");
                }
            }
        });
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

    public void addEditText() { //TODO: RecyclerView pt 3... Suicide is a viable option instead jklol
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
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                classMap.put(id, String.valueOf(newStudent.getText()));
            }
        });

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        sv.scrollTo(0, sv.getBottom());
        newStudent.requestFocus();
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

            establishStudents();
            switchActivities("Main");
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Name your class!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void establishStudents() {
        SharedPreferences.Editor editor = classPrefs.edit();
        for (int x = 0; x <= classMap.size(); x++) {
            editor.putString(String.valueOf(x), classMap.get(x));
        }
        editor.apply();
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