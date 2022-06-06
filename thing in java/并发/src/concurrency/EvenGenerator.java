package concurrency;

public class EvenGenerator extends IntGenerator{
    private int currentValue = 0;

    @Override
    public synchronized int next() {
        ++currentValue;//非原子操作，中断
        Thread.yield();
        ++currentValue;
        return currentValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
