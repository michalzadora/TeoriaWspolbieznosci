import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mandelbrot extends JFrame {

    private static final boolean VISIBILITY = true;
    private final int MAX_ITER = 1000;
    private final double ZOOM = 150;
    private BufferedImage I;

    public Mandelbrot(ExecutorService pool) {
        super("Mandelbrot Set");
        try {
            prepareJFrame();
            long timestamp1 = System.nanoTime();
            List<Future<int[]>> futures = new ArrayList<>();

            for (int y = 0; y < getHeight(); y++) {
                Callable<int[]> rowCalculator = new RowCalculator(y, getWidth(), ZOOM, MAX_ITER);
                Future<int[]> future = pool.submit(rowCalculator);
                futures.add(future);
            }
            for (Future f : futures) {
                while (!f.isDone()) ;
            }
            long timestamp2 = System.nanoTime();
            printMandelbrot(futures);
        }catch (Exception e){

        }
    }

    private void printMandelbrot(List<Future<int[]>> futures) throws InterruptedException, java.util.concurrent.ExecutionException {
        for (int y = 0; y < getHeight(); y++) {
            I.setRGB(0, y, getWidth(), 1, futures.get(y).get(), 0, 1);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }


    private void prepareJFrame() {
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args){
        for(int i=0 ;i<1 ; i++) {
            new Mandelbrot(Executors.newSingleThreadExecutor()).setVisible(VISIBILITY);
        }
    }

}
