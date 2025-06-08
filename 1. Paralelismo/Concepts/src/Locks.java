import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(10);
        Runnable r1 = () -> {
            lock.lock(); // Inicia a região crítica - Sincroniza tudo dentro desse espaço
            atomicInteger.incrementAndGet();
            lock.unlock(); // Encerra a região crítica
            System.out
                    .println(String.format("Thread %d ==> %s", atomicInteger.get(), Thread.currentThread().getName()));
        };

        for (int i = 0; i < 10; i++) {
            executors.submit(r1);
        }

        executors.shutdown();
    }
}
