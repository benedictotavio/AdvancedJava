import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Semaforos {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        Runnable runnable3 = () -> {
            simularEtapa("Etapa ------------------------> 3");
        };

        CyclicBarrier barrier = new CyclicBarrier(3, runnable3);
        ExecutorService executor = Executors.newFixedThreadPool(12);

        for (int i = 0; i < 10; i++) {
            executor.submit(executeThread());
            executor.submit(executeThread2());
            barrier.await();
            System.out.println("------------------------------------------------");
        }

        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }

    private static Runnable executeThread() {
        return () -> simularEtapa("Etapa ------------------------> 1");
    }

    private static Runnable executeThread2() {
        return () -> simularEtapa("Etapa ------------------------> 2");
    }

    private static void simularEtapa(String etapa) {
        try {
            Thread.sleep(5000);
            System.out.println(
                    "✔️ " + etapa + "  foi concluida na Thread " + " [" + Thread.currentThread().getName() + "]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
