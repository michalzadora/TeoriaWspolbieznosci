import java.math.BigDecimal;
import java.util.Random;


public class Task4_2 {


    public static void main(String[] args) {

        Integer M = Integer.valueOf(args[0]);
        Integer amountOfPaC = Integer.valueOf(args[1]);
        final boolean[] flag = {true};
        Buffer buffer = new Buffer(M);
        BufferNotNaiwny bufferNotNaiwny = new BufferNotNaiwny(M);
        Random gene = new Random();
        TimeMeasurementRepository tmr_naive = new TimeMeasurementRepository(M, amountOfPaC + "_" + M);
        TimeMeasurementRepository tmr = new TimeMeasurementRepository(M, amountOfPaC + "_" + M);
        for (int i = 0; i < amountOfPaC; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int counter = 0;
                    //while (true) {
                    try {
                        for (; counter < 1000; counter++) {
                            Integer produceSize = Math.abs(gene.nextInt(M-1)) + 1;
                            long timestamp1 = System.nanoTime();
                            buffer.add(produceSize);
                            long timestamp2 = System.nanoTime();
                            tmr_naive.addMeasurementPut(produceSize, BigDecimal.valueOf(timestamp2 - timestamp1));
//                            System.out.println("Producer: size  = " + produceSize + " time: " + System.currentTimeMillis());

                            timestamp1 = System.nanoTime();
                            bufferNotNaiwny.add(produceSize);
                            timestamp2 = System.nanoTime();
                            tmr.addMeasurementPut(produceSize, BigDecimal.valueOf(timestamp2 - timestamp1));

                        }
                        System.out.println("Stop production");
                        tmr.printResults("fair");
                        tmr_naive.printResults("naive");
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
                //}
            }).start();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    int counter = 0;
                    while (true) {
                        try {

                            Integer produceSize = Math.abs(gene.nextInt(M-1)) + 1;
                            long timestamp1 = System.nanoTime();
                            buffer.remove(produceSize);
                            long timestamp2 = System.nanoTime();
                            tmr_naive.addMeasurementGet(produceSize, BigDecimal.valueOf(timestamp2 - timestamp1));
//                            System.out.println("Consumer: size  = " + produceSize + " time: " + System.currentTimeMillis());

                            timestamp1 = System.nanoTime();
                            bufferNotNaiwny.remove(produceSize);
                            timestamp2 = System.nanoTime();
                            tmr.addMeasurementGet(produceSize, BigDecimal.valueOf(timestamp2 - timestamp1));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }


}
