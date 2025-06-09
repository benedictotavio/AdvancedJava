import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

public class SyncronousQueue {
    public static void main(String[] args) {
        BlockingQueue<String> eventsQueue = new LinkedTransferQueue<>(); // FIFO
        ExecutorService exService = Executors.newCachedThreadPool(); // Cria um pool de threads

        Runnable r1 = () -> {
            try {
                System.out.println("Executando tarefa 1 - " + Thread.currentThread().getName());
                Thread.sleep(1000);
                for (int i = 0; i < 10; i++) {
                    eventsQueue.put("Evento " + i);
                    System.out.println("🛠️  [Produtor] Adicionando evento: " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        exService.submit(r1);

        final int NUM_CONSUMIDORES = 3;

        for (int i = 1; i <= NUM_CONSUMIDORES; i++) {
            int consumidorId = i;
            exService.submit(() -> {
                try {
                    while (true) {
                        String evento = eventsQueue.take();
                        System.out.println("🛠️  [Consumidor " + consumidorId + "] Processando: " + evento);
                        Thread.sleep(2000); // simula tempo de processamento
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        exService.shutdown();
    }
}
