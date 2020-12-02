package me.xx2bab.asmdemo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecordThread extends Thread {

    public RecordThread() {
        super();
        log();
    }

    public RecordThread(Runnable target) {
        super(target);
        log();
    }

    public RecordThread(@Nullable ThreadGroup group, Runnable target) {
        super(group, target);
        log();
    }

    public RecordThread(@NotNull String name) {
        super(name);
        log();
    }

    public RecordThread(@Nullable ThreadGroup group, @NotNull String name) {
        super(group, name);
        log();
    }

    public RecordThread(Runnable target, String name) {
        super(target, name);
        log();
    }

    public RecordThread(@Nullable ThreadGroup group, Runnable target, @NotNull String name) {
        super(group, target, name);
        log();
    }

    public RecordThread(@Nullable ThreadGroup group, Runnable target, @NotNull String name, long stackSize) {
        super(group, target, name, stackSize);
        log();
    }

    private void log() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            System.out.println("A new Thread was initialized @" + this.toString()
                    + " : " + element.getLineNumber() + " " + element.getMethodName());
        }
    }
}
