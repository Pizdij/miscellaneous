import java.io.*;
import java.nio.charset.StandardCharsets;
import java.io.StringReader;

public class CastScanner {
    private static final int BUFFER_SIZE = 1024;
    private final Reader reader;
    private final char[] buffer;
    private int bufferLength = 0;
    private int currentPos = 0;
    private boolean isClosed = false;
    private boolean endOfInput = false;
    private boolean needNum = false;
    private boolean needSlash = false;
    private char linesep = System.lineSeparator().charAt(System.lineSeparator().length() - 1);
    public CastScanner(InputStream in) {
        this(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public CastScanner(File in) throws FileNotFoundException {
        this(new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8));
    }

    public CastScanner(String in) {
        this(new StringReader(in));
    }

    public CastScanner(Reader in) {
        this.reader = in;
        this.buffer = new char[BUFFER_SIZE];
    }

    private void newBuffer() throws IOException {
        bufferLength = reader.read(buffer);
        currentPos = 0;
        if (bufferLength == -1) {
            endOfInput = true;
        }
    }

    public boolean hasNext() throws IOException {
        if (endOfInput) {
            return false;
        }
        if (currentPos >= bufferLength) {
            newBuffer();
        }
        return !endOfInput;
    }

    public boolean hasNextInLine() throws IOException {
        if (currentPos >= bufferLength) {
            newBuffer();
        }
        if (endOfInput) {
            return false;
        }
        char ch = buffer[currentPos];
        if (ch == linesep) {
            currentPos++;
            return false;
        }
        return true;
    }


    public void isDigituser(boolean s) throws IOException {
        needNum = s;
    }

    public void isSlash(boolean s) throws IOException {
        needSlash = s;
    }

    public String nextWord() throws IOException {
        StringBuilder word = new StringBuilder();
        while (true) {
            if (currentPos >= bufferLength) {
                newBuffer();
                if (bufferLength == -1) {
                    if (!word.isEmpty()) {
                        break;
                    }
                    return "";
                }
            }
            char ch;
            if (!needSlash) {
                ch = buffer[currentPos++];
            } else {
                ch = buffer[currentPos];
            }
            if (Character.isLetter(ch) || ch == '\'' || isDash(ch) || (needNum && Character.isDigit(ch))) {
                word.append(ch);
            } else {
                if (!word.isEmpty() || ((ch == linesep) && needSlash)) {
                    break;
                }
            }
            if (needSlash) {
                currentPos++;
            }
        }

        return word.toString();
    }

    private static boolean isDash(char ch) {
        int type = Character.getType(ch);
        return type == Character.DASH_PUNCTUATION;
    }

    public void close() throws IOException {
        if (!isClosed) {
            reader.close();
            isClosed = true;
        }
    }
}
