public class Main {
    public static void main(String[] args) throws InterruptedException {
        PrintersMonitor printerMonitor = new PrintersMonitor(10);
        int users = 20;

        for (int i = 0; i < users; i++) {
            QueuePrints task = new QueuePrints(printerMonitor, i);
            Thread t = new Thread(task);
            t.start();

        }

    }
}