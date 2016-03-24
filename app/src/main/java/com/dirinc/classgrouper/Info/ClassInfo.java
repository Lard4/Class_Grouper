package com.dirinc.classgrouper.Info;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dirinc.classgrouper.Activity.Main;

import java.util.HashMap;

public class ClassInfo {

    public HashMap<Integer, String> thisClass = new HashMap<>();
    private SharedPreferences sharedPreferences;
    private Context context;

    public int nClass;
    private int cardColor;
    private String cardName;
    private String cardStudentCount;

    public ClassInfo(int nClass, Context context) {
        this.nClass = nClass;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("class" + nClass, 0);
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

        this.thisClass = null;

        HashMap<Integer, String> roster = new HashMap<>();
        SharedPreferences classPrefs = context.getSharedPreferences("class" + nClass, 0);
        int nStudent = 0;
        int error = 0;

        while (true) {
            if (classPrefs.contains(String.valueOf(nStudent))) {
                roster.put(nStudent - error, classPrefs.getString(String.valueOf(nStudent), null));
                nStudent++;
            } else {
                error++;
                nStudent++;
                if (error > 5) {
                    break;
                }
            }
        }
        setMap(roster);
        setCardName(classPrefs.getString("title", null))
                .setCardStudentCount(roster.size() + " STUDENTS")
                .setCardColor(Main.generateColor());

        System.out.println();
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
