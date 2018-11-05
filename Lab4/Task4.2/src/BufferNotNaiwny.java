import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferNotNaiwny {
    private int bufferSize;
    private int maxSize;
    private ReentrantLock lock = new ReentrantLock();
    private Condition addCond = lock.newCondition();
    private Condition removeCond = lock.newCondition();
    private Condition addCondFirst = lock.newCondition();
    private Condition removeCondFirst = lock.newCondition();

    public BufferNotNaiwny(int max) {
        this.maxSize = 2 * max;
        this.bufferSize = 0;
    }

    public void add(int quan) throws InterruptedException {
        lock.lock();
        if(lock.hasWaiters(addCondFirst))
            addCond.await();
        if (bufferSize + quan > this.maxSize) {
            addCondFirst.await();
        }
        this.bufferSize += quan;
        addCond.signal();
        removeCondFirst.signal();
        lock.unlock();
    }

    public void remove(int quan) throws InterruptedException {
        lock.lock();
        if (lock.hasWaiters(removeCondFirst)) {
            removeCond.await();
        }
        if (quan > this.bufferSize) {
            removeCondFirst.await();
        }
        this.bufferSize -= quan;
        removeCond.signal();
        addCondFirst.signal();
        lock.unlock();
    }
}
