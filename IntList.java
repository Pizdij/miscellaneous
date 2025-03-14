import java.io.IOException;
import java.util.Arrays;

public class IntList {
    private int[] data;
    private int size;
    private int totalWords;
    private int prevLine;
    private int wordsInLine;
    public static int t = 1;
    public IntList() {
        data = new int[1];
        size = 0;
        totalWords = 0;
        prevLine = -1;
        wordsInLine = 0;
    }

    public static void modNeed(int num) throws IOException {
        t = num;
    }

    public void add(int value) {
        if (size == data.length) {
            resize();
        }
        data[size++] = value;
    }

    public void add(int value, int line) {
        if (line != prevLine) {
            wordsInLine = 0;
            prevLine = line;
        }

        wordsInLine++;
        totalWords++;

        if (wordsInLine % t == 0) {
            if (size == data.length) {
                resize();
            }
            data[size++] = value;
        }
    }

    public int size() {
        return size;
    }


    private void resize() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (t != 1){
            sb.append(totalWords).append(" ");
        }
        for (int i = 0; i < size; i++) {
            sb.append(data[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
