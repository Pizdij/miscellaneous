import java.util.Arrays;
import java.util.Scanner;

public class ReverseSumAbs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] mat = new int[1][];
        int cnt_line = 0;
        int mcc = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                if (cnt_line == mat.length) {
                    mat = Arrays.copyOf(mat, mat.length * 2);
                }
                mat[cnt_line] = new int[0];
                cnt_line++;
                continue;
            }
            Scanner sc2 = new Scanner(line);
            int[] line_mat = new int[1];
            int cnt_len_line = 0;

            while (sc2.hasNextInt()) {
                int element = Math.abs(sc2.nextInt());
                if (cnt_len_line == line_mat.length) {
                    line_mat = Arrays.copyOf(line_mat, line_mat.length * 2);
                }
                line_mat[cnt_len_line] = element;
                cnt_len_line++;
            }
            if (cnt_line == mat.length) {
                mat = Arrays.copyOf(mat, mat.length * 2);
            }
            mat[cnt_line] = Arrays.copyOf(line_mat, cnt_len_line);
            mcc = Math.max(mcc, cnt_len_line);
            cnt_line++;
        }
        mat = Arrays.copyOf(mat, cnt_line);
        int[] ans_for_line = new int[cnt_line];
        int[] ans_for_len_line = new int[mcc];
        for (int i = 0; i < cnt_line; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                ans_for_line[i] += mat[i][j];
                ans_for_len_line[j] += mat[i][j];
            }
        }
        for (int i = 0; i < cnt_line; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(ans_for_line[i] + ans_for_len_line[j] - mat[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

