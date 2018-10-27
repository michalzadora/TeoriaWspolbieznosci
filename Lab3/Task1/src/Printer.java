public class Printer {
    private int id;

    public Printer(int id) {
        this.id = id;
    }

    void print() throws InterruptedException{
        System.out.println("Printer nr. " + this.id + " prints.");
        Thread.sleep(1000);
    }

    public int getId() {
        return id;
    }
}
