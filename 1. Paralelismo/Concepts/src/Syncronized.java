import java.time.LocalDateTime;

public class Syncronized {
    public static void main(String[] args) {
        var mr = new MyRunnable3();
        var t1 = new Thread(mr);
        var t2 = new Thread(mr);
        var t3 = new Thread(mr);

        t1.start();
        t2.start();
        t3.start();
    }

    public static class MyRunnable implements Runnable {

        private int count = 0;

        public synchronized void run() {
            count++;
            System.out.println(
                    LocalDateTime.now() + "Thread: " + Thread.currentThread().getName() + "Count: " + count);
        }
    }

    public static class MyRunnable2 implements Runnable {
        private int count = 0;

        public void run() {
            synchronized (this) {
                count++;
                System.out.println(
                        LocalDateTime.now() + "Thread: " + Thread.currentThread().getName() + "Count: " + count);
            }
        }
    }

    private static class MyRunnable3 implements Runnable {
        private int count = 0;

        // Nesse caso utilizamo o synchronized para sincronizar o acesso ao objeto count.
        public void run() {
            synchronized (MyRunnable3.class) {
                count++;
            }

            System.out.println(
                    LocalDateTime.now() + "Thread: " + Thread.currentThread().getName() + "Count: " + count);
        }
    }

}
