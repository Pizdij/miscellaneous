package game;

public class RhombusTicTac extends TicTacToeBoard {
    public RhombusTicTac(int n, int m, int k, int p) {
        super(2 * n - 1, 2 * m - 1, k, p);
    }

    @Override
    int emptyCells() {
        return ((n + 1) / 2) * (n + 1) / 2;
    }

    @Override
    boolean isValidPosition(int i, int j) {
        int absRowInd = Math.abs(i - ((n + 1) / 2 - 1));
        int absColInd = Math.abs(j - ((n + 1) / 2 - 1));
        return (absRowInd + absColInd) <= ((n + 1) / 2 - 1) && (((n + 1) / 2) % 2 != (i + j) % 2);
    }
    
}
