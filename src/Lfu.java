class Lfu extends PageReplacement {

    private final int[] frequency;
    private final int[] timesAccessed;
    private final double[] trueFrequency;
    private int index;

    public Lfu(int frames) {
        super(frames);
        index = 0;
        frequency = new int[frames];
        timesAccessed = new int[frames];
        trueFrequency = new double[frames];

        for (int i = 0; i < frames; i++) {
            frequency[i] = 0;
            timesAccessed[i] = 0;
            trueFrequency[i] = 0;
        }
    }

    @Override
    public void getPage(int page) {
        pages.add(page);
        for (int i = 0; i < frames; i++) {
            if (index == 0) {
                output[i].add(0);
            } else {
                int prevPage = output[i].get(index - 1);
                output[i].add(prevPage);
            }
        }
        boolean isFound = false;
        for (int i = 0; i < frames; i++) {
            if (output[i].get(index) == page) {
                isFound = true;
                frequency[i]++;
                pageFaults.add(false);
                break;
            }
        }
        if (!isFound) {
            int frameIndex = 0;
            double leastUsedAmount = 1.0;
            for (int i = frames - 1; i >= 0; i--) {
                if (leastUsedAmount >= trueFrequency[i]) {
                    leastUsedAmount = trueFrequency[i];
                    frameIndex = i;
                }
            }
            output[frameIndex].remove(index);
            output[frameIndex].add(page);
            frequency[frameIndex] = 1;
            timesAccessed[frameIndex] = 0;
            pageFaults.add(true);
        }
        index++;
        for (int i = 0; i < frames; i++) {
            timesAccessed[i]++;
            trueFrequency[i] = frequency[i] / (timesAccessed[i] * 1.0);
        }
    }
}