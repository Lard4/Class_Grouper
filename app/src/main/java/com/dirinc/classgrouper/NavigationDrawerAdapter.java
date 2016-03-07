package com.dirinc.classgrouper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> data;
    private NavigationDrawerCallbacks navigationDrawerCallbacks;
    private View selectedView;
    private int selectedPosition;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        this.data = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return navigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        this.navigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.itemView.setClickable(true);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedView != null) {
                    selectedView.setSelected(false);
                }

                selectedPosition = viewHolder.getAdapterPosition();
                v.setSelected(true);
                selectedView = v;

                if (navigationDrawerCallbacks != null)
                    navigationDrawerCallbacks.onNavigationDrawerItemSelected(viewHolder.getAdapterPosition());
                }
            }
        );
        viewHolder.itemView.setBackgroundResource(R.drawable.row_selector);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(data.get(i).getText());
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
                data.get(i).getDrawable(), null, null, null);

        if (selectedPosition == i) {
            if (selectedView != null) {
                selectedView.setSelected(false);
            }
            selectedPosition = i;
            selectedView = viewHolder.itemView;
            selectedView.setSelected(true);
        } else {
            if (selectedView != null) {
                selectedView.setSelected(false);
            }
        }
    }


    public void selectPosition(int position) {
        selectedPosition = position;
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}