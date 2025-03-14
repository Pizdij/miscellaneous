import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<int[]> numbersList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                numbersList.add(new int[0]);
                continue;
            }

            List<Integer> lineNumbers = new ArrayList<>();
            StringBuilder currentNumber = new StringBuilder();

            for (char c : line.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    if (!currentNumber.isEmpty()) {
                        try {
                            lineNumbers.add(Integer.parseInt(currentNumber.toString()));
                        } catch (NumberFormatException e) {
                            lineNumbers.add(0);
                        }
                        currentNumber.setLength(0);
                    }
                } else {
                    currentNumber.append(c);
                }
            }

            if (!currentNumber.isEmpty()) {
                try {
                    lineNumbers.add(Integer.parseInt(currentNumber.toString()));
                } catch (NumberFormatException e) {
                    lineNumbers.add(0);
                }
            }

            int[] lineArray = lineNumbers.stream().mapToInt(Integer::intValue).toArray();
            numbersList.add(lineArray);
        }

        for (int i = numbersList.size() - 1; i >= 0; i--) {
            int[] lineArray = numbersList.get(i);
            for (int j = lineArray.length - 1; j >= 0; j--) {
                System.out.print(lineArray[j]);
                if (j > 0) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}
