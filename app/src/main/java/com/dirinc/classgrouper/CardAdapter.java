package com.dirinc.classgrouper;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Student> mStudents;
    private List<ClassInfo> mClasses;
    private HashMap<Integer, String> thisClass;

    private String[][] classData = new String[100][2];
    private static final int TOOLBAR_ID_CONSTANT = 100;

    private int nClass;
    private boolean isMain;
    private ClassInfo classEditor;
    private View layout;

    public CardAdapter(HashMap<Integer, String> thisClass, int nClass, Context context,
                       boolean isMain, String classData[][], View layout) {
        super();
        this.isMain = isMain;
        this.layout = layout;

        if (isMain) {
            this.classData = classData;
            mClasses = new ArrayList<>();

            for (int i = 0; i < classData.length; i++) {
                ClassInfo newClass = new ClassInfo(i, context);

                if (classData[i][0].equals("")) break; //Exit for loop once we hit bottom

                newClass.setCardName(classData[i][0])
                        .setCardStudentCount(classData[i][1])
                        .setCardColor(generateColor());
                mClasses.add(newClass);
            }
        } else {
            this.thisClass = thisClass;
            this.nClass = nClass;
            this.classEditor = new ClassInfo(nClass, context);

            mStudents = new ArrayList<>();

            for (int i = 0; i < thisClass.size(); i++) {
                Student student = new Student();
                if (getInitials(i) != null) {
                    student.setInitials(getInitials(i)).setColor(generateColor());
                    mStudents.add(student);
                }
            }
        }
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
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (isMain) {
            ClassInfo classInfo = mClasses.get(i);
            viewHolder.classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ActivityMain().goToRoster(mClasses.get(i).nClass);
                }
            });
            viewHolder.classCardTitle.setText(classInfo.getCardName());
            viewHolder.classCardStudentCount.setText(classInfo.getCardStudentCount());
            viewHolder.classCardColor.setBackgroundColor(classInfo.getCardColor());
            viewHolder.classCardToolbar.inflateMenu(R.menu.menu_delete);
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
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    //TODO deleteClass(nClass);
                                    Snackbar.make(layout, "Class x deleted", Snackbar.LENGTH_LONG)
                                            .setAction("Dandy!", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) { }
                                            })
                                            .setActionTextColor(Color.parseColor("#FFFFC107"))
                                            .show();
                                }
                            }).show();
                    return true;
                }
            });
        } else {
            Student student = mStudents.get(i);
            viewHolder.studentCardColor.setBackgroundColor(student.getColor());
            viewHolder.studentCardInitials.setText(student.getInitials());

            viewHolder.studentCardDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mStudents.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, mStudents.size());
                    classEditor.removeStudent(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isMain) {
            return mClasses.size();
        } else {
            return mStudents.size();
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

    class ViewHolder extends RecyclerView.ViewHolder {
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
                this.classCardTitle = (TextView) itemView.findViewById(R.id.class_card_title);
                this.classCardStudentCount = (TextView) itemView.findViewById(R.id.class_card_student_count);
                this.classCardColor = (ImageView) itemView.findViewById(R.id.class_card_color);
                this.classCardToolbar = (Toolbar) itemView.findViewById(R.id.class_card_toolbar);
            } else {
                this.studentCardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
                this.studentCardInitials = (TextView) itemView.findViewById(R.id.student_name);
                this.studentCardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
                this.studentCardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
            }
        }
    }
}
