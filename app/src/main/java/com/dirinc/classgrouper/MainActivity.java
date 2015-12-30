package com.dirinc.classgrouper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesclass1;
    private SharedPreferences sharedPreferencesclass2;
    private SharedPreferences sharedPreferencesclass3;
    private SharedPreferences sharedPreferencesclass4;
    private SharedPreferences sharedPreferencesclass5;
    private SharedPreferences sharedPreferencesclass6;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;
    private View view;

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
        view = findViewById(android.R.id.content);

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

        classcard2 = (CardView) findViewById(R.id.cardclass2);
        classcard2.setOnClickListener(new Listener());

        classcard3 = (CardView) findViewById(R.id.cardclass3);
        classcard3.setOnClickListener(new Listener());

        classcard4 = (CardView) findViewById(R.id.cardclass4);
        classcard4.setOnClickListener(new Listener());

        classcard5 = (CardView) findViewById(R.id.cardclass5);
        classcard5.setOnClickListener(new Listener());

        classcard6 = (CardView) findViewById(R.id.cardclass6);
        classcard6.setOnClickListener(new Listener());

        Toolbar class1Toolbar = (Toolbar) findViewById(R.id.class1_toolbar);
        Toolbar class2Toolbar = (Toolbar) findViewById(R.id.class2_toolbar);
        Toolbar class3Toolbar = (Toolbar) findViewById(R.id.class3_toolbar);
        Toolbar class4Toolbar = (Toolbar) findViewById(R.id.class4_toolbar);
        Toolbar class5Toolbar = (Toolbar) findViewById(R.id.class5_toolbar);
        Toolbar class6Toolbar = (Toolbar) findViewById(R.id.class6_toolbar);

        if ((class1Toolbar != null) && (class2Toolbar != null) && (class3Toolbar != null)
                && (class4Toolbar != null) && (class5Toolbar != null) && (class6Toolbar != null)) {
            class1Toolbar.inflateMenu(R.menu.menu_delete);
            class2Toolbar.inflateMenu(R.menu.menu_delete);
            class3Toolbar.inflateMenu(R.menu.menu_delete);
            class4Toolbar.inflateMenu(R.menu.menu_delete);
            class5Toolbar.inflateMenu(R.menu.menu_delete);
            class6Toolbar.inflateMenu(R.menu.menu_delete);

            class1Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 1);
                    return true;
                }
            });
            class2Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 2);
                    return true;
                }
            });
            class3Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 3);
                    return true;
                }
            });
            class4Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 4);
                    return true;
                }
            });
            class5Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 5);
                    return true;
                }
            });
            class6Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                            "Are you sure you want to continue", "Delete", "Cancel", 6);
                    return true;
                }
            });
        }

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

    @SuppressLint("CommitPrefEdits") // COMMIT IT NOW!!!
    public void deleteClass(int whichClass) {
        File dir = new File(getApplicationContext().getFilesDir().getParent() + "/shared_prefs/");
        editor = sharedPreferences.edit();
        editor.remove("class" + whichClass);
        int newNumberOfClasses = (getNumberOfClasses() - 1);
        editor.remove("numberOfClasses");
        editor.putInt("numberOfClasses", newNumberOfClasses);
        editor.commit();

        switch (whichClass) {
            case 1:
                editor = sharedPreferencesclass1.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class1.xml").delete();
                break;

            case 2:
                editor = sharedPreferencesclass2.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class2.xml").delete();
                break;

            case 3:
                editor = sharedPreferencesclass3.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class3.xml").delete();
                break;

            case 4:
                editor = sharedPreferencesclass4.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class4.xml").delete();
                break;

            case 5:
                editor = sharedPreferencesclass5.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class5.xml").delete();
                break;

            case 6:
                editor = sharedPreferencesclass6.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class6.xml").delete();
                break;
        }
        createCards();
    }

    public void createCards() {
        if (sharedPreferences.contains("class1")) {
            getRoster(1); // effectively update student count
            classTitle1.setText(
                    getClassName(1)
            );
            classStudentCount1.setText(
                    (student) + "  STUDENTS"
            );
        } else {
            classTitle1.setText(
                    "No Classes!"
            );
            classStudentCount1.setText(
                    "CREATE A CLASS TO GET STARTED"
            );
        }

        // CLASS 2
        if (sharedPreferences.contains("class2")) {
            getRoster(2);
            classTitle2.setText(
                    getClassName(2)
            );
            classStudentCount2.setText(
                    (student) + "  STUDENTS"
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
                    (student) + "  STUDENTS"
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
                    (student) + "  STUDENTS"
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
                    (student) + "  STUDENTS"
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
                    (student) + "  STUDENTS"
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
                                String positiveButton, String negativeButton, final int nClass) {
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
                        deleteClass(nClass);
                        Snackbar.make(view, "Class " + nClass + " deleted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
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

    class Listener implements View.OnClickListener {

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
    }
}