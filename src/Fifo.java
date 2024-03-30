class Fifo extends PageReplacement {

    // data fields
    private int index;
    private int nextFrame;

    public Fifo(int frames) {
        super(frames);
        nextFrame = 0;
        index = 0;
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
                pageFaults.add(false);
                break;
            }
        }
        if (!isFound) {
            output[nextFrame].remove(index);
            output[nextFrame].add(page);
            nextFrame++;
            if (nextFrame >= frames) {
                nextFrame = 0;
            }
            pageFaults.add(true);
        }
        index++;
    }
}
