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

    private SharedPreferences sharedPreferences, sharedPreferencesClass1, sharedPreferencesClass2,
            sharedPreferencesClass3, sharedPreferencesClass4, sharedPreferencesClass5, sharedPreferencesClass6;
    private SharedPreferences.Editor editor;

    private View view;
    private Toolbar toolbar;
    private TextView classTitle1, classStudentCount1, classTitle2, classStudentCount2, classTitle3,
            classStudentCount3, classTitle4, classStudentCount4, classTitle5, classStudentCount5,
            classTitle6, classStudentCount6;
    private CardView classcard1, classcard2, classcard3, classcard4, classcard5, classcard6;
    private ImageView classColor2, classColor3, classColor4, classColor5, classColor6;

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
        view = findViewById(R.id.main_layout);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesClass1 = getSharedPreferences(SHARED_PREFS_CLASS1, 0);
        sharedPreferencesClass2 = getSharedPreferences(SHARED_PREFS_CLASS2, 0);
        sharedPreferencesClass3 = getSharedPreferences(SHARED_PREFS_CLASS3, 0);
        sharedPreferencesClass4 = getSharedPreferences(SHARED_PREFS_CLASS4, 0);
        sharedPreferencesClass5 = getSharedPreferences(SHARED_PREFS_CLASS5, 0);
        sharedPreferencesClass6 = getSharedPreferences(SHARED_PREFS_CLASS6, 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .build();
        setupDrawer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities("CreateClass", 0);
            }
        });

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

        classcard1 = (CardView) findViewById(R.id.cardclass1);
        classcard1.setOnClickListener(new Listener(69)); // Pass 69 because it's unused
        classcard2 = (CardView) findViewById(R.id.cardclass2);
        classcard2.setOnClickListener(new Listener(69)); // Hey, gotta have some fun here
        classcard3 = (CardView) findViewById(R.id.cardclass3);
        classcard3.setOnClickListener(new Listener(69)); // Immaturity FTW
        classcard4 = (CardView) findViewById(R.id.cardclass4);
        classcard4.setOnClickListener(new Listener(69)); // Still going strong
        classcard5 = (CardView) findViewById(R.id.cardclass5);
        classcard5.setOnClickListener(new Listener(69)); // Don't judge
        classcard6 = (CardView) findViewById(R.id.cardclass6);
        classcard6.setOnClickListener(new Listener(69)); // I'm regretting this

        Toolbar class1Toolbar = (Toolbar) findViewById(R.id.class1_toolbar);
        Toolbar class2Toolbar = (Toolbar) findViewById(R.id.class2_toolbar);
        Toolbar class3Toolbar = (Toolbar) findViewById(R.id.class3_toolbar);
        Toolbar class4Toolbar = (Toolbar) findViewById(R.id.class4_toolbar);
        Toolbar class5Toolbar = (Toolbar) findViewById(R.id.class5_toolbar);
        Toolbar class6Toolbar = (Toolbar) findViewById(R.id.class6_toolbar);

        if ((class1Toolbar != null) && (class2Toolbar != null) && (class3Toolbar != null)
                && (class4Toolbar != null) && (class5Toolbar != null) && (class6Toolbar != null)) {
            class1Toolbar.inflateMenu(R.menu.menu_delete);
            class1Toolbar.setOnMenuItemClickListener(new Listener(1));
            class2Toolbar.inflateMenu(R.menu.menu_delete);
            class2Toolbar.setOnMenuItemClickListener(new Listener(2));
            class3Toolbar.inflateMenu(R.menu.menu_delete);
            class3Toolbar.setOnMenuItemClickListener(new Listener(3));
            class4Toolbar.inflateMenu(R.menu.menu_delete);
            class4Toolbar.setOnMenuItemClickListener(new Listener(4));
            class5Toolbar.inflateMenu(R.menu.menu_delete);
            class5Toolbar.setOnMenuItemClickListener(new Listener(5));
            class6Toolbar.inflateMenu(R.menu.menu_delete);
            class6Toolbar.setOnMenuItemClickListener(new Listener(6));
        }
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
            switchActivities("SettingsActivity", 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupDrawer() {
        PrimaryDrawerItem homeDrawer = new PrimaryDrawerItem()
                .withName("Home")
                .withIdentifier(0)
                .withIcon(GoogleMaterial.Icon.gmd_home);

        PrimaryDrawerItem classesDrawer = new PrimaryDrawerItem()
                .withName("All Classes")
                .withSelectable(false)
                .withEnabled(false)
                .withTextColor(Color.parseColor("#9E9E9E"));

        final SecondaryDrawerItem class1Drawer = new SecondaryDrawerItem()
                .withName(getClassName(1))
                .withIdentifier(1)
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class2Drawer = new SecondaryDrawerItem()
                .withName(getClassName(2))
                .withIdentifier(2)
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class3Drawer = new SecondaryDrawerItem()
                .withName(getClassName(3))
                .withIdentifier(3)
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class4Drawer = new SecondaryDrawerItem()
                .withName(getClassName(4))
                .withIdentifier(4)
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class5Drawer = new SecondaryDrawerItem()
                .withName(getClassName(5))
                .withIdentifier(5)
                .withIcon(R.drawable.class_ic_png);

        SecondaryDrawerItem class6Drawer = new SecondaryDrawerItem()
                .withName(getClassName(6))
                .withIdentifier(6)
                .withIcon(R.drawable.class_ic_png);

        PrimaryDrawerItem settingsDrawer = new PrimaryDrawerItem()
                .withName("Settings")
                .withIdentifier(10)
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
                        switch (drawerItem.getIdentifier()) {
                            case 0: // Always HOME
                                switchActivities("MainActivity", 0);
                                break;

                            case 1:
                                switchActivities("ClassRoster", 1);
                                break;

                            case 2:
                                switchActivities("ClassRoster", 2);
                                break;

                            case 3:
                                switchActivities("ClassRoster", 3);
                                break;

                            case 4:
                                switchActivities("ClassRoster", 4);
                                break;

                            case 5:
                                switchActivities("ClassRoster", 5);
                                break;

                            case 6:
                                switchActivities("ClassRoster", 6);
                                break;

                            case 10:
                                switchActivities("SettingsActivity", 0);
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
                editor = sharedPreferencesClass1.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class1.xml").delete();
                break;

            case 2:
                editor = sharedPreferencesClass2.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class2.xml").delete();
                break;

            case 3:
                editor = sharedPreferencesClass3.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class3.xml").delete();
                break;

            case 4:
                editor = sharedPreferencesClass4.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class4.xml").delete();
                break;

            case 5:
                editor = sharedPreferencesClass5.edit();
                editor.clear();
                editor.commit();
                new File(dir, "class5.xml").delete();
                break;

            case 6:
                editor = sharedPreferencesClass6.edit();
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
                    R.string.no_classes
            );
            classStudentCount1.setText(
                    R.string.no_classes_create_one
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
        setupDrawer(); // Effectively update the drawer
    }

    public String getClassName(int whichClass) {
        if (sharedPreferences.contains("class" + whichClass)) {
            return sharedPreferences.getString("class" + whichClass, "");
        }
        else {
            return "No Classes!";
        }
    }

    public void switchActivities(String newActivity, int newClass) {
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

            case "ClassRoster":
                changeActivities = new Intent(getApplicationContext(), ClassRoster.class);
                Log.d("ActivitySwitch", "Switching to ClassRoster Activity");
                Bundle bundle = new Bundle();
                bundle.putInt("bzofghia", newClass);
                changeActivities.putExtras(bundle);
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
                                .setAction("Dandy!", null)
                                .setActionTextColor(Color.parseColor("#FFFFC107"))
                                .show();
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
                        if (sharedPreferencesClass1.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass1.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 2:
                        if (sharedPreferencesClass2.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass2.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 3:
                        if (sharedPreferencesClass3.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass3.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 4:
                        if (sharedPreferencesClass4.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass4.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 5:
                        if (sharedPreferencesClass5.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass5.getString(String.valueOf(student), "");
                            thisClass.put(student, name);
                            student++;
                        }
                        else {
                            hasStudents = false;
                        }
                        break;

                    case 6:
                        if (sharedPreferencesClass6.contains(String.valueOf(student))) {
                            name = sharedPreferencesClass6.getString(String.valueOf(student), "");
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

    class Listener implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
        int nClass = 0;

        public Listener(int nClass) {
            this.nClass = nClass;
        }

        public void switchActivities(String newActivity, int nClass) {
            Intent changeActivities;

            switch (newActivity) {
                case "ClassRoster":
                    changeActivities = new Intent(getApplicationContext(), ClassRoster.class);
                    Log.d("ActivitySwitch", "Switching to ClassRoster Activity");
                    Bundle bundle = new Bundle();
                    bundle.putInt("bzofghia", nClass);
                    changeActivities.putExtras(bundle);
                    finish();
                    startActivity(changeActivities);
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                    "Are you sure you want to continue", "Delete", "Cancel", nClass);
            return true;
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