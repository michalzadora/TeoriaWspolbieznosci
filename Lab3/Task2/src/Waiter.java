import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private final Condition waitTable = lock.newCondition();
    private final List<Condition> waitOnSec = new ArrayList<>();
    private Boolean[] firstPeople;
    private Boolean free;
    private Boolean isPairLeft;
    private int reservedByPairID;


    public Waiter(int ammountOfPairs) {
        firstPeople = new Boolean[ammountOfPairs];
        for (int i = 0; i < ammountOfPairs; i++) {
            this.waitOnSec.add(lock.newCondition());
            this.firstPeople[i] = false;
        }
        this.free = true;
        this.isPairLeft = false;
    }


    public void reserveTable(int pairID) throws InterruptedException {
        lock.lock();
        try {
            while (!firstPeople[pairID]) {
                firstPeople[pairID] = true;
//                System.out.println("First part of pair " + pairID + " is waiting for companiero.");
                waitOnSec.get(pairID).await();
            }
            waitOnSec.get(pairID).signal();
            while (!free && reservedByPairID != pairID) {
//                System.out.println("Pair " + pairID + " is waiting for table.");
                waitTable.await();
            }
            this.free = false;
            reservedByPairID = pairID;
        } finally {
            lock.unlock();
        }
    }


    public void releaseTable() {
        lock.lock();
        try {
            if(isPairLeft) {
                firstPeople[reservedByPairID] = false;
                free = true;
                System.out.println("Second person from pair " + this.reservedByPairID + " is leaving from table");
                waitTable.signalAll();
                isPairLeft = false;
            }
            else{
                isPairLeft = true;
                System.out.println("First person from pair " + this.reservedByPairID + " is leaving from table");
            }
        } finally {
            lock.unlock();
        }
    }

}
