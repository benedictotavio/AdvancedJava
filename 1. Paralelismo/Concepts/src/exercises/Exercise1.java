package exercises;

public class Exercise1 {
    public static void main(String[] args) {
        var mr = new MyRunnable();
        var t1 = new Thread(mr);
        var t2 = new Thread(mr);
        var t3 = new Thread(mr);

        t1.start();
        t2.start();
        t3.start();
    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Contador: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }
}
