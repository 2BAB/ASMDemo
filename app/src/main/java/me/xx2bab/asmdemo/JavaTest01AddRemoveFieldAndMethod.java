package me.xx2bab.asmdemo;

import java.lang.reflect.Method;

public class JavaTest01AddRemoveFieldAndMethod {

    public void output() {
        System.out.println("JavaTest-01-AddFieldAndMethod is running.");
        boolean exist = false;
        for (Method m : this.getClass().getMethods()) {
            System.out.println(m.getName());
            if (m.getName().equals("outputTobeRemoved")) {
                exist = true;
                break;
            }
        }
        System.out.println("outputTobeRemoved is " + exist);

    }

    public void outputTobeRemoved() {
        System.out.println("JavaTest-01-AddFieldAndMethod is running, but this message should not be printed.");
    }

}
