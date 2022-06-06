package concurrency;

public class BasicThreads {
    public static void main(String[] args) {
        Thread thread = new Thread(new LiftOff());
        System.out.println("start");
        thread.start();
        System.out.println("Waiting for LiftOff!");
    }
}
