class Lru extends PageReplacement {

    private final int[] recentFrames;
    private int index;

    public Lru(int frames) {
        super(frames);
        index = 0;
        recentFrames = new int[frames];
        for (int i = 0; i < frames; i++) {
            recentFrames[i] = -1;
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
                recentFrames[i] = index;
                pageFaults.add(false);
                break;
            }
        }
        if (!isFound) {
            int frameIndex = 0;
            int leastUsedAmount = index;
            for (int i = 0; i < frames; i++) {
                if (leastUsedAmount > recentFrames[i]) {
                    leastUsedAmount = recentFrames[i];
                    frameIndex = i;
                }
            }
            output[frameIndex].remove(index);
            output[frameIndex].add(page);
            recentFrames[frameIndex] = index;
            pageFaults.add(true);
        }
        index++;
    }
}