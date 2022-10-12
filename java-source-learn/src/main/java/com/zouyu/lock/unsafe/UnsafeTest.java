package com.zouyu.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ZouYu 2022/9/5 13:42
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class UnsafeTest {

    private static final Unsafe unsafe;

    private volatile int state;

    private static final long stateOffset;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public int getState() {
        return state;
    }
    public void add() {
        int stateVolatile;
        do {
            stateVolatile = unsafe.getIntVolatile(this, stateOffset);
        } while (!unsafe.compareAndSwapInt(this, stateOffset, stateVolatile, stateVolatile + 1));
    }

    public void add(int num) {
        int stateVolatile;
        do {
            stateVolatile = unsafe.getIntVolatile(this, stateOffset);
        } while (!unsafe.compareAndSwapInt(this, stateOffset, stateVolatile, stateVolatile + num));
    }



    public static void main(String[] args) throws InterruptedException {
        UnsafeTest unsafeTest = new UnsafeTest();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                unsafeTest.add(2);
            }
        });

        long currentTimeMillis = System.currentTimeMillis();
        thread.start();

        for (int i = 0; i < 10000000; i++) {
            unsafeTest.add();
        }

        thread.join();

        System.out.println(System.currentTimeMillis() - currentTimeMillis);
        System.out.println(unsafeTest.getState());

    }



}

