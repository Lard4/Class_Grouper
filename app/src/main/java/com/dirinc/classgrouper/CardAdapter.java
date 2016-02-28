package com.dirinc.classgrouper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private List<Student> mItems;
    private HashMap<Integer, String> thisClass;
    private int nClass;
    private final ClassInfo classEditor;

    public CardAdapter(HashMap<Integer, String> thisClass, int nClass, Context context) {
        super();
        this.thisClass = thisClass;
        this.nClass = nClass;
        this.classEditor = new ClassInfo(nClass, context);

        mItems = new ArrayList<>();

        for (int i = 0; i < thisClass.size(); i++) {
            Student student = new Student();
            if (getInitials(i) != null) {
                student.setInitials(getInitials(i));
                student.setColor(generateColor());
                mItems.add(student);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Student student = mItems.get(i);
        viewHolder.cardColor.setBackgroundColor(student.getColor());
        viewHolder.cardInitials.setText(student.getInitials());

        viewHolder.cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItems.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, mItems.size());
                //classEditor.removeStudent(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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

        public RelativeLayout cardColor;
        public TextView cardInitials;
        public ImageView cardDelete, cardAbsent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
            this.cardInitials = (TextView) itemView.findViewById(R.id.student_name);
            this.cardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
            this.cardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
        }
    }
}
