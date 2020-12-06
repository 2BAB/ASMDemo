package me.xx2bab.asmdemo;

public class JavaTestMain {

    public static void main(String[] args) {
        JavaTest01AddRemoveFieldAndMethod test01 = new JavaTest01AddRemoveFieldAndMethod();
        test01.output();
        JavaTest02ReplaceNewThread test02 = new JavaTest02ReplaceNewThread();
        test02.output();
        JavaTest03ReplaceWakeLockMethodCalling test03 = new JavaTest03ReplaceWakeLockMethodCalling();
        test03.output();
    }

}
