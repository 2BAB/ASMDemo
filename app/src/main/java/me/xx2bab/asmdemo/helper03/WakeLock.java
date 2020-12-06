package me.xx2bab.asmdemo.helper03;

public final class WakeLock {

    public void acquire() {
        System.out.println("WakeLock acquire");
    }

    public void acquire(long timeout) {
        System.out.println("WakeLock acquire with timeout: " + timeout);
    }

    public void release() {
        System.out.println("WakeLock release");
    }

    public void release(int flags) {
        System.out.println("WakeLock release with flags: " + flags);
    }

}