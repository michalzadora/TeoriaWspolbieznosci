public class Main {
    public static void main(String[] args) {
        int amountOfPairs = 10;
        Waiter waiter = new Waiter(amountOfPairs);

        for (int i = 0; i < amountOfPairs * 2; i++) {
            Person task = new Person(waiter, i / 2, i);
            Thread t = new Thread(task);
            t.start();
        }


    }
}
