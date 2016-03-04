package com.dirinc.classgrouper;

import android.content.Context;
import android.content.SharedPreferences;
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

        @SuppressWarnings("unchecked")
        //Dont panic, <String, Integer> is only temporary...
        HashMap<String, Integer> map = (HashMap<String, Integer>) sharedPreferences.getAll();
        for (String s : map.keySet()) {
            String value = String.valueOf(map.get(s));
            thisClass.put(Integer.parseInt(s), value);
        }
    }

    public HashMap<Integer, String> getMap() {
        return thisClass;
    }

    public void setMap(HashMap<Integer, String> newMap) {
        this.thisClass = newMap;

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear(); //Flush
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

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardStudentCount(String cardStudentCount) {
        this.cardStudentCount = cardStudentCount;
    }

    public String getCardStudentCount() {
        return cardStudentCount;
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public int getCardColor() {
        return cardColor;
    }
}
