import com.sun.xml.internal.bind.v2.TODO;

public class Person implements Runnable{
    private final Waiter waiter;
    private final int pairID;

    public Person(Waiter waiter, int pairID) {
        this.waiter = waiter;
        this.pairID = pairID;
    }


    @Override
    public void run() {
        int i = 0;
        while (i != 5) {
            try{
                doThings();
                waiter.reserveTable(pairID);
                eat();
                waiter.releaseTable();
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void doThings() throws InterruptedException {
        Thread.sleep(1000);
    }

    void eat() throws InterruptedException {
        System.out.println("Pair nr. " + this.pairID + " eating.");
        Thread.sleep(1000);
    }

}
