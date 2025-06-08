package services.chart;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartService {

    public void executeWithCompletionService(int productId) throws ExecutionException, InterruptedException {
        newCompletionService(
                () -> addChartQuantity(productId));
        newCompletionService(
                () -> increaseValueInChart(productId));
        newCompletionService(
                () -> sendEmail(productId));
    }

    public void executeWithExecutionService(int productId) throws ExecutionException, InterruptedException {
        newExecutionService(
                () -> addChartQuantity(productId));
        newExecutionService(
                () -> increaseValueInChart(productId));
        newExecutionService(
                () -> sendEmail(productId));
    }

    public void executeWithThread(int productId) throws InterruptedException, ExecutionException {
        System.out.println("Iniciando o processo de adicionar produto ao carrinho...");
        newThread(() -> addChartQuantity(productId));
        newThread(() -> increaseValueInChart(productId));
        newThread(() -> sendEmail(productId));
        System.out.println("✔️ Processo de adicionar produto ao carrinho concluído!");
    }

    private void newCompletionService(Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        var cs = CompletableFuture.supplyAsync(
                () -> {
                    runnable.run();
                    return null;
                },
                executorService);
        try {
            cs.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void newExecutionService(Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        var es = executorService.submit(runnable);
        try {
            es.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            thread.interrupt();
        }
    }

    private void sendEmail(int productId) {
        simulaEtapa("Enviar e-mail", productId);
    }

    private void addChartQuantity(int productId) {
        simulaEtapa("Adicionar quantidade ao carrinho", productId);
    }

    private void increaseValueInChart(int productId) {
        simulaEtapa("Aumentar valor do carrinho", productId);
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
