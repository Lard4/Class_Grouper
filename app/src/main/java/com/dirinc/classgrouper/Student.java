package com.dirinc.classgrouper;

public class Student {
    private String initials;
    private int color;

    /*
     * Set methods shall return 'this' because
     * #MethodChaining is more fun.
     */

    public String getInitials() {
        return initials;
    }

    public Student setInitials(String initials) {
        this.initials = initials;
        return this;
    }

    public int getColor() {
        return color;
    }

    public Student setColor(int color) {
        this.color = color;
        return this;
    }
}
