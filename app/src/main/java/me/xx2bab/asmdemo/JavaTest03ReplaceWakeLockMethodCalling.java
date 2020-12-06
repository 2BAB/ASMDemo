package me.xx2bab.asmdemo;

import me.xx2bab.asmdemo.helper03.WakeLock;
import me.xx2bab.asmdemo.helper03.WakeLockProxy;

public class JavaTest03ReplaceWakeLockMethodCalling {

    public void output() {
        WakeLock wakeLock = new WakeLock();
        wakeLock.acquire();
        wakeLock.release();
        wakeLock.acquire(1000);
//        WakeLockProxy.acquire(wakeLock, 1000);
        wakeLock.release(0x0002);
    }
    
}
