package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class Incrementable {
    protected long counter = 0;

    public abstract void increment();
}

class SynchronizingTest extends Incrementable {
    @Override
    public void increment() {
        ++counter;
    }
}

class LockingTest extends Incrementable {
    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            ++counter;
        } finally {
            lock.unlock();
        }
    }
}

public class SimpleMicroBenchmark {
    static long test(Incrementable incrementable) {
        long nanoTime = System.nanoTime();
        for (long i = 0; i < 10000000L; i++) {
            incrementable.increment();
        }
        return System.nanoTime() - nanoTime;
    }

    public static void main(String[] args) {
        long synchTime = test(new SynchronizingTest());
        long lockTime = test(new LockingTest());
        System.out.printf("synchronized: %1$10d\n", synchTime);
        System.out.printf("Lock:         %1$10d\n", lockTime);
        System.out.printf("Lock/synchronized = %1$.3f\n", (double)lockTime / (double) synchTime);
    }
}
