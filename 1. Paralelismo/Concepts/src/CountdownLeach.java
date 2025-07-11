import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountdownLeach {

    private static volatile int i = 0; // volatile is important here to avoid caching

    public static void main(String[] args) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        Runnable r1 = () -> {
            int j = new Random().nextInt(100);
            int x = i * j;
            System.out.println(
                    i + " * " + j + " = " + x);
        };

        executor.scheduleAtFixedRate(r1, 0, 1, TimeUnit.SECONDS);

        while (true) {
            slleep(1000);
            i = new Random().nextInt(100);
        }
    }

    private static void slleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}