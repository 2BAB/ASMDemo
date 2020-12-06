package me.xx2bab.asmdemo.helper03;

public class WakeLockProxy {

    public static void acquire(WakeLock wakelock) {
        wakelock.acquire();
        System.out.println("WakeLockProxy acquire");
    }

    public static void acquire(WakeLock wakelock, long timeout) {
        wakelock.acquire(timeout);
        System.out.println("WakeLockProxy acquire with timeout: " + timeout);
    }

    public static void release(WakeLock wakelock) {
        wakelock.release();
        System.out.println("WakeLockProxy release");
    }

    public static void release(WakeLock wakelock, int flags) {
        wakelock.release(flags);
        System.out.println("WakeLockProxy release with flags: " + flags);
    }


}
