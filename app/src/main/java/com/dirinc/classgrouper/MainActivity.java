package com.dirinc.classgrouper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;

    private TextView classTitle1;
    private TextView classStudentCount1;
    private CardView classcard1;

    private int id = 1;

    private static final String SHARED_PREFS = "shared_preferences";
    private static final String SHARED_PREFS_CLASS1 = "class1";
    private static final int CLASS_CARD = 1;
    private static final int CLASS_TITLE = 2;
    private static final int CLASS_COUNT = 3;

    private String[] files;
    private int student = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);
        sharedPreferencesclass1 = getSharedPreferences(SHARED_PREFS_CLASS1,0);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities("CreateClass");
            }
        });

        classcard1 = (CardView) findViewById(R.id.cardclass1);
        classcard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities("CreateClass");
            }
        });

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

    public void createCards() {
        if (sharedPreferences.contains("class1")) {
            classTitle1.setText(
                    getClassName(1)
            );
            classStudentCount1.setText(
                    (student - 1) + "  STUDENTS"
            );
        }

        // CLASS X
        for (int cardsToCreate = 2; sharedPreferences.contains("class" + cardsToCreate); cardsToCreate++) {
            Log.d("CLASS_CARDS", "Creating card " + cardsToCreate);
            id++;
            RelativeLayout thisLayout = (RelativeLayout) findViewById(R.id.rel_layout);
            CardView newClass = new CardView(
                    (new ContextThemeWrapper(this, R.style.class_card)));
            TextView newClassTitle = new TextView(
                    (new ContextThemeWrapper(this, R.style.class_card_title)));
            TextView newClassCount = new TextView(
                    (new ContextThemeWrapper(this, R.style.class_card_count)));

            newClass.setId(CLASS_CARD + id);
            newClassTitle.setId(CLASS_TITLE + id);
            newClassCount.setId(CLASS_COUNT + id);

            newClass.setMinimumHeight( (int)(getResources().getDimension(R.dimen.newClassWidthHeight)));
            newClass.setMinimumWidth((int) (getResources().getDimension(R.dimen.newClassWidthHeight)));

            newClassTitle.setText(getClassName(id));
            newClassCount.setText((student - 1) + " STUDENTS");

            RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (cardsToCreate == 2) {
                EditLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.cardclass1);
            } else if (cardsToCreate == 3) {
                EditLayoutParams.addRule(RelativeLayout.BELOW, R.id.cardclass1);
            } else if (cardsToCreate % 2 == 0) {
                EditLayoutParams.addRule(RelativeLayout.RIGHT_OF, (CLASS_CARD + id));
            } else if (cardsToCreate % 2 != 0) {
                EditLayoutParams.addRule(RelativeLayout.BELOW, ((CLASS_CARD + id) - 2));
            }

            newClass.setLayoutParams(EditLayoutParams);

            (thisLayout).addView(newClass);
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

        // if class exists
        if (whichClass <= getNumberOfClasses()) {
            while (hasStudents) {
                if (sharedPreferencesclass1.contains(String.valueOf(student))) {
                    name = sharedPreferencesclass1.getString(String.valueOf(student), "");
                    thisClass.put(student, name);
                    student++;
                }
                else {
                    hasStudents = false;
                }
            }
        }
        return thisClass;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}