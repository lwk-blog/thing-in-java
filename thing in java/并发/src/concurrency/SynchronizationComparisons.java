package concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class Accumulator {
    public static long cycles = 5000L;
    private static final int N = 4;
    private static ExecutorService executorService = Executors.newFixedThreadPool(N * 2);
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(N * 2 + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long duration = 0;
    protected String id = "error";
    protected static final int SIZE = 100000;
    protected static int[] preLoaded = new int[SIZE];

    static {
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preLoaded[i] = random.nextInt();
        }
    }

    public abstract void accumulate();

    public abstract long read();

    private class Modifier implements Runnable {
        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                accumulate();
            }
        }
    }

    private class Reader implements Runnable {
        private volatile long value;

        @Override
        public void run() {
            for (long i = 0; i < cycles; i++) {
                value = read();
            }
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void timedTest() {
        long time = System.nanoTime();
        for (int i = 0; i < N; i++) {
            executorService.execute(new Modifier());
            executorService.execute(new Reader());
        }
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        duration = System.nanoTime() - time;
        System.out.printf("%%13s: %13d\n", id, duration);
    }

    public static void repotr(Accumulator acc1, Accumulator acc2) {
        System.out.printf("");
    }
}
public class SynchronizationComparisons {
}
