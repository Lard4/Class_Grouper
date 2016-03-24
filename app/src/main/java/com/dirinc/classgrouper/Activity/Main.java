package com.dirinc.classgrouper.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;

import com.dirinc.classgrouper.Adapter.*;
import com.dirinc.classgrouper.Info.*;
import com.dirinc.classgrouper.R;
import com.dirinc.classgrouper.Util.NavigationDrawer;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationDrawer navigationDrawer;
    private View view;

    public static final String aCreateClass = "CreateClass";
    public static final String aClassRoster = "ClassRoster";
    public static final String aSettings = "Settings";
    public static final String aMain = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        this.view = findViewById(R.id.main_layout);

        final AppCompatActivity activity = this;

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(activity, "CreateClass", 0);
            }
        });

        createDrawer(this, toolbar);
        createCards();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void createDrawer(AppCompatActivity activity, Toolbar tb) {
        navigationDrawer = new NavigationDrawer(activity, tb);
        navigationDrawer.initialize()
                .addFooter("Settings", GoogleMaterial.Icon.gmd_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                    }
                }, 0)
                .addFooter("Send Feedback", GoogleMaterial.Icon.gmd_feedback, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"kdixson538@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Class Grouper");
                        i.putExtra(Intent.EXTRA_TEXT   , "Love the app!");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Snackbar.make(view, "There are no email clients installed", Snackbar.LENGTH_SHORT);
                        }
                    }
                }, 1);
    }

    public NavigationDrawer getDrawer(AppCompatActivity activity, Toolbar tb) {
        createDrawer(activity, toolbar);
        return navigationDrawer;
    }

    public void createCards() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.class_recycler);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView.Adapter mainAdapter = new MainAdapter(getClassData(this),
                findViewById(R.id.main_layout), this, navigationDrawer, this);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(mainAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dX, int dY) {
                    if (dY > 0 && fab.isShown()) {
                        fab.startAnimation(AnimationUtils.loadAnimation(Main.this, R.anim.fab_scale_down));
                        fab.setVisibility(View.GONE);
                    } else if (dY < 0 && !fab.isShown()) {
                        fab.setVisibility(View.VISIBLE);
                        fab.startAnimation(AnimationUtils.loadAnimation(Main.this, R.anim.fab_scale_up));
                    }
                }
            });
        }
    }

    public ArrayList<ClassInfo> getClassData(Context context) {
        ArrayList<ClassInfo> classes = new ArrayList<>();
        SharedPreferences prefs = context.getSharedPreferences("shared_preferences", 0);
        int nClasses = prefs.getInt("numberOfClasses", 0);

        if (nClasses == 0) {
            ClassInfo newClass = new ClassInfo(-1, context);
            newClass.setCardName("No Classes!")
                    .setCardStudentCount("CREATE A CLASS TO GET STARTED")
                    .setCardColor(generateColor());

            classes.add(newClass);
            return classes;
        }

        for (int i = 0; i < nClasses; i++) {
            ClassInfo newClass = new ClassInfo(i, context);
            HashMap<Integer, String> roster = new HashMap<>();
            SharedPreferences classPrefs = context.getSharedPreferences("class" + i, 0);
            int student = 0;

            while (true) {
                if (classPrefs.contains(String.valueOf(student))) {
                    roster.put(student, classPrefs.getString(String.valueOf(student), null));
                    student++;
                } else {
                    break;
                }
            }
            newClass.setMap(roster);
            newClass.setCardName(classPrefs.getString("title", null))
                    .setCardStudentCount(roster.size() + " STUDENTS")
                    .setCardColor(Main.generateColor());

            classes.add(newClass);
        }
        return classes;
    }

    public static int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            switchActivities(this, "Settings", 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void switchActivities(AppCompatActivity activity, String newActivity, int newClass) {
        Intent changeActivities;

        switch (newActivity) {
            case "Main":
                changeActivities = new Intent(activity, Main.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                activity.startActivity(changeActivities);
                break;

            case "CreateClass":
                changeActivities = new Intent(activity, CreateClass.class);
                Log.d("ActivitySwitch", "Switching to CreateClass Activity");
                activity.startActivity(changeActivities);
                break;

            case "Settings":
                changeActivities = new Intent(activity, Settings.class);
                Log.d("ActivitySwitch", "Switching to Settings Activity");
                activity.startActivity(changeActivities);
                break;

            case "ClassRoster":
                changeActivities = new Intent(activity, ClassRoster.class);
                Log.d("ActivitySwitch", "Switching to ClassRoster Activity");
                Bundle bundle = new Bundle();
                bundle.putInt("bzofghia", newClass);
                changeActivities.putExtras(bundle);
                activity.startActivity(changeActivities);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}