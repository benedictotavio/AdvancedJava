package services.payment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutePaymentImpl implements ExecutePayment {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(6);

    @Override
    public void pay(int productId) {
        var paymentFuture =  executorService.submit(() -> payment(productId));
        try {
            paymentFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static void payment(int productId) {
        if (validateProduct(productId)) System.out.println(
            "Product " + productId + " was sold!!!"
        );
        adicionarProdutoAoCarrinho(productId);
        sendEmail(productId);
    }

    private static boolean validateProduct(int productId) {
        return productId > 0;
    }

    private static void adicionarProdutoAoCarrinho(int productId) {
        simulaEtapa("Adicionar produto ao carrinho", productId);
    }

    private static void sendEmail(int productId) {
        simulaEtapa("Enviar e-mail", productId);
    }

    private static void simulaEtapa(String etapa, int id) {
        try {
            Thread.sleep(5000);
            System.out.println("✔️ " + etapa + " concluída para pedido " + id + " [" + Thread.currentThread().getName() + "]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
