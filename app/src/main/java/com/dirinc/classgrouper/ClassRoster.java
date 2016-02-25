package com.dirinc.classgrouper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.github.clans.fab.FloatingActionMenu;

import java.util.*;

public class ClassRoster extends AppCompatActivity {
    private int classNumber;
    private boolean menuIsOpened = false;

    private HashMap<Integer, String> thisClass = new HashMap<>();
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;

    public static final int CARD_MARGINS_HORIZONTAL = 50;
    public static final int CARD_MARGINS_VERTICAL = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);
        loadClasses(); // Must go before FAM is clicked to update count

        this.overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        Bundle bundle = getIntent().getExtras();
        classNumber = bundle.getInt("bzofghia");

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
        RecyclerView.Adapter mAdapter = new CardAdapter(thisClass);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                switchActivities("MainActivity");
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

    public void loadClasses() {
        SharedPreferences pref = null;
        switch (classNumber) {
            case 1:
                pref = getApplicationContext().getSharedPreferences("class1", Context.MODE_PRIVATE);
                break;

            case 2:
                pref = getApplicationContext().getSharedPreferences("class2", Context.MODE_PRIVATE);
                break;

            case 3:
                pref = getApplicationContext().getSharedPreferences("class3", Context.MODE_PRIVATE);
                break;

            case 4:
                pref = getApplicationContext().getSharedPreferences("class4", Context.MODE_PRIVATE);
                break;

            case 5:
                pref = getApplicationContext().getSharedPreferences("class5", Context.MODE_PRIVATE);
                break;

            case 6:
                pref = getApplicationContext().getSharedPreferences("class6", Context.MODE_PRIVATE);
                break;
        }
        if (pref != null) {
            @SuppressWarnings("unchecked")
            HashMap<String, Integer> map =  (HashMap<String, Integer>) pref.getAll();
            for (String s : map.keySet()) {
                String value = String.valueOf(map.get(s));
                addToMap(Integer.parseInt(s), value);
            }
        }
    }

    public void createRoster(int id) {
/*

        RelativeLayout mainRelativeLayout = (RelativeLayout) findViewById(R.id.class_roster);

        // Needed for student_card deletion to work
        for ( int child = 0; child < mainRelativeLayout.getChildCount(); child++) {
            View view = mainRelativeLayout.getChildAt(child);
            view.setVisibility(View.GONE);
        }

        for (int x = 0; x < getClassSize(); x++) {
            final CardView card = (CardView) findViewById(R.id.student_card);
            TextView name = (TextView) findViewById(R.id.student_name);
            final ImageView delete = (ImageView) findViewById(R.id.student_delete);
            final ImageView absent = (ImageView) findViewById(R.id.student_absent);
            final RelativeLayout indvCardLayout = (RelativeLayout) findViewById(R.id.card_layout);

            card.setVisibility(View.VISIBLE);

            card.setId(id);
            name.setId(id + 1000); // Easy number over 100 in case of retard with > 100 students
            indvCardLayout.setId(id + 10000);
            delete.setId(id + 100000);
            absent.setId(id + 1000000);

            RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.student_card_width),
                    (int) getResources().getDimension(R.dimen.student_card_height)
            );

            if (id % 2 != 0) { // Odd, LEFT SIDE
                if (id != 1) {
                    int belowThis = (id - 2);
                    EditLayoutParams.setMargins(0, CARD_MARGINS_HORIZONTAL, 0, 0);
                    EditLayoutParams.addRule(RelativeLayout.BELOW, belowThis);
                }
            } else { // 0 is "even", RIGHT SIDE
                int rightOfThis = (id - 1);
                EditLayoutParams.setMargins(CARD_MARGINS_VERTICAL, 0, 0, 0);
                EditLayoutParams.addRule(RelativeLayout.RIGHT_OF, rightOfThis);
            }

            card.setLayoutParams(EditLayoutParams);
            card.setUseCompatPadding(true);
            card.setPreventCornerOverlap(false);
            card.setRadius(getResources().getDimension(R.dimen.student_card_radius));
            card.setMaxCardElevation(getResources().getDimension(R.dimen.student_card_elevation));
            card.setBackgroundColor(getResources().getColor(android.R.color.white));

            name.setText(getStudentInitials(x));

            delete.setColorFilter(Color.parseColor("#404040"));

            final int finalId = id;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptDelete(finalId);
                }
            });

            absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float semiTransparent = 0.5f;
                    float opaque = 1.0f;

                    if (card.getAlpha() == semiTransparent) {
                        card.setAlpha(opaque);
                        absent.setColorFilter(Color.parseColor("#404040"));
                    } else {
                        card.setAlpha(semiTransparent);
                        absent.setColorFilter(Color.parseColor("#9E0010"));
                    }
                }
            });

            Random rand = new Random();

            int newColor = Color.argb(255,
                    rand.nextInt(156) + 100,    //R
                    rand.nextInt(156) + 100,    //G
                    rand.nextInt(156) + 100);   //B
            indvCardLayout.setBackgroundColor(newColor);

            // Add it to the main RelativeLayout
            CardView cardLayout = (CardView) View.inflate(this,
                    R.layout.student_card, null);

            mainRelativeLayout.addView(cardLayout);
            id++;
        }
*/

    }

    public void promptDelete(int id) {
        showAlertDialog("Warning!", "Deleting a class cannot be undone! " +
                "Are you sure you want to continue", "Delete", "Cancel", id);
    }

    public void showAlertDialog(String title, String message,
                                String positiveButton, String negativeButton, final int id) {
        android.support.v7.app.AlertDialog.Builder dialog =
                new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setTitle(title)
                .setMessage(message)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        thisClass.remove(id - 1); // DELETE ONE LESS BECAUSE IDS MUST START AT 1!!
                        //SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_CLASS1, 0).edit();
                        createRoster(1);
                    }
                }).show();
    }

    public String getStudentInitials(int key) {
        String name = getStudentName(key).replaceAll("[.,]", "");
        String initials = "";

        for(String s : name.split(" ")) {
            initials += s.charAt(0);
        }

        return initials;
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
        Intent changeActivities = new Intent(this, MainActivity.class);
        Log.d("ActivitySwitch", "Switching to Main Activity");
        startActivity(changeActivities);
    }

    public void switchActivities(String newActivity) {
        Intent changeActivities;

        switch (newActivity) {
            case "MainActivity":
                changeActivities = new Intent(this, MainActivity.class);
                Log.d("ActivitySwitch", "Switching to Main Activity");
                startActivity(changeActivities);
                break;
        }
    }
}