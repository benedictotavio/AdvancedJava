import java.util.ArrayList;
import java.util.List;

public class SincronizarColecoes {

    private static List<String> lista = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        System.out.println(
            "Lista: " + lista.size() + " Threads: " + Thread.activeCount()
        );

        var mr = new MyRunnable();

        var t1 = new Thread(mr);
        var t2 = new Thread(mr);
        var t3 = new Thread(mr);
        var t4 = new Thread(mr);
        var t5 = new Thread(mr);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(1000);
        
        System.out.println(
            "Lista: " + lista.size() + " Threads: " + Thread.activeCount()
        );

        for (String s : lista) {
            System.out.println(s);
        }
    }

    public static class MyRunnable implements Runnable {
        private int count = 0;
        public void run() {
            synchronized (lista) {
                lista.add("Thread: " + Thread.currentThread().getName() + " = cleaCount: " + count++);
            }
        }
    }
}
