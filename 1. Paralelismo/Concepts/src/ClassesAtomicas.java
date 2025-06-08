import java.util.concurrent.atomic.AtomicInteger;

public class ClassesAtomicas {
    static AtomicInteger contador = new AtomicInteger(0);

    public static void main(String[] args) {
        var mr = new MyRunnable();
        
        var t1 = new Thread(mr);
        var t2 = new Thread(mr);
        var t3 = new Thread(mr);

        t1.start();
        t2.start();
        t3.start();
    }


    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Contador: " + contador.incrementAndGet());
        }
    }
}
