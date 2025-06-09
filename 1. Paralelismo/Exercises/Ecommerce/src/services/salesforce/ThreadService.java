package services.salesforce;

public class ThreadService extends Thread {

    private Runnable runnable;

    public ThreadService(Runnable runnable) {
        super(runnable);
    }

    @Override
    public void run() {
        runnable.run();
    }
}
