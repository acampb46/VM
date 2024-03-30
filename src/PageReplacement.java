import java.util.ArrayList;

abstract class PageReplacement {
    protected final int frames;
    protected final ArrayList<Boolean> pageFaults;
    protected final ArrayList<Integer> pages;
    protected final ArrayList<Integer>[] output;

    public PageReplacement(int frames) {
        this.frames = frames;
        pageFaults = new ArrayList<>();
        pages = new ArrayList<>();
        output = new ArrayList[frames];
        for (int i = 0; i < frames; i++) {
            output[i] = new ArrayList<>();
        }
    }

    public abstract void getPage(int page);

    public int getTotalPageFaults() {
        int total = 0;
        for (Boolean pageFault : pageFaults) {
            if (pageFault) {
                total++;
            }
        }
        return total;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        int splits = (pages.size() / 26) + 1;
        for (int i = 0; i < splits; i++) {
            for (int j = 0; j < 26; j++) {
                int index = (i * 26) + j;
                if (pages.size() > index) {
                    str.append(String.format("%3d", pages.get(index)));
                }
            }
            str.append("\n");
            for (int j = 0; j < 26; j++) {
                int index = (i * 26) + j;
                if (pages.size() > index) {
                    str.append("___");
                }
            }
            for (int l = 0; l < frames; l++) {
                str.append("\n");
                for (int j = 0; j < 26; j++) {
                    int index = (i * 26) + j;
                    if (pageFaults.size() > index) {
                        if (pageFaults.get(index)) {
                            str.append(String.format("%3d", output[l].get(index)));
                        } else {
                            str.append(" ");
                        }
                    }
                }
            }
            str.append("\n\n");
        }
        return str.toString();
    }
}