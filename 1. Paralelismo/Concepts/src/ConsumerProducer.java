import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerProducer {

    private static final BlockingQueue<Integer> LISTA = new LinkedBlockingDeque<>(5);
    private static volatile boolean isConsuming = true; // o vollatile permite que a variável seja acessada de forma segura por várias threads
    private static volatile boolean isProducing = true; // o vollatile permite que a variável seja acessada de forma segura por várias threads
    private static Lock LOCK = new ReentrantLock();
    private static int number = 0;

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        Runnable producer = () -> {
            while (true) {
                if (isProducing) {
                    LOCK.lock();
                    simulaEtapa("Produzindo um evento", number);
                    System.out.println("Produzindo...");
                    int number = new Random().nextInt(100);
                    LISTA.add(number);
                    if (LISTA.size() == 5) {
                        isProducing = false;
                    }
                    if (LISTA.size() == 1) {
                        isConsuming = true;
                    }
                    number++;
                    LOCK.unlock();
                } else {
                    System.out.println("Aguardando produção...");
                }
            }

        };

        Runnable consumer = () -> {
            while (true) {
                if (isConsuming) {
                    LOCK.lock();
                    System.out.println("Consumindo...");
                    Optional<Integer> optional = LISTA.stream().findFirst();
                    optional.ifPresent(integer -> {
                        LISTA.remove(integer);
                        simulaEtapa("Consumindo um evento", integer);
                        if (LISTA.isEmpty()) {
                            isConsuming = false;
                        }
                        if (LISTA.size() == 4) {
                            isProducing = true;
                        }
                    });
                    LOCK.unlock();
                } else {
                    System.out.println("Aguardando consumo...");
                }
            }
        };

        executorService.submit(producer);
        executorService.submit(consumer);
    }

    private static void simulaEtapa(String etapa, int id) {
        try {
            Thread.sleep(5000);
            System.out.println(
                    "✔️ " + etapa + " concluída para pedido " + id + " [" + Thread.currentThread().getName() + " ]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
