package com.dirinc.classgrouper;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.adapter.BaseDrawerAdapter;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private View view;
    private Toolbar toolbar;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }

    public void start() {
        view = findViewById(R.id.main_layout);

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

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

        /*
        Toolbar class1Toolbar = (Toolbar) findViewById(R.id.class1_toolbar);
        class1Toolbar.inflateMenu(R.menu.menu_delete);
        class1Toolbar.setOnMenuItemClickListener(new Listener(1));
        */

        createCards();
    }

    public void createCards() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.class_recycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new CardAdapter(null, 0,
                getApplicationContext(), true, getClassData());
        mRecyclerView.setAdapter(mAdapter);
    }

    public String[][] getClassData() {
        String[][] classData = new String[2][100];

        for (int i = 0; i < classData.length; i++) {
            for (int ii = 0; ii < classData[i].length; ii++) {
                classData[i][ii] = "";
            }
        }

        classData[0][0] = "Tester One";
        classData[0][1] = "2 STUDENTS";

        return classData;
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

        final SecondaryDrawerItem class0 = new SecondaryDrawerItem()
                .withName(getClassName(1))
                .withIdentifier(1)
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
                        class0,
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

                            case 10:
                                switchActivities("SettingsActivity", 0);
                                break;

                            default:
                                switchActivities("ClassRoster", drawerItem.getIdentifier());
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    public void deleteClass(int whichClass) {
        File dir = new File(getApplicationContext().getFilesDir().getParent() + "/shared_prefs/");

        int newNumberOfClasses = (getNumberOfClasses() - 1);
        editor.remove("numberOfClasses");
        editor.putInt("numberOfClasses", newNumberOfClasses);
        editor.commit();

        new File(dir, "class" + whichClass + ".xml").delete();
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
                startActivity(changeActivities);
                //finish();
                break;

            case "SettingsActivity":
                changeActivities = new Intent(this, SettingsActivity.class);
                Log.d("ActivitySwitch", "Switching to Settings Activity");
                startActivity(changeActivities);
                //finish();
                break;

            case "ClassRoster":
                changeActivities = new Intent(getApplicationContext(), ClassRoster.class);
                Log.d("ActivitySwitch", "Switching to ClassRoster Activity");
                Bundle bundle = new Bundle();
                bundle.putInt("bzofghia", newClass);
                changeActivities.putExtras(bundle);
                startActivity(changeActivities);
                //finish();
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
                        Snackbar.make(view, "ClassInfo " + nClass + " deleted", Snackbar.LENGTH_LONG)
                                .setAction("Dandy!", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Nothing to do, but if this is null, it won't show :/
                                    }
                                })
                                .setActionTextColor(Color.parseColor("#FFFFC107"))
                                .show();
                    }
                }).show();
    }

    public int getNumberOfClasses() {
        return sharedPreferences.getInt("numberOfClasses", 0);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}