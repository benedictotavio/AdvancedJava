package services.salesforce;

public class SalesforceService {

    public SalesforceService() {
    }

    public void send() {
        ThreadService ts = new ThreadService(
                () -> {
                    System.out.println(
                            "Thread in SalesforceService: " + Thread.currentThread().getName());
                    System.out.println("Enviando dados para o Salesforce");
                });
    }
}
