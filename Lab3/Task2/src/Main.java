public class Main {
    public static void main(String[] args){
        Waiter waiter = new Waiter(5);

        for (int i = 0; i < 10; i++) {
            Person task = new Person(waiter, i / 2);
            Thread t = new Thread(task);
            t.start();
        }


    }
}
