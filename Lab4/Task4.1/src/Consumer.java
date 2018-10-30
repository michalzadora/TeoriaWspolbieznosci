import java.util.Random;

public class Consumer implements Runnable {
    private Buffer buffer;
    private Random generator = new Random();
    private final int ID;
    private final int bufferSize;

    public Consumer(int ID, Buffer buffer, int bufferSize) {
        this.buffer = buffer;
        this.ID = ID;
        this.bufferSize = bufferSize;
    }


    @Override
    public void run() {
        try {
            for (int resource = 0; resource < bufferSize; resource++) {
                int tempResource = buffer.getResource(resource, ID);
                Thread.sleep(Math.abs(generator.nextInt() % 100));
                System.out.println(String.format("\n CONSUMER CHECK RESOURCE ID: %d = %d\n", resource, tempResource));
                buffer.releaseResource(resource, ID);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
