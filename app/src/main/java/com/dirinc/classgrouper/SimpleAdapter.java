package com.dirinc.classgrouper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    List<String> mItems;
    HashMap<Integer, String> thisClass;

    public SimpleAdapter(HashMap<Integer, String> thisClass) {
        super();
        this.thisClass = thisClass;
        mItems = new ArrayList<String>();

        for (int i = 0; i < thisClass.size(); i++) {
            mItems.add(thisClass.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.cardColor.setBackgroundColor(generateColor());
        viewHolder.cardInitials.setText(getInitials(i));

        /*
        viewHolder.cardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.cardAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }

    public String getInitials(int key) {
        String name = getStudentName(key).replaceAll("[.,]", "");
        String initials = "";

        for(String s : name.split(" ")) {
            initials += s.charAt(0);
        }

        return initials;
    }

    public String getStudentName(int key) {
        return thisClass.get(key);
    }

    public int generateColor() {
        Random rand = new Random();
        return Color.argb(255,              //Opacity
                rand.nextInt(156) + 100,    //R
                rand.nextInt(156) + 100,    //G
                rand.nextInt(156) + 100);   //B
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public RelativeLayout cardColor;
        public TextView cardInitials;
        public ImageView cardDelete, cardAbsent;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.student_card);
            this.cardColor = (RelativeLayout) itemView.findViewById(R.id.card_layout);
            this.cardInitials = (TextView) itemView.findViewById(R.id.student_name);
            this.cardDelete = (ImageView) itemView.findViewById(R.id.student_delete);
            this.cardAbsent = (ImageView) itemView.findViewById(R.id.student_absent);
        }
    }
}
