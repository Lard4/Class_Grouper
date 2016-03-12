package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Student> students;
    private List<ClassInfo> classData;
    private HashMap<Integer, String> thisClass;

    private int nClass;
    private boolean isMain;
    private ClassInfo classEditor;
    private View layout;
    public Context context;

    /** Roster Activity **/
    public CardAdapter(ArrayList<ClassInfo> classData, int nClass, Context context) {
        super();
        this.isMain = false;
        this.context = context;
        this.classData = classData;
        this.nClass = nClass;
        this.classEditor = new ClassInfo(nClass, context);
        this.students = new ArrayList<>();
        this.thisClass = classData.get(nClass).getMap();

        for (int i = 0; i < thisClass.size(); i++) {
            Student student = new Student();
            if (getInitials(i) != null) {
                student.setInitials(getInitials(i)).setColor(generateColor());
                students.add(student);
            }
        }
    }

    /** Main Activity **/
    public CardAdapter(ArrayList<ClassInfo> classData, View layout, Context context) {
        super();
        this.isMain = true;
        this.layout = layout;
        this.classData = classData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        if (isMain) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.class_card, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.student_card, viewGroup, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final int position = viewHolder.getAdapterPosition();

        if (isMain) {
            final ClassInfo classInfo = classData.get(position);
            viewHolder.classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(context, ActivityClassRoster.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("bzofghia", position);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            viewHolder.classCardTitle.setText(classInfo.getCardName());
            viewHolder.classCardStudentCount.setText(classInfo.getCardStudentCount());
            viewHolder.classCardColor.setBackgroundColor(classInfo.getCardColor());
            viewHolder.classCardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(layout.getContext());
                    dialog.setTitle("Warning!")
                            .setMessage("You are about to delete a class. Are you sure you" +
                                    "want to do this? This action cannot be undone.")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    dialoginterface.cancel();
                                }
                            })
                            .setPositiveButton("OK, M8!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int idk) {
                                    removeClass(position);
                                    Snackbar.make(layout, "Class '" + classInfo.getCardName() + "' deleted", Snackbar.LENGTH_LONG)
                                            .setAction("Dandy!", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) { /*69*/ }
                                            })
                                            .setActionTextColor(Color.parseColor("#FFFFC107"))
                                            .show();
                                }
                            }).show();
                    return true;
                }
            });
        } else {
            final Student student = students.get(position);
            viewHolder.studentCardColor.setBackgroundColor(student.getColor());
            viewHolder.studentCardInitials.setText(student.getInitials());
            viewHolder.studentCardDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Actually work.
                    students.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, students.size());
                }
            });
            viewHolder.studentCardAbsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //May someday break something... Oh whale.
                    CardView card = (CardView) v.getParent().getParent();

                    if (card.getAlpha() != .2f) {
                        makeAbsent(true, position, viewHolder);
                    } else {
                        makeAbsent(false, position, viewHolder);
                    }
                }
            });
            if (students.get(position).getAbsent()) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        makeAbsent(true, i, viewHolder);

                        handler.postDelayed(this, 20);
                        handler.removeCallbacks(this);
                    }
                }, 20);
            }
        }
    }

    public void makeAbsent(boolean isAbsent, final int position, final ViewHolder viewHolder) {
        if (isAbsent) {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = students.get(position);
                    students.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    students.add(tempStud);
                    notifyItemInserted(students.size());

                    handler.postDelayed(this, 0);

                    viewHolder.studentCard.setAlpha(.2f);
                    //students.get(i).setAbsent(true);
                    viewHolder.studentCardDelete.setClickable(false);

                    handler.removeCallbacks(this);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(.2f);
            students.get(position).setAbsent(true);
            viewHolder.studentCardDelete.setClickable(false);

        } else {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = students.get(position);
                    students.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    students.add(0, tempStud);
                    notifyItemInserted(0);

                    handler.postDelayed(this, 0);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(1f);
            students.get(position).setAbsent(false);
            viewHolder.studentCardDelete.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        if (isMain) {
            return classData.size();
        } else {
            return students.size();
        }
    }

    public void removeClass(int whichClass) {
        classData.remove(whichClass);
        notifyItemRemoved(whichClass);
        notifyItemRangeChanged(whichClass, classData.size());

        File dir = new File(context.getFilesDir().getParent() + "/shared_prefs/");

        SharedPreferences prefs = context.getSharedPreferences("shared_preferences", 0);
        SharedPreferences.Editor editor = prefs.edit();

        int numberOfClasses = prefs.getInt("numberOfClasses", 0);

        editor.remove("numberOfClasses");
        editor.putInt("numberOfClasses", numberOfClasses - 1);
        editor.apply();

        new File(dir, "class" + whichClass + ".xml").delete(); // Is this _perjury_? /s

        while (true) {
            if (new File(dir, "class" + (whichClass + 1) + ".xml").exists()) {
                new File(dir, "class" + (whichClass + 1) + ".xml").renameTo(
                        new File(dir, "class" + (whichClass) + ".xml"));
                whichClass++;
            } else {
                break;
            }
        }

        if (getItemCount() == 0) {
            ClassInfo emptyClass = new ClassInfo(-1, context);
            emptyClass.setCardName("No Classes!")
                    .setCardStudentCount("CREATE A CLASS TO GET STARTED")
                    .setCardColor(generateColor());

            classData.add(emptyClass);
            notifyItemInserted(0);
        }
    }

    public String getInitials(int key) {
        if (thisClass.get(key) != null) {
            String name = thisClass.get(key).replaceAll("[.,]", "");
            String initials = "";

            for (String s : name.split(" ")) {
                initials += s.charAt(0);
            }
            return initials;
        } else {
            return null;
        }
    }

    public int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    public void generateGroups() {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CardView studentCard;
        public RelativeLayout studentCardColor;
        public TextView studentCardInitials;
        public ImageView studentCardDelete, studentCardAbsent;

        public TextView classCardTitle, classCardStudentCount;
        public ImageView classCardColor;
        public Toolbar classCardToolbar;
        public CardView classCard;

        public ViewHolder(View itemView) {
            super(itemView);

            if (isMain) {
                this.classCard = (CardView) itemView.findViewById(R.id.class_card);
                context = this.classCard.getContext();
                this.classCardTitle = (TextView) itemView.findViewById(R.id.class_card_title);
                this.classCardStudentCount = (TextView) itemView.findViewById(R.id.class_card_student_count);
                this.classCardColor = (ImageView) itemView.findViewById(R.id.class_card_color);
                this.classCardToolbar = (Toolbar) itemView.findViewById(R.id.class_card_toolbar);
                this.classCardToolbar.inflateMenu(R.menu.menu_delete);
            } else {
                this.studentCardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
                this.studentCardInitials = (TextView) itemView.findViewById(R.id.student_name);
                this.studentCardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
                this.studentCardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
                this.studentCard = (CardView) itemView.findViewById(R.id.student_card);
            }
        }
    }
}
