import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PrintersMonitor {
    Printer[] printers;
    private Queue<Printer> printersQueue;
    final Lock lock = new ReentrantLock();
    final Condition empty = lock.newCondition();

    public PrintersMonitor(int numberOfPrinters) {
        this.printersQueue = new LinkedList<Printer>();
        this.printers = new Printer[numberOfPrinters];
        for (int i = 0; i < numberOfPrinters; i++) {
            Printer printer = new Printer(i);
            printers[i] = printer;
            this.printersQueue.add(printer);
        }
    }

    int reservePrinter() throws InterruptedException {
        lock.lock();
        try {
            while (printersQueue.size() == 0) {
                empty.await();
            }
            Printer p = this.printersQueue.poll();
            return p.getId();
        } finally {
            lock.unlock();
        }
    }

    void releasePrinter(int printer){
        lock.lock();
        Printer p = this.printers[printer];
        printersQueue.add(p);
        empty.signal();
        lock.unlock();
    }


    void print(int printerID) throws InterruptedException {
        this.printers[printerID].print();
    }


}
