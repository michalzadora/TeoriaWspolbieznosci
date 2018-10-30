import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferCell {
    private int prevID = -1;
    private final Lock lock = new ReentrantLock();
    private final Condition wait = lock.newCondition();
    private int resource = 0;


    int getResource(int ID) throws InterruptedException {
        lock.lock();
        while (prevID + 1 != ID) {
            wait.await();
        }
        return resource;
    }

    void setResource(int resource) {
        this.resource = resource;
    }

    void releaseResource(int ID) {
        this.prevID = ID;
        wait.signalAll();
        lock.unlock();
    }


}
