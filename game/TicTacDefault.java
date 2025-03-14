package game;

public class TicTacDefault extends TicTacToeBoard {
    public TicTacDefault(int n, int m, int k, int p) {
        super(n, m, k, p);
    }

    @Override
    int emptyCells() {
        return n * m;
    }

    @Override
    boolean isValidPosition(int i, int j) {
        return 0 <= i && i < n && 0 <= j && j < m;
    }
}
