package com.thepacific.mvp;

public class AppLife {
    private static int lifeCounter = 0;

    public static void flagOnStart() {
        lifeCounter++;
    }

    public static void flagOnStop() {
        lifeCounter--;
    }

    public static boolean isForeground() {
        return lifeCounter > 0;
    }

    private AppLife() {
    }
}
