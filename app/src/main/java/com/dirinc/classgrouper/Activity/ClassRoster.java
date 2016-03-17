package com.dirinc.classgrouper.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dirinc.classgrouper.Adapter.*;
import com.dirinc.classgrouper.Info.*;
import com.dirinc.classgrouper.R;

import com.github.clans.fab.FloatingActionMenu;

import java.util.*;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

public class ClassRoster extends AppCompatActivity {
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

        mRecyclerView = (RecyclerView) findViewById(R.id.roster_recycler);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final RecyclerView.Adapter mAdapter = new CardAdapter(loadClasses(), classNumber, this);
        mRecyclerView.setAdapter(mAdapter);


        final MaterialDialog dialoger = new MaterialDialog.Builder(this)
                .title("Custom Groups")
                .customView(R.layout.custom_group, true)
                .positiveText("OK")
                .negativeText("CANCEL")
                .build();

        final View view = dialoger.getCustomView();

        if (view != null) {
            final MaterialNumberPicker numberPicker =
                    (MaterialNumberPicker) view.findViewById(R.id.number_picker);
            numberPicker.setMaxValue(mAdapter.getItemCount() - 1);
        }

        final FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        assert fam != null;
        handleFam(fam);
        fam.setClosedOnTouchOutside(true);

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpened) {
                    dialoger.show();
                } else {
                    fam.toggle(true);
                    menuIsOpened = !menuIsOpened; // Flipperoo
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                switchActivities("Main");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        return (new Main().getClassData(this));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent changeActivities = new Intent(this, Main.class);
        Log.d("ActivitySwitch", "Switching to Main Activity");
        startActivity(changeActivities);
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "Main":
                changeActivities = new Intent(this, Main.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }
}