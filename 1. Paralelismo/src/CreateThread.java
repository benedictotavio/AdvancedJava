public class CreateThread {
    public static void main(String[] args) throws Exception {

        System.out.println(
            "Currrent thread: " + currenThread().getName()
        );

         var runnable = new MyRunnable();
         var thread = new Thread(runnable);

         thread.run(); // Ele vai executar na thread principal

         thread.start(); // Ele vai iniciar uma nova thread

         // thread.start(); // Não vai funcionar pois a thread já foi iniciada uma vez

        // Criar uma thread com o mesmo Runnable
        var thread2 = new Thread(runnable);
        thread2.start();
    }

    public static Thread currenThread() {
        return Thread.currentThread();
    }

    public static Thread newThread() {
        return new Thread();
    }

    public static Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }

    /**
     * 
     * @param message
     * 
     * @return Cria um novo objeto de Thread
     * 
     * Cria um novo objeto de Thread baseando em uma Lambda Function.  
     */
    public static Thread newThread(String message) {
        return new Thread(
            () -> System.out.println(
                message
            )
        );
    }
}