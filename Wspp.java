import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Wspp {
    public static void main(String[] args){
        if (args.length != 2) {
            System.err.println("Usage: java WordStatInput <inputFile> <outputFile>");
            return;
        }
        processFile(args[0], args[1]);
    }

    private static void processFile(String inFile, String outFile){
        Map<String, IntList> wordCount = new LinkedHashMap<>();
        CastScanner scanner;
        try{
            scanner = new CastScanner(new File(inFile));
            try{
                int i = 1;
                scanner.isDigituser(false);
                scanner.isSlash(true);
                while (scanner.hasNext()){
                    while (scanner.hasNextInLine()){
                        String word = scanner.nextWord().toLowerCase();
                        if (word != ""){
                            if (wordCount.containsKey(word)){
                                wordCount.get(word).add(i);
                            } else{
                                IntList ind = new IntList();
                                ind.add(i);
                                wordCount.put(word, ind);
                            }
                            i++;
                        } else{
                            break;
                        }
                    }
                }
            } finally{
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
                out.write(entry.getKey() + " " + + entry.getValue().size() + " " + entry.getValue().toString());
                out.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing the output file: " + e.getMessage());
        }
    }
}
