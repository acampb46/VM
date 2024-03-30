class Optimal extends PageReplacement {

    private static final int MAX_INTEGER_VALUE = Integer.MAX_VALUE;
    private final int[] logestUsed;

    public Optimal(int frames) {
        super(frames);
        logestUsed = new int[frames];
        for (int i = 0; i < frames; i++) {
            logestUsed[i] = MAX_INTEGER_VALUE;
        }
    }

    @Override
    public void getPage(int page) {
        pages.add(page);

        for (int i = 0; i < frames; i++) {
            output[i].clear();
            output[i].add(0);
        }
        pageFaults.clear();

        for (int i = 0; i < pages.size(); i++) {
            int nextPage = pages.get(i);
            for (int j = 0; j < frames; j++) {
                int current = output[j].get(i);
                boolean usedAgain = false;
                for (int k = i + 1; k < pages.size(); k++) {
                    if (pages.get(k) == current) {
                        usedAgain = true;
                        logestUsed[j] = k - i;
                        break;
                    }
                }
                if (!usedAgain) {
                    logestUsed[j] = MAX_INTEGER_VALUE;
                }
            }
            int index = 0;
            int longest = 0;
            for (int j = 0; j < frames; j++) {
                if (logestUsed[j] > longest) {
                    longest = logestUsed[j];
                    index = j;
                }
            }
            boolean isCached = false;
            for (int j = 0; j < frames; j++) {
                if (output[j].get(i) == nextPage) {
                    isCached = true;
                    pageFaults.add(false);
                    break;
                }
            }
            if (!isCached) {
                output[index].remove(i);
                output[index].add(nextPage);
                pageFaults.add(true);
            }
            for (int j = 0; j < frames; j++) {
                output[j].add(output[j].get(i));
            }
        }
    }
}