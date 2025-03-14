import java.io.IOException;
import java.util.Arrays;

public class ReverseMaxAbsOctDec {
    // :NOTE: don't throw exceptions from main
    public static void main(String[] args) {
        try {
            CastScanner scanner = new CastScanner(System.in);
            try {
                scanner.isDigituser(true);
                scanner.isSlash(true);
                int[][] numbers = new int[1][];
                int mcc = 0;
                int lineCount = 0;
                while (scanner.hasNext()) {
                    int[] lineArray = new int[1];
                    int currentLength = 0;
                    while (scanner.hasNextInLine()) {
                        String mbNum = scanner.nextWord();

                        if (mbNum.isEmpty()) {
                            continue;
                        }

                        if (currentLength == lineArray.length) {
                            lineArray = Arrays.copyOf(lineArray, lineArray.length * 2);
                        }

                        if (mbNum.endsWith("O") || mbNum.endsWith("o")) {
                            mbNum = mbNum.substring(0, mbNum.length() - 1);
                            lineArray[currentLength] = Integer.parseUnsignedInt(mbNum, 8);
                        } else {
                            lineArray[currentLength] = Integer.parseInt(mbNum);
                        }

                        currentLength++;
                    }


                    if (currentLength > 0 || lineCount > 0) {
                        if (lineCount == numbers.length) {
                            numbers = Arrays.copyOf(numbers, numbers.length * 2);
                        }
                        numbers[lineCount] = Arrays.copyOf(lineArray, currentLength);
                        lineCount++;
                    } else {
                        if (lineCount == numbers.length) {
                            numbers = Arrays.copyOf(numbers, numbers.length * 2);
                        }
                        numbers[lineCount] = new int[0];
                        lineCount++;
                    }
                    mcc = Math.max(mcc, currentLength);
                }

                numbers = Arrays.copyOf(numbers, lineCount);

                int[] to_line = new int[lineCount];
                int[] to_not_line = new int[mcc];

                Arrays.fill(to_not_line, Integer.MIN_VALUE);

                for (int i = 0; i < lineCount; i++) {
                    for (int j = 0; j < numbers[i].length; j++) {
                        if (Math.abs(to_line[i]) < Math.abs(numbers[i][j])) {
                            to_line[i] = numbers[i][j];
                        }
                        if (Math.abs(to_not_line[j]) < Math.abs(numbers[i][j])) {
                            to_not_line[j] = numbers[i][j];
                        }
                    }
                }

                for (int i = 0; i < lineCount; i++) {
                    for (int j = 0; j < numbers[i].length; j++) {
                        int max_value;
                        if (Math.abs(to_line[i]) > Math.abs(to_not_line[j])) {
                            max_value = to_line[i];
                        } else {
                            max_value = to_not_line[j];
                        }
                        System.out.print(Integer.toOctalString(max_value) + "o ");
                    }
                    System.out.println();
                }
                // :NOTE: finally
            } finally {
                scanner.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading the input: " + e.getMessage());
        }
    }
}



