package com.dirinc.classgrouper.Info;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

public class ClassInfo {

    public int nClass;
    public HashMap<Integer, String> thisClass = new HashMap<>();
    private SharedPreferences sharedPreferences;
    private int cardColor;
    private String cardName;
    private String cardStudentCount;

    public ClassInfo(int nClass, Context context) {
        this.nClass = nClass;
        sharedPreferences = context.getSharedPreferences("class" + nClass, 0);
        /*
        @SuppressWarnings("unchecked")
        // Dont panic, <String, Integer> is only temporary...
        HashMap<String, Integer> map = (HashMap<String, Integer>) sharedPreferences.getAll();
        for (String s : map.keySet()) {
            String value = String.valueOf(map.get(s));
            thisClass.put(Integer.parseInt(s), value);
        } */
    }

    public HashMap<Integer, String> getMap() {
        return thisClass;
    }

    public void setMap(HashMap<Integer, String> newMap) {
        this.thisClass = newMap;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String classTitle = sharedPreferences.getString("title", null); // Save me from the monster!

        editor.clear(); //Flush
        editor.apply();

        editor.putString("title", classTitle);
        editor.apply();

        for (int x = 0; x <= newMap.size(); x++) {
            editor.putString(String.valueOf(x), newMap.get(x));
        }
        editor.apply();
    }

    public void removeStudent(int student) {
        Log.d("CLASS", "Should remove student " + (student + 1) +
                ", but actually purging " + thisClass.get(student));
        thisClass.remove(student);
        setMap(thisClass);
    }

    public ClassInfo setCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public String getCardName() {
        return cardName;
    }

    public ClassInfo setCardStudentCount(String cardStudentCount) {
        this.cardStudentCount = cardStudentCount;
        return this;
    }

    public String getCardStudentCount() {
        return cardStudentCount;
    }

    public ClassInfo setCardColor(int cardColor) {
        this.cardColor = cardColor;
        return this;
    }

    public int getCardColor() {
        return cardColor;
    }
}
