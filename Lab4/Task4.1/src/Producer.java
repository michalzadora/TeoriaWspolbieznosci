import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;
    private Random generator = new Random();
    private final int ID;
    private final int bufferSize;

    public Producer(int ID, Buffer buffer, int bufferSize) {
        this.buffer = buffer;
        this.ID = ID;
        this.bufferSize = bufferSize;
    }


    @Override
    public void run() {
        try {
            for (int resource = 0; resource < bufferSize; resource++) {
                buffer.getResource(resource, ID);
                Thread.sleep(Math.abs(generator.nextInt() % 100));
                buffer.setResource(resource, 0);
                buffer.releaseResource(resource, ID);
                System.out.println(String.format("Producer ID: %d produced resource %d", resource, ID));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
