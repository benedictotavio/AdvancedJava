import java.util.concurrent.*;

public class CyclicBarrierSync {

    private static BlockingDeque<Double> deque = new LinkedBlockingDeque<>();

    public static void main(String[] args) {

        Runnable finish = () -> {
            System.out.println("All threads finished");
            double sum = 0;
            sum += deque.poll();
            sum += deque.poll();
            sum += deque.poll();
            System.out.println("Sum is " + sum);
        };

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, finish);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable r1 = () -> {
            deque.add(4323d * 3d);
            await(cyclicBarrier);
        };

        Runnable r2 = () -> {
            deque.add(Math.pow(
                    3d, 20d));
            await(cyclicBarrier);
        };

        Runnable r3 = () -> {
            deque.add(45d * 127d / 12d);
            await(cyclicBarrier);
        };

        executorService.submit(r1);
        executorService.submit(r2);
        executorService.submit(r3);

        executorService.shutdown();
    }

    private static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Barrier reached");
    }
}