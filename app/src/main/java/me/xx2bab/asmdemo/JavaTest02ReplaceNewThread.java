package me.xx2bab.asmdemo;

public class JavaTest02ReplaceNewThread {

    public void output() {
        Thread t = new Thread();
        t.start();

        Thread t0 = new Thread(() -> {
            System.out.println("JavaTest-02-ReplaceNewThread thread 0 is running.");
        });
        t0.start();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("JavaTest-02-ReplaceNewThread thread 1 is running.");
            }
        });
        t1.start();

        Thread t2 = new Thread("custom-thread-2") {
            @Override
            public void run() {
                super.run();
                System.out.println("JavaTest-02-ReplaceNewThread thread 2 is running.");
            }
        };
        t2.start();

        System.out.println("JavaTest-02-ReplaceNewThread is running.");
    }
    
}
