package com.dirinc.classgrouper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap <Integer, String> class1 = new HashMap<>();

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesclass1;
    private SharedPreferences sharedPreferencesclass2;
    private SharedPreferences sharedPreferencesclass3;
    private SharedPreferences sharedPreferencesclass4;
    private SharedPreferences sharedPreferencesclass5;
    private SharedPreferences sharedPreferencesclass6;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;

    private TextView classTitle1;
    private TextView classStudentCount1;
    private CardView classcard1;

    private TextView classTitle2;
    private TextView classStudentCount2;
    private CardView classcard2;
    private ImageView classColor2;

    private TextView classTitle3;
    private TextView classStudentCount3;
    private CardView classcard3;
    private ImageView classColor3;

    private TextView classTitle4;
    private TextView classStudentCount4;
    private CardView classcard4;
    private ImageView classColor4;

    private TextView classTitle5;
    private TextView classStudentCount5;
    private CardView classcard5;
    private ImageView classColor5;

    private TextView classTitle6;
    private TextView classStudentCount6;
    private CardView classcard6;
    private ImageView classColor6;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";
    private static final String SHARED_PREFS_CLASS2 = "class2";
    private static final String SHARED_PREFS_CLASS3 = "class3";
    private static final String SHARED_PREFS_CLASS4 = "class4";
    private static final String SHARED_PREFS_CLASS5 = "class5";
    private static final String SHARED_PREFS_CLASS6 = "class6";

    private int student = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesclass1 = getSharedPreferences(SHARED_PREFS_CLASS1, 0);
        sharedPreferencesclass2 = getSharedPreferences(SHARED_PREFS_CLASS2, 0);
        sharedPreferencesclass3 = getSharedPreferences(SHARED_PREFS_CLASS3, 0);
        sharedPreferencesclass4 = getSharedPreferences(SHARED_PREFS_CLASS4, 0);
        sharedPreferencesclass5 = getSharedPreferences(SHARED_PREFS_CLASS5, 0);
        sharedPreferencesclass6 = getSharedPreferences(SHARED_PREFS_CLASS6, 0);

        // Init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init drawer
        new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .build();
        setupDrawer();

        classTitle1 = (TextView) findViewById(R.id.class1_title);
        classStudentCount1 = (TextView) findViewById(R.id.class1_student_count);

        classTitle2 = (TextView) findViewById(R.id.class2_title);
        classStudentCount2 = (TextView) findViewById(R.id.class2_student_count);
        classColor2 = (ImageView) findViewById(R.id.class2_color);

        classTitle3 = (TextView) findViewById(R.id.class3_title);
        classStudentCount3 = (TextView) findViewById(R.id.class3_student_count);
        classColor3 = (ImageView) findViewById(R.id.class3_color);

        classTitle4 = (TextView) findViewById(R.id.class4_title);
        classStudentCount4 = (TextView) findViewById(R.id.class4_student_count);
        classColor4 = (ImageView) findViewById(R.id.class4_color);

        classTitle5 = (TextView) findViewById(R.id.class5_title);
        classStudentCount5 = (TextView) findViewById(R.id.class5_student_count);
        classColor5 = (ImageView) findViewById(R.id.class5_color);

        classTitle6 = (TextView) findViewById(R.id.class6_title);
        classStudentCount6 = (TextView) findViewById(R.id.class6_student_count);
        classColor6 = (ImageView) findViewById(R.id.class6_color);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities("CreateClass");
            }
        });

        classcard1 = (CardView) findViewById(R.id.cardclass1);
        classcard1.setOnClickListener(new Listener());
        classcard1.setOnLongClickListener(new Listener());

        classcard2 = (CardView) findViewById(R.id.cardclass2);
        classcard2.setOnClickListener(new Listener());
        classcard2.setOnLongClickListener(new Listener());

        classcard3 = (CardView) findViewById(R.id.cardclass3);
        classcard3.setOnClickListener(new Listener());
        classcard3.setOnLongClickListener(new Listener());

        classcard4 = (CardView) findViewById(R.id.cardclass4);
        classcard4.setOnClickListener(new Listener());
        classcard4.setOnLongClickListener(new Listener());

        classcard5 = (CardView) findViewById(R.id.cardclass5);
        classcard5.setOnClickListener(new Listener());
        classcard5.setOnLongClickListener(new Listener());

        classcard6 = (CardView) findViewById(R.id.cardclass6);
        classcard6.setOnClickListener(new Listener());
        classcard6.setOnLongClickListener(new Listener());

        boolean userIsNew = sharedPreferences.getBoolean("userIsNew", false);

        // TODO: Tutorial

        editor = sharedPreferences.edit();
        editor.putBoolean("userIsNew", false);
        editor.apply();

        getRoster(1); // Will update student count!
        createCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            switchActivities("SettingsActivity");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupDrawer() {
        PrimaryDrawerItem homeDrawer = new PrimaryDrawerItem()
                .withName("Home")
                .withIcon(GoogleMaterial.Icon.gmd_home);

        PrimaryDrawerItem classesDrawer = new PrimaryDrawerItem()
                .withName("All Classes")
                .withTextColor(Color.parseColor("#9E9E9E"));

        SecondaryDrawerItem class1Drawer = new SecondaryDrawerItem()
                .withName(getClassName(1))
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class2Drawer = new SecondaryDrawerItem()
                .withName(getClassName(2))
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class3Drawer = new SecondaryDrawerItem()
                .withName(getClassName(3))
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class4Drawer = new SecondaryDrawerItem()
                .withName(getClassName(4))
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class5Drawer = new SecondaryDrawerItem()
                .withName(getClassName(5))
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class6Drawer = new SecondaryDrawerItem()
                .withName(getClassName(6))
                .withIcon(R.drawable.class_ic_png);

        // TODO: Class 2

        PrimaryDrawerItem settingsDrawer = new PrimaryDrawerItem()
                .withName("Settings")
                .withIcon(GoogleMaterial.Icon.gmd_settings);

        DividerDrawerItem dividerDrawer = new DividerDrawerItem();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.classroom)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        homeDrawer,
                        dividerDrawer,
                        classesDrawer,
                        class1Drawer,
                        dividerDrawer,
                        settingsDrawer
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.d("DRAWER", "Clicked: " + position);
                        switch (position) {
                            // Home
                            case 0:
                                break;

                            // Settings
                            case 6:
                                switchActivities("SettingsActivity");
                                break;
                        }
                        return false;
                    }
                })
                .build();

        switch (getNumberOfClasses()) {
            case 2:
                result.addItemAtPosition(class2Drawer, 5);
                break;

            case 3:
                result.addItemAtPosition(class2Drawer, 5);
                result.addItemAtPosition(class3Drawer, 6);
                break;

            case 4:
                result.addItemAtPosition(class2Drawer, 5);
                result.addItemAtPosition(class3Drawer, 6);
                result.addItemAtPosition(class4Drawer, 7);
                break;

            case 5:
                result.addItemAtPosition(class2Drawer, 5);
                result.addItemAtPosition(class3Drawer, 6);
                result.addItemAtPosition(class4Drawer, 7);
                result.addItemAtPosition(class5Drawer, 8);
                break;

            case 6:
                result.addItemAtPosition(class2Drawer, 5);
                result.addItemAtPosition(class3Drawer, 6);
                result.addItemAtPosition(class4Drawer, 7);
                result.addItemAtPosition(class5Drawer, 8);
                result.addItemAtPosition(class6Drawer, 9);
                break;
        }
    }

    public void createCards() {
        if (sharedPreferences.contains("class1")) {
            getRoster(1); // effectively update student count
            classTitle1.setText(
                    getClassName(1)
            );
            classStudentCount1.setText(
                    (student - 1) + "  STUDENTS"
            );
        }

        // CLASS 2
        if (sharedPreferences.contains("class2")) {
            getRoster(2);
            classTitle2.setText(
                    getClassName(2)
            );
            classStudentCount2.setText(
                    (student - 1) + "  STUDENTS"
            );
        } else {
            if (classcard2 != null) classcard2.setVisibility(View.GONE);
            if (classTitle2 != null) classTitle2.setVisibility(View.GONE);
            if (classStudentCount2 != null) classStudentCount2.setVisibility(View.GONE);
            if (classColor2 != null) classColor2.setVisibility(View.GONE);
        }

        // CLASS 3
        if (sharedPreferences.contains("class3")) {
            getRoster(3);
            classTitle3.setText(
                    getClassName(3)
            );
            classStudentCount3.setText(
                    (student - 1) + "  STUDENTS"
            );
        } else {
            if (classcard3 != null) classcard3.setVisibility(View.GONE);
            if (classTitle3 != null) classTitle3.setVisibility(View.GONE);
            if (classStudentCount3 != null) classStudentCount3.setVisibility(View.GONE);
            if (classColor3 != null) classColor3.setVisibility(View.GONE);
        }

        // CLASS 4
        if (sharedPreferences.contains("class4")) {
            getRoster(4);
            classTitle4.setText(
                    getClassName(4)
            );
            classStudentCount4.setText(
                    (student - 1) + "  STUDENTS"
            );
        } else {
            if (classcard4 != null) classcard4.setVisibility(View.GONE);
            if (classTitle4 != null) classTitle4.setVisibility(View.GONE);
            if (classStudentCount4 != null) classStudentCount4.setVisibility(View.GONE);
            if (classColor4 != null) classColor4.setVisibility(View.GONE);
        }

        // CLASS 5
        if (sharedPreferences.contains("class5")) {
            getRoster(5);
            classTitle5.setText(
                    getClassName(5)
            );
            classStudentCount5.setText(
                    (student - 1) + "  STUDENTS"
            );
        } else {
            if (classcard5 != null) classcard5.setVisibility(View.GONE);
            if (classTitle5 != null) classTitle5.setVisibility(View.GONE);
            if (classStudentCount5 != null) classStudentCount5.setVisibility(View.GONE);
            if (classColor5 != null) classColor5.setVisibility(View.GONE);
        }

        // CLASS 6
        if (sharedPreferences.contains("class6")) {
            getRoster(6);
            classTitle6.setText(
                    getClassName(6)
            );
            classStudentCount6.setText(
                    (student - 1) + "  STUDENTS"
            );
        } else {
            if (classcard6 != null) classcard6.setVisibility(View.INVISIBLE);
            if (classTitle6 != null) classTitle6.setVisibility(View.GONE);
            if (classStudentCount6 != null) classStudentCount6.setVisibility(View.GONE);
            if (classColor6 != null) classColor6.setVisibility(View.GONE);
        }
    }

    public String getClassName(int whichClass) {
        if (sharedPreferences.contains("class" + whichClass)) {
            return sharedPreferences.getString("class" + whichClass, "");
        }
        else {
            return "No Classes!";
        }
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "CreateClass":
                changeActivities = new Intent(this, CreateClass.class);
                Log.d("ActivitySwitch", "Switching to CreateClass Activity");
                finish();
                startActivity(changeActivities);
                break;

            case "SettingsActivity":
                changeActivities = new Intent(this, SettingsActivity.class);
                Log.d("ActivitySwitch", "Switching to Settings Activity");
                finish();
                startActivity(changeActivities);
                break;
        }
    }

    public void showAlertDialog(String title, String message,
                                String positiveButton, String negativeButton) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }

    public int getNumberOfClasses() {
        return sharedPreferences.getInt("numberOfClasses", 0);
    }

    public HashMap getRoster(int whichClass) {
        HashMap <Integer, String> thisClass = new HashMap<>();
        boolean hasStudents = true;
        String name;
        student = 1;

        // if class exists
        if (whichClass <= getNumberOfClasses()) {
            while (hasStudents) {
                switch (whichClass) {
                    case 1:
                        if (sharedPreferencesclass1.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass1.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 2:
                        if (sharedPreferencesclass2.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass2.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 3:
                        if (sharedPreferencesclass3.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass3.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 4:
                        if (sharedPreferencesclass4.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass4.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 5:
                        if (sharedPreferencesclass5.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass5.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 6:
                        if (sharedPreferencesclass6.contains(String.valueOf(student))) {
                            name = sharedPreferencesclass6.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;
                }

            }
        }
        return thisClass;
    }

    class Listener implements View.OnLongClickListener, View.OnClickListener {

        public void switchActivities(String newActivity, int nClass) {
            Intent changeActivities;

            switch (newActivity) {
                case "ClassRoster":
                    changeActivities = new Intent(getApplicationContext(), ClassRoster.class);
                    Log.d("ActivitySwitch", "Switching to ClassRoster Activity");
                    Bundle bundle = new Bundle();
                    bundle.putInt("huey", nClass);
                    changeActivities.putExtras(bundle);
                    finish();
                    startActivity(changeActivities);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardclass1:
                    switchActivities("ClassRoster", 1);
                    break;

                case R.id.cardclass2:
                    switchActivities("ClassRoster", 2);
                    break;

                case R.id.cardclass3:
                    switchActivities("ClassRoster", 3);
                    break;

                case R.id.cardclass4:
                    switchActivities("ClassRoster", 4);
                    break;

                case R.id.cardclass5:
                    switchActivities("ClassRoster", 5);
                    break;

                case R.id.cardclass6:
                    switchActivities("ClassRoster", 6);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View view) {
            switch (view.getId()) {
                case R.id.cardclass1:
                    return true;

                case R.id.cardclass2:
                    return true;

                case R.id.cardclass3:
                    return true;

                case R.id.cardclass4:
                    return true;

                case R.id.cardclass5:
                    return true;

                case R.id.cardclass6:
                    return true;

                default:
                    return false;
            }
        }
    }
}