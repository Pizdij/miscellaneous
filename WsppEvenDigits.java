import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppEvenDigits {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java WordStatInput <inputFile> <outputFile>");
            return;
        }
        processFile(args[0], args[1]);
    }

    private static void processFile(String inFile, String outFile) {
        Map<String, IntList> wordCount = new LinkedHashMap<>();
        CastScanner scanner;
        try {
            scanner = new CastScanner(new File(inFile));
            try {
                int lineNumber = 1;
                scanner.isDigituser(true);
                scanner.isSlash(true);
                IntList.modNeed(2);
                while (scanner.hasNext()) {
                    int wordIndexInLine = 1;
                    while (scanner.hasNextInLine()) {
                        String word = scanner.nextWord().toLowerCase();
                        if (!word.isEmpty()) {
                            IntList indices = wordCount.get(word);
                            if (indices == null) {
                                indices = new IntList();
                                wordCount.put(word, indices);
                            }
                            indices.add(wordIndexInLine, lineNumber);
                            wordIndexInLine++;
                        }
                    }
                    lineNumber++;
                }
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8))) {
            for (Map.Entry<String, IntList> entry : wordCount.entrySet()) {
                out.write(entry.getKey() + " " + entry.getValue().toString());
                out.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing the output file: " + e.getMessage());
        }
    }
}
