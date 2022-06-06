package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Deadlock {
    Object a = new Object();
    Object b = new Object();

    public void test() {
        while (true) {
            synchronized (a) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println("a-b");
                }
            }
        }
    }

    public void test1() {
        while (true) {
            synchronized (b) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.out.println("b-a");
                }
            }
        }
    }

    public static void main(String[] args) {
        Deadlock deadlock = new Deadlock();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                deadlock.test();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                deadlock.test1();
            }
        });
        executorService.shutdown();
    }
}
