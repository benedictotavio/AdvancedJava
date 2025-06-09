import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import services.chart.ChartService;
import services.payment.ExecutePayment;
import services.payment.ExecutePaymentImpl;
import services.reports.ReportsService;
import services.salesforce.SalesforceService;

public class App {

    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        processPayment();
    }

    // TODO: Mover para um facade -> PaymentFacade
    private static void processPayment() {
        ExecutorService executors = Executors.newCachedThreadPool();

        // Só permite 2 tarefas simultâneas no recurso "restrito"
        Semaphore semaforo = new Semaphore(3);

        // Protege uma seção crítica exclusiva (mesmo entre os 2 que entram)
        Lock lock = new ReentrantLock();

        // Aguarda 3 tarefas antes de executar o relatório
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            ReportsService reportsService = new ReportsService();
            reportsService.generateReport();
        });

        executors.submit(() -> {
            ExecutePayment executePayment = new ExecutePaymentImpl();
            try {
                semaforo.acquire();
                executePayment.pay(10);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("App.processPayment()" + e.getMessage());
                e.printStackTrace();
            }
            lock.lock();
            counter++;
            System.out.println("App.processPayment() - counter: " + counter);
            lock.unlock();
            semaforo.release();
        });

        executors.submit(
                () -> {
                    ChartService chartService = new ChartService();
                    try {
                        semaforo.acquire();
                        chartService.executeWithCompletionService(10);
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("App.processPayment()" + e.getMessage());
                        e.printStackTrace();
                    }
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        System.out.println("App.processPayment()" + e.getMessage());
                        e.printStackTrace();
                    }
                });

        executors.submit(() -> {
            SalesforceService salesforceService = new SalesforceService();
            try {
                semaforo.acquire();
                salesforceService.send();
            } catch (InterruptedException e) {
                System.out.println("App.processPayment()" + e.getMessage());
                e.printStackTrace();
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("App.processPayment()" + e.getMessage());
                e.printStackTrace();
            }
        });
        
        executors.shutdown();
    }
}
