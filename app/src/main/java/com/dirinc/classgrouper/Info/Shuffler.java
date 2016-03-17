package com.dirinc.classgrouper.Info;

import java.util.HashMap;

public class Shuffler {

    private HashMap<Integer, String> classMap = new HashMap<>();

    public Shuffler(HashMap<Integer, String> classMap) {
        this.classMap = classMap;
    }

    public void shuffle(int groups) {
        int students = classMap.size();

        printGroups(groups, students);
    }

    public void printGroups(int groups, int students) {
        int studentsPerGroup = (students / groups);
        int counter = 1;

        // Even if the groups are uneven, this is our starting point
        for (int nGroups = groups; nGroups > 0; nGroups--) {
            classMap.put(nGroups, /*studentsPerGroup*/ null);
        }

        // Handle uneven groups
        if (students % groups != 0) {
            int studentsLeftOver = (students % groups);

            while (studentsLeftOver > 0) {
                classMap.put(counter, /*(studentsPerGroup + 1)*/ null);
                studentsLeftOver--;
                counter++;
            }
        }
    }

}
