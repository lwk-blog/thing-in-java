package concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean capture = lock.tryLock();
        try {
            System.out.println("tryLock(): " + capture);
        } finally {
            if (capture) lock.unlock();
        }
    }

    public void timed() {
        boolean capture = false;

        try {
            capture = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("lock.tryLock(2, TimeUnit.SECONDS): " + capture);
        } finally {
            if (capture) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();
        new Thread() {
            {setDaemon(true);}

            @Override
            public void run() {
                attemptLocking.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();
        Thread.sleep(100);
        attemptLocking.untimed();
        attemptLocking.timed();
    }
}
