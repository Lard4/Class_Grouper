package com.dirinc.classgrouper;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> data;
    private NavigationDrawerCallbacks navigationDrawerCallbacks;
    private View selectedView;
    private int selectedPosition;

    // Store IDs for each drawer item
    public HashMap<Integer, String> idMap = new HashMap<>();

    private static final int DRAWER_ITEM_ID_CONSTANT = 1000;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        this.data = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return navigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        this.navigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    public void addItem(String name, Drawable image) {
        data.add(new NavigationItem(name, image));
        notifyItemInserted(getItemCount() + 1);
        notifyDataSetChanged();
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.container.setClickable(true);
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedView != null) {
                    selectedView.setSelected(false);
                }

                Intent intent;
                switch (idMap.get(viewHolder.container.getId())) {
                    case "Home":
                        intent = new Intent(view.getContext(), ActivityMain.class);
                        view.getContext().startActivity(intent);
                        break;
                    case "Settings":
                        intent = new Intent(view.getContext(), ActivitySettings.class);
                        view.getContext().startActivity(intent);
                        break;
                }

                selectedPosition = viewHolder.getAdapterPosition();
                v.setSelected(true);
                selectedView = v;

                if (navigationDrawerCallbacks != null) {
                    navigationDrawerCallbacks.onNavigationDrawerItemSelected(viewHolder.getAdapterPosition());
                }
            }
        });
        viewHolder.container.setBackgroundResource(R.drawable.row_selector);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, int i) {
        if (data.get(i).isDivider()) {
            viewHolder.divider.setVisibility(View.VISIBLE);
            viewHolder.textView.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.GONE);
            RelativeLayout.LayoutParams EditLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            EditLayoutParams.setMargins(0, 36, 0, 36);
            viewHolder.container.setLayoutParams(EditLayoutParams);
            return;
        }

        viewHolder.textView.setText(data.get(i).getText());
        viewHolder.imageView.setImageDrawable(data.get(i).getDrawable());
        viewHolder.imageView.setImageAlpha(150);
        viewHolder.container.setId(DRAWER_ITEM_ID_CONSTANT + i);
        this.idMap.put(DRAWER_ITEM_ID_CONSTANT + i, viewHolder.textView.getText().toString());

        if (selectedPosition == i) {
            if (selectedView != null) {
                selectedView.setSelected(false);
            }
            selectedPosition = i;
            selectedView = viewHolder.container;
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
        public ImageView imageView;
        public ImageView divider;
        public RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.item_name);
            this.imageView = (ImageView) itemView.findViewById(R.id.item_image);
            this.divider = (ImageView) itemView.findViewById(R.id.item_divider);
            this.container = (RelativeLayout) itemView.findViewById(R.id.item_container);
        }
    }
}