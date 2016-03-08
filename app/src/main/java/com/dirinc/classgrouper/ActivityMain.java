package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ActivityMain extends AppCompatActivity implements NavigationDrawerCallbacks {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static NavigationDrawerFragment navigationDrawerFragment;

    private View view;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private static final String SHARED_PREFS = "shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view = findViewById(R.id.main_layout);

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, 0);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities("ActivityCreateClass", 0);
            }
        });

        createDrawer();
        createCards();
    }

    public void start() {
        /*
        Toolbar class1Toolbar = (Toolbar) findViewById(R.id.class1_toolbar);
        class1Toolbar.inflateMenu(R.menu.menu_delete);
        class1Toolbar.setOnMenuItemClickListener(new Listener(1));
        */
    }

    public void createDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);
        // Set up the drawer.
        navigationDrawerFragment.setup(R.id.fragment_drawer,
                (DrawerLayout) findViewById(R.id.drawer), toolbar, true);
        /* Populate the navigation drawer
        navigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com",
                BitmapFactory.decodeResource(getResources(), R.drawable.avatar)); */
    }

    public NavigationDrawerFragment getDrawer() {
        return navigationDrawerFragment;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Update the main content by replacing fragments
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }

    public void createCards() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.class_recycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new CardAdapter(getClassData(this),
                findViewById(R.id.main_layout), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dX, int dY) {
                if (dY > 0 && fab.isShown())
                    fab.hide();
                else if (dY < 0 && !fab.isShown())
                    fab.show();
            }
        });
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
                    .setCardColor(ActivityMain.generateColor());

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
        if (/*!navigationDrawerFragment.isDrawerOpen()*/ true) {
            /* Only show items in the action bar relevant to this screen
             * if the drawer is not showing. Otherwise, let the drawer
             * decide what to show in the action bar.
             */
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            switchActivities("ActivitySettings", 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            case "ActivityCreateClass":
                changeActivities = new Intent(this, ActivityCreateClass.class);
                Log.d("ActivitySwitch", "Switching to ActivityCreateClass Activity");
                startActivity(changeActivities);
                //finish();
                break;

            case "ActivitySettings":
                changeActivities = new Intent(this, ActivitySettings.class);
                Log.d("ActivitySwitch", "Switching to Settings Activity");
                startActivity(changeActivities);
                //finish();
                break;

            case "ActivityClassRoster":
                changeActivities = new Intent(getApplicationContext(), ActivityClassRoster.class);
                Log.d("ActivitySwitch", "Switching to ActivityClassRoster Activity");
                Bundle bundle = new Bundle();
                bundle.putInt("bzofghia", newClass);
                changeActivities.putExtras(bundle);
                startActivity(changeActivities);
                //finish();
                break;
        }
    }

    public int getNumberOfClasses() {
        return sharedPreferences.getInt("numberOfClasses", 0);
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment.isDrawerOpen()) {
            navigationDrawerFragment.closeDrawer();
        } else {
            super.onBackPressed();
            this.finishAffinity();
        }
    }
}