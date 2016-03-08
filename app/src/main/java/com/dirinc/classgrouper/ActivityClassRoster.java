package com.dirinc.classgrouper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionMenu;

import java.util.*;

public class ActivityClassRoster extends AppCompatActivity {
    private int classNumber;
    private boolean menuIsOpened = false;

    private HashMap<Integer, String> thisClass = new HashMap<>();
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);
        Bundle bundle = getIntent().getExtras();
        classNumber = bundle.getInt("bzofghia");

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setClosedOnTouchOutside(true);
        handleFam(fam);

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpened) {
                    //69
                }
                fam.toggle(true);
                menuIsOpened = !menuIsOpened; // Flipperoo
            }
        });

        //createRoster(1);
        mRecyclerView = (RecyclerView) findViewById(R.id.roster_recycler);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new CardAdapter(loadClasses(), classNumber, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                switchActivities("ActivityMain");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void shuffleGroups(int nGroups) {

    }

    public void handleFam(final FloatingActionMenu fam) {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fam.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fam.getMenuIconView().setImageResource(fam.isOpened()
                        ? R.drawable.ic_loop_white_24dp : R.drawable.fab_add);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fam.setIconToggleAnimatorSet(set);
    }

    public ArrayList<ClassInfo> loadClasses() {
        return (new ActivityMain().getClassData(this));
    }

    public int getClassSize() {
        return thisClass.size();
    }

    public String getStudentName(int key) {
        return thisClass.get(key);
    }

    public void addToMap(int key, String value) {
        thisClass.put(key, value);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent changeActivities = new Intent(this, ActivityMain.class);
        Log.d("ActivitySwitch", "Switching to Main Activity");
        startActivity(changeActivities);
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "ActivityMain":
                changeActivities = new Intent(this, ActivityMain.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }
}