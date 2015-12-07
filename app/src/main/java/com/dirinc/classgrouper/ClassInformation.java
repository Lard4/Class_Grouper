package com.dirinc.classgrouper;

import java.util.HashMap;

public class ClassInformation {
    private MainActivity main;

    public ClassInformation() {
        main = new MainActivity();
        boolean hasClasses = true;
        int numberOfClasses = main.getNumberOfClasses();

        HashMap class1;
        class1 = main.getRoster(1);
    }
}
