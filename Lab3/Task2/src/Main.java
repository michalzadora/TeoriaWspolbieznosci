public class Main {
    public static void main(String[] args) {
        Waiter waiter = new Waiter(10);

        for (int i = 0; i < 20; i++) {
            Person task = new Person(waiter, i / 2, i);
            Thread t = new Thread(task);
            t.start();
        }


    }
}
