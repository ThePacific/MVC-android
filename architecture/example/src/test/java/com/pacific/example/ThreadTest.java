package com.pacific.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    private String currentThread = "A";

    String[] alphabet = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"};

    String[] zh = new String[]{
            "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十",
            "三十一", "三十二", "三十三", "三十四", "三十五", "三十六", "三十七", "三十八", "三十九", "四十",
            "四十一", "四十二", "四十三", "四十四", "四十五", "四十六", "四十七", "四十八", "四十九", "五十",
            "五十一", "五十二"};

    class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 26; i++) {
                    while (!Thread.currentThread().getName().equals(currentThread)) {
                        conditionA.await();
                    }

                    int seed = i * 4;

                    System.out.println(seed + 1);
                    System.out.println(seed + 2);
                    System.out.println(seed + 3);
                    System.out.println(seed + 4);

                    currentThread = "B";
                    conditionB.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class ThreadB implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 26; i++) {
                    while (!Thread.currentThread().getName().equals(currentThread)) {
                        conditionB.await();
                    }

                    int seedIndex = i * 2;
                    System.out.println(zh[seedIndex]);
                    System.out.println(zh[seedIndex + 1]);
                    currentThread = "C";
                    conditionC.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class ThreadC implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 26; i++) {
                    while (!Thread.currentThread().getName().equals(currentThread)) {
                        conditionC.await();
                    }
                    System.out.println(alphabet[i]);
                    currentThread = "A";
                    conditionA.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void doMain() {
        ThreadTest threadTest = new ThreadTest();
        Thread threadA = new Thread(threadTest.new ThreadA());
        Thread threadB = new Thread(threadTest.new ThreadB());
        Thread threadC = new Thread(threadTest.new ThreadC());
        threadA.setName("A");
        threadB.setName("B");
        threadC.setName("C");
        threadA.start();
        threadB.start();
        threadC.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
