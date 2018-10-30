public class Main {
    public static void main(String[] args) {
        int id = 0;
        int bufferSize = 100;
        int amountOfProcessors = 5;

// Threads for processors and one for producer and one for consumer.
        Thread[] threads = new Thread[amountOfProcessors + 2];
        Buffer buffer = new Buffer(bufferSize);
        Producer producer = new Producer(id, buffer, bufferSize);
        threads[0] = new Thread(producer);
        id++;
        for (int i = 0; i < amountOfProcessors; i++, id++) {
            Processor processor = new Processor(id, buffer, bufferSize);
            threads[i + 1] = new Thread(processor);
        }
        Consumer consumer = new Consumer(id, buffer, bufferSize);
        threads[amountOfProcessors + 1] = new Thread(consumer);
        for (int i = 0; i < amountOfProcessors + 2; i++) {
            threads[i].start();
        }

    }
}
