import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    private static boolean isDash(char ch) {
        int type = Character.getType(ch);
        return type == Character.DASH_PUNCTUATION;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java WordStatInput <inputFile> <outputFile>");
            return;
        }
        processFile(args[0], args[1]);
    }

    public static void processFile(String inputFile, String outFile) {
        Map<String, Integer> wordCount = new LinkedHashMap<>();
        CastScanner scanner;

        try {
            scanner = new CastScanner(new File(inputFile));
            try{
                scanner.isDigituser(false);
                scanner.isSlash(false);

                String word = scanner.nextWord();
                // isEmpty()
                while (!word.isEmpty()) {
                    word = word.toLowerCase();
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    word = scanner.nextWord();
                }
                // :NOTE: finally
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                out.write(entry.getKey() + " " + entry.getValue());
                out.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing the output file: " + e.getMessage());
        }
    }
}
