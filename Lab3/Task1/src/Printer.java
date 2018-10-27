public class Printer {
    private int id;

    public Printer(int id) {
        this.id = id;
    }

    void print(int ID) throws InterruptedException{
        System.out.println("Printer nr. " + this.id + " prints something from Thread nr. " + ID);
        Thread.sleep(1000);
    }

    public int getId() {
        return id;
    }
}
