package com.thepacific.mvp;

public class AppLife {
    private static int lifeCounter = 0;

    public static void attachOnStart() {
        lifeCounter++;
    }

    public static void detachOnStop() {
        lifeCounter--;
    }

    public static boolean isForeground() {
        return lifeCounter > 0;
    }

    private AppLife() {
    }
}
