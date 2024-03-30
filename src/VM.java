import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VM {
    public static void main(String[] args) {
        try {
            Scanner myScan = new Scanner(new File("src/pages.txt"));

            while (myScan.hasNext()) {
                int frameSize = myScan.nextInt();
                PageReplacement fifo = new Fifo(frameSize);
                PageReplacement lru = new Lru(frameSize);
                PageReplacement lfu = new Lfu(frameSize);
                PageReplacement optimal = new Optimal(frameSize);

                while (true) {
                    int page = myScan.nextInt();

                    if (page == -1) {
                        break;
                    } else {
                        fifo.getPage(page);
                        lru.getPage(page);
                        lfu.getPage(page);
                        optimal.getPage(page);
                    }
                }
                System.out.println("FIFO:");
                System.out.println(fifo);
                System.out.println("LRU:");
                System.out.println(lru);
                System.out.println("LFU:");
                System.out.println(lfu);
                System.out.println("Optimal:");
                System.out.println(optimal);

                System.out.println("Using " + frameSize + " frames, the reference string yielded:");
                int fifoPageFault = fifo.getTotalPageFaults();
                int lruPageFault = lru.getTotalPageFaults();
                int lfuPageFault = lfu.getTotalPageFaults();
                int optimalPageFault = optimal.getTotalPageFaults();
                double fifoPercent = (fifoPageFault / (optimalPageFault * 1.0)) * 100.0;
                double lruPercent = (lruPageFault / (optimalPageFault * 1.0)) * 100.0;
                double lfuPercent = (lfuPageFault / (optimalPageFault * 1.0)) * 100.0;
                System.out.println("Scheme #Faults %Optimal");
                System.out.printf("%-8s %-8d %.1f%%%n", "FIFO", fifoPageFault, fifoPercent);
                System.out.printf("%-8s %-8d %.1f%%%n", "LRU", lruPageFault, lruPercent);
                System.out.printf("%-8s %-8d %.1f%%%n", "LFU", lfuPageFault, lfuPercent);
                System.out.printf("%-8s %-8d %d%%%n", "Optimal", optimalPageFault, 100);

                System.out.println("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
