package com.dirinc.classgrouper.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.dirinc.classgrouper.Activity.Settings;
import com.dirinc.classgrouper.Info.ClassInfo;
import com.dirinc.classgrouper.Info.Student;
import com.dirinc.classgrouper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RosterAdapter extends SectionedRecyclerViewAdapter<RosterAdapter.ViewHolder> {

    private List<Student> studentData;
    private List<ClassInfo> classData;
    private HashMap<Integer, String> thisClass;

    private int nClass;
    private boolean isMain;
    private ClassInfo classEditor;
    private View layout;
    public Context context;

    public RosterAdapter(ArrayList<ClassInfo> classData, int nClass, Context context) {
        super();
        this.context = context;
        this.classData = classData;
        this.nClass = nClass;
        this.classEditor = new ClassInfo(nClass, context);
        this.studentData = new ArrayList<>();
        this.thisClass = classData.get(nClass).getMap();

        for (int i = 0; i < thisClass.size(); i++) {
            Student student = new Student();
            if (getInitials(i) != null) {
                student.setInitials(getInitials(i))
                        .setColor(generateColor())
                        .setName(getName(i));
                studentData.add(student);
            }
        }
    }

    @Override
    public int getSectionCount() {
        return getGroupCount(); // number of sections.
    }

    @Override
    public int getItemCount(int section) {
        return getStudentInGroupCount(); // number of items in section (section index is parameter).
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int section) {
        holder.sectionTitle.setText("Group " + (section + 1));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int section, int relativePosition, int absolutePosition) {
        final int position = viewHolder.getAdapterPosition();

        final Student student = studentData.get(position - 1);

        viewHolder.studentCardColor.setBackgroundColor(student.getColor());
        setStudentName(viewHolder.studentCardInitials, student);
        viewHolder.studentCardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Actually work.
                studentData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentData.size());
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
        if (studentData.get(position).getAbsent()) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    makeAbsent(true, position, viewHolder);

                    handler.postDelayed(this, 20);
                    handler.removeCallbacks(this);
                }
            }, 20); //Hold up 20ms to finish drawing
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Change inflated layout based on 'header'.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == VIEW_TYPE_HEADER ? R.layout.adapter_header : R.layout.student_card, parent, false);
        return new ViewHolder(v);
    }

    public void makeAbsent(boolean isAbsent, final int position, final ViewHolder viewHolder) {
        if (isAbsent) {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = studentData.get(position);
                    studentData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    studentData.add(tempStud);
                    notifyItemInserted(studentData.size());

                    handler.postDelayed(this, 0);

                    viewHolder.studentCard.setAlpha(.2f);
                    //studentData.get(i).setAbsent(true);
                    viewHolder.studentCardDelete.setClickable(false);

                    handler.removeCallbacks(this);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(.2f);
            studentData.get(position).setAbsent(true);
            viewHolder.studentCardDelete.setClickable(false);

        } else {
            /*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Student tempStud = studentData.get(position);
                    studentData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, classData.size());
                    studentData.add(0, tempStud);
                    notifyItemInserted(0);

                    handler.postDelayed(this, 0);
                }
            }, 250);
            */
            viewHolder.studentCard.setAlpha(1f);
            studentData.get(position).setAbsent(false);
            viewHolder.studentCardDelete.setClickable(true);
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

    public String getName(int key) {
        if (thisClass.get(key) != null) {
            String name = thisClass.get(key);
            String formattedName = "";

            for (String s : name.split(" ")) {
                formattedName += s.charAt(0);

                for (int i = 1; i < s.length(); i++) {
                    formattedName += "<small>" + s.charAt(i) + "</small>";
                }

                formattedName += " ";
            }

            return formattedName;
        } else {
            return null;
        }
    }

    public void setStudentName(TextView name, Student student) {
        SharedPreferences prefs = context.getSharedPreferences("shared_preferences", 0);
        boolean fullName = prefs.getBoolean(Settings.FN, false);

        if (fullName) {
            name.setTextSize(name.getResources().getDimension(R.dimen.student_card_text_size_name));
            name.setMaxLines(3);
            name.setText(Html.fromHtml(student.getName()));
        } else {
            name.setText(student.getInitials());
        }
    }

    public int getGroupCount() {
        return 2;
    }

    public int getStudentInGroupCount() {
        return 4;
    }

    public int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView studentCard;
        public RelativeLayout studentCardColor;
        public TextView studentCardInitials;
        public ImageView studentCardDelete, studentCardAbsent;
        public TextView sectionTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            this.studentCardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
            this.studentCardInitials = (TextView) itemView.findViewById(R.id.student_name);
            this.studentCardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
            this.studentCardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
            this.studentCard = (CardView) itemView.findViewById(R.id.student_card);
            this.sectionTitle = (TextView) itemView.findViewById(R.id.section_title);
        }
    }
}