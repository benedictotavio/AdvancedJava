package exercises;

public class Exercise2 {

    private static int position = 0;
    public static void main(String[] args) {
        var mr = new MyRunnable();
        var t1 = new Thread(mr);
        var t2 = new Thread(mr);

        t1.start();
        t2.start();
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (this) {
                position++;
            }
            System.out.println("Position: " + position);
        }
    }
}