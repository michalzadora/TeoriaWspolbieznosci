public class QueuePrints implements Runnable {
    private PrintersMonitor printersMonitor;
    private int id;
    public QueuePrints(PrintersMonitor printersMonitor, int i) {
        this.printersMonitor = printersMonitor;
        this.id = i;
    }

    @Override
    public void run(){
        int i = 0;
        while (i != 5) {
            try{
                createPrint();
                int printerID = this.printersMonitor.reservePrinter();
                System.out.println("Printer nr. " + printerID + " is reserved by thread nr. " + id);
                Thread.sleep(1000);
                this.printersMonitor.print(printerID, id);
                this.printersMonitor.releasePrinter(printerID);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    void createPrint() throws InterruptedException {
        Thread.sleep(100);
    }

}
