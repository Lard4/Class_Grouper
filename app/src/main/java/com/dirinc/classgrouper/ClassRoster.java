package com.dirinc.classgrouper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;

public class ClassRoster extends AppCompatActivity {
    private int classNumber;
    private boolean menuIsOpened = false;

    private View view;

    private HashMap<Integer, String> class1 = new HashMap<>();
    private HashMap<Integer, String> class2 = new HashMap<>();
    private HashMap<Integer, String> class3 = new HashMap<>();
    private HashMap<Integer, String> class4 = new HashMap<>();
    private HashMap<Integer, String> class5 = new HashMap<>();
    private HashMap<Integer, String> class6 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);

        view = findViewById(R.id.class_roster);

        Bundle bundle = getIntent().getExtras();
        classNumber = bundle.getInt("bzofghia");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fam.setClosedOnTouchOutside(true);
        handleFam(fam);

        fam.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuIsOpened) {
                    promptGroupQuantity();
                }
                fam.toggle(true);
                menuIsOpened = !menuIsOpened; // Flipperoo
            }
        });

        loadClasses();
        createRoster(1);
    }

    public void promptGroupQuantity() {
        final Dialog d = new Dialog(ClassRoster.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.content_class_roster);
        //Button b1 = (Button) d.findViewById(R.id.button1);
        //Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.number_picker);
        np.setVisibility(View.VISIBLE);
        np.setMaxValue(getClassSize()); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // HUE
            }
        });
        /*
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue())); //set the value to textview
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        }); */
        d.show();
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
            HashMap<String, Integer> map = (HashMap<String, Integer>) pref.getAll();
            for (String s : map.keySet()) {
                String value = String.valueOf(map.get(s));
                addToMap(Integer.parseInt(s), value);
            }
        }
    }

    public void createRoster(int id) {
        boolean isSquare = false;
        RelativeLayout mainRelativeLayout = (RelativeLayout) findViewById(R.id.class_roster);

        for (int x = 0; x < getClassSize(); x++) {
            final CardView card = (CardView) findViewById(R.id.student_card);
            TextView name = (TextView) findViewById(R.id.student_name);
            final ImageView leftColor = (ImageView) findViewById(R.id.student_card_color_left);
            final ImageView rightColor = (ImageView) findViewById(R.id.student_card_color_right);
            final ViewGroup indvCardLayout = (ViewGroup) findViewById(R.id.card_layout);

            card.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            leftColor.setVisibility(View.VISIBLE);
            rightColor.setVisibility(View.VISIBLE);

            leftColor.setId(100 + x);
            rightColor.setId(1000 + x);
            name.setId(id + 10000);
            card.setId(id);
            indvCardLayout.setId(100000 + x);


            leftColor.setOnClickListener(new Listener(isSquare, leftColor, indvCardLayout));
            rightColor.setOnClickListener(new Listener(isSquare, rightColor, indvCardLayout));
            isSquare = true;

            name.setText(getStudentName(x));

            TransitionManager.beginDelayedTransition(mainRelativeLayout);

            RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 220);

            if (id != 1) {
                EditLayoutParams.addRule(RelativeLayout.BELOW, (id - 1));
                EditLayoutParams.setMargins(0, 20, 0, 0);
            }

            card.setLayoutParams(EditLayoutParams);

            // Add it to the main RelativeLayout
            CardView cardLayout = (CardView) View.inflate(this,
                    R.layout.student, null);
            mainRelativeLayout.addView(cardLayout);
            id++;
        }
    }

    public int getClassSize() {
        switch (classNumber) {
            case 1:
                return class1.size();

            case 2:
                return class2.size();

            case 3:
                return class3.size();

            case 4:
                return class4.size();

            case 5:
                return class5.size();

            case 6:
                return class6.size();

            default:
                return 0;
        }
    }

    public String getStudentName(int key) {
        switch (classNumber) {
            case 1:
                return class1.get(key);

            case 2:
                return class2.get(key);

            case 3:
                return class3.get(key);

            case 4:
                return class4.get(key);

            case 5:
                return class5.get(key);

            case 6:
                return class6.get(key);

            default:
                return null;
        }
    }

    public void addToMap(int key, String value) {
        switch (classNumber) {
            case 1:
                class1.put(key, value);
                break;

            case 2:
                class2.put(key, value);
                break;

            case 3:
                class3.put(key, value);
                break;

            case 4:
                class4.put(key, value);
                break;

            case 5:
                class5.put(key, value);
                break;

            case 6:
                class6.put(key, value);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent changeActivities = new Intent(this, MainActivity.class);
        Log.d("ActivitySwitch", "Switching to Main Activity");
        startActivity(changeActivities);
    }

    class Listener implements View.OnClickListener, View.OnTouchListener{
        final boolean[] isSquare = new boolean[1];
        final ImageView color;
        final ViewGroup indvCardLayout;
        final boolean wantsColor;

        public Listener(boolean s, ImageView lc, ViewGroup cl) {
            isSquare[0] = s;
            color = lc;
            indvCardLayout = cl;
            wantsColor = true;
        }

        @Override
        public boolean onTouch (View view, MotionEvent motionEvent) {

            return false;
        }

        @Override
        public void onClick(View v) {
            if (wantsColor) {
                TransitionManager.beginDelayedTransition(indvCardLayout);

                if (!isSquare[0]) {
                    // Reposition it
                    RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    positionRules.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    positionRules.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE);
                    positionRules.setMargins(0, 20, 0, 0);
                    color.setLayoutParams(positionRules);

                    // It's a square now
                    isSquare[0] = false;
                } else {
                    // Reposition it
                    RelativeLayout.LayoutParams positionRules = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    positionRules.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    positionRules.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                    positionRules.setMargins(0, 0, 20, 0);
                    color.setLayoutParams(positionRules);

                    // Change size
                    ViewGroup.LayoutParams sizeRules = color.getLayoutParams();
                    sizeRules.width = 85;
                    sizeRules.height = 85;
                    color.setLayoutParams(sizeRules);

                    // It's a square now
                    isSquare[0] = false;
                }
            }
        }
    }
}