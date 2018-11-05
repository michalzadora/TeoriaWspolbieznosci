import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int bufferSize;
    private int maxSize;
    private Lock lock = new ReentrantLock();
    private Condition addCond = lock.newCondition();
    private Condition removeCond = lock.newCondition();


    public Buffer(int max) {
        this.maxSize = 2 * max;
        this.bufferSize = 0;
    }

    public void add(int quan) throws InterruptedException {
        lock.lock();
        while (bufferSize + quan > this.maxSize) {
            addCond.await();
        }
        this.bufferSize += quan;
        removeCond.signal();
        lock.unlock();
    }

    public void remove(int quan) throws InterruptedException {
        lock.lock();
        while (quan > this.bufferSize) {
            removeCond.await();
        }
        this.bufferSize -= quan;
        addCond.signal();
        lock.unlock();
    }


}
