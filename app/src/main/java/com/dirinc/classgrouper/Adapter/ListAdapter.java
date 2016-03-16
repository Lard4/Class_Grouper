package com.dirinc.classgrouper.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dirinc.classgrouper.Info.Student;
import com.dirinc.classgrouper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<String> mDataset = new ArrayList<>();
    private SharedPreferences prefs;

    public ListAdapter(List<String> myDataset, SharedPreferences prefs) {
        mDataset = myDataset;
        this.prefs = prefs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_text, parent, false);

        return new ViewHolder(view, new EditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final int position = holder.getAdapterPosition();

        holder.editTextListener.updatePosition(position);
        holder.studentText.setHint(mDataset.get(position));
        holder.studentNumber.setText(position + 1 + ".");

        if (position != 0) {
            holder.studentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        holder.studentText.setHint(v.getContext().getResources()
                                .getString(R.string.prompt_student_name));
                        setDeleteListener(holder.studentDelete, position);
                        holder.studentText.setOnFocusChangeListener(null);

                        mDataset.add(v.getContext().getResources()
                                .getString(R.string.prompt_new_student_name));
                        notifyItemInserted(position + 1);
                    }
                }
            });
        }

        //Check to make sure they're not deleting their only way of adding a new EditText
        if (!holder.studentText.getHint().equals(holder.studentText.getContext().getResources()
                .getString(R.string.prompt_new_student_name))) {
            setDeleteListener(holder.studentDelete, position);
        }
    }

    private void setDeleteListener(ImageView button, final int position) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.remove(String.valueOf(position));
                editPrefs.apply();

                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
            }
        });
    }

    private void updatePrefs() {
        SharedPreferences.Editor editPrefs =  prefs.edit();

        for (int i = 0; i < mDataset.size(); i++) {
            editPrefs.putString(String.valueOf(i), mDataset.get(i));
        }

        editPrefs.apply();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText studentText;
        public ImageView studentDelete;
        public TextView studentNumber;
        public LinearLayout studentTextWhole;
        public EditTextListener editTextListener;

        public ViewHolder(View itemView, EditTextListener editTextListener) {
            super(itemView);

            this.studentText = (EditText) itemView.findViewById(R.id.student_name);
            this.studentDelete = (ImageView) itemView.findViewById(R.id.student_clear);
            this.studentNumber = (TextView) itemView.findViewById(R.id.student_number);
            this.studentTextWhole = (LinearLayout) itemView.findViewById(R.id.student_name_whole);
            this.editTextListener = editTextListener;
            this.studentText.addTextChangedListener(editTextListener);
        }
    }

    private class EditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset.set(position, charSequence.toString());
            updatePrefs();
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    }
}
