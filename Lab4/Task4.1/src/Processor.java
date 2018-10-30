import java.util.Random;

public class Processor implements Runnable {
    private Buffer buffer;
    private Random generator = new Random();
    private final int ID;
    private final int bufferSize;

    public Processor(int ID, Buffer buffer, int bufferSize) {
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
                buffer.setResource(resource, tempResource + 1);
                buffer.releaseResource(resource, ID);
                System.out.println(String.format("Resources ID: %d was processed by Processor: %d.", resource, this.ID));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

