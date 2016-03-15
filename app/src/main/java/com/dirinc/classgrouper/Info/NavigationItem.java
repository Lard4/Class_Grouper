package com.dirinc.classgrouper.Info;

import android.graphics.drawable.Drawable;

public class NavigationItem {
    private String text;
    private Drawable drawable;
    private boolean divider = false;

    public NavigationItem(String text, Drawable drawable) {
        this.text = text;
        this.drawable = drawable;
    }

    public NavigationItem() {
        this.divider = true;
    }

    public boolean isDivider() {
        return divider;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
