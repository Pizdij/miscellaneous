//package game;
//
//import java.util.Arrays;
//import java.util.Map;
//
//public abstract class TicTacToeBoard implements Board, Position {
//    private static final Map<Cell, Character> SYMBOLS = Map.of(
//            Cell.X, 'X',
//            Cell.O, 'O',
//            Cell.E, '.'
//    );
//
//    private final Cell[][] cells;
//    private Cell turn;
//    private int isEmpty;
//    protected final int n;
//    protected final int m;
//    private final int k;
//    private final int p;
//    //note: extra memory
//    private int checkRowHigh = 0;
//    private int checkColHigh = 0;
//    private int checkRowLow = 0;
//    private int checkColLow = 0;
//    private int howMoves = 0;
//
//    public TicTacToeBoard(int n, int m, int k, int p) {
//        this.n = n;
//        this.m = m;
//        this.k = k;
//        this.p = p;
//        isEmpty = emptyCells();
//        this.cells = new Cell[n][m];
//        for (Cell[] row : cells) {
//            Arrays.fill(row, Cell.E);
//        }
//        turn = Cell.X;
//    }
//
//    abstract int emptyCells();
//    abstract boolean isValidPosition(int i, int j);
//
//    @Override
//    public Position getPosition() {
//        return new PublicPosition(this);
//    }
//
//    @Override
//    public Cell getCell() {
//        return turn;
//    }
//
//    @Override
//    public Result makeMove(final Move move) {
//        if (!isValid(move)) {
//            return Result.LOSE;
//        }
//
//        cells[move.getRow()][move.getColumn()] = move.getValue();
//        howMoves++;
//        isEmpty--;
//        int row = move.getRow();
//        int column = move.getColumn();
//        if (checkWin(row, column)) {
//            return Result.WIN;
//        }
//        if (isEmpty == 0) {
//            return Result.DRAW;
//        }
//
//        turn = (turn == Cell.X ? Cell.O : Cell.X);
//        return Result.UNKNOWN;
//    }
//
//    private boolean checkWin(int row, int column) {
//        Cell current = cells[row][column];
//
//        if (checkHorizontal(row, column, current)) {
//            return true;
//        }
//
//        if (checkVertical(row, column, current)) {
//            return true;
//        }
//
//        if (checkDiagonal1(row, column, current)) {
//            return true;
//        }
//
//        return checkDiagonal2(row, column, current);
//    }
//
//    public int getN() {
//        return n;
//    }
//
//    public int getM() {
//        return m;
//    }
//
//    //note: copypaste
//    private boolean checkHorizontal(int row, int column, Cell current) {
//        int inline = 1;
//
//        int time_j = column + 1;
//        while (time_j < m && cells[row][time_j] == current) {
//            inline++;
//            time_j++;
//        }
//
//        time_j = column - 1;
//        while (time_j >= 0 && cells[row][time_j] == current) {
//            inline++;
//            time_j--;
//        }
//
//        return inline >= k;
//    }
//
//    private boolean checkVertical(int row, int column, Cell current) {
//        int inline = 1;
//
//        int time_i = row + 1;
//        while (time_i < n && cells[time_i][column] == current) {
//            inline++;
//            time_i++;
//        }
//
//        time_i = row - 1;
//        while (time_i >= 0 && cells[time_i][column] == current) {
//            inline++;
//            time_i--;
//        }
//
//        return inline >= k;
//    }
//
//    private boolean checkDiagonal1(int row, int column, Cell current) {
//        int inline = 1;
//
//        int time_i = row + 1, time_j = column + 1;
//        while (time_i < n && time_j < m && cells[time_i][time_j] == current) {
//            inline++;
//            time_i++;
//            time_j++;
//        }
//
//        time_i = row - 1;
//        time_j = column - 1;
//        while (time_i >= 0 && time_j >= 0 && cells[time_i][time_j] == current) {
//            inline++;
//            time_i--;
//            time_j--;
//        }
//
//        return inline >= k;
//    }
//
//    private boolean checkDiagonal2(int row, int column, Cell current) {
//        int inline = 1;
//
//        int time_i = row + 1, time_j = column - 1;
//        while (time_i < n && time_j >= 0 && cells[time_i][time_j] == current) {
//            inline++;
//            time_i++;
//            time_j--;
//        }
//
//        time_i = row - 1;
//        time_j = column + 1;
//        while (time_i >= 0 && time_j < m && cells[time_i][time_j] == current) {
//            inline++;
//            time_i--;
//            time_j++;
//        }
//
//        return inline >= k;
//    }
//
//    @Override
//    public boolean isValid(final Move move) {
//        int row = move.getRow();
//        int col = move.getColumn();
//        boolean flag = 0 <= row && row < n
//                && 0 <= col && col < m
//                && cells[row][col] == Cell.E
//                && turn == getCell() && isValidPosition(row, col);
//        if (howMoves < p) {
//            if (howMoves == 0) {
//                checkColHigh = m / 2;
//                checkColLow = checkColHigh;
//                if (col % 2 == 0) {
//                    checkColLow--;
//                }
//                checkRowHigh = m / 2;
//                checkRowLow = checkRowHigh;
//                if (row % 2 == 0) {
//                    checkRowLow--;
//                }
//                return flag && checkRowLow <= row && row <= checkColHigh
//                        && checkColLow <= col && col <= checkColHigh;
//            }
//            checkColHigh++;
//            checkColLow--;
//            checkRowHigh++;
//            checkRowLow--;
//            return flag && checkRowLow <= row && row <= checkColHigh
//                    && checkColLow <= col && col <= checkColHigh;
//        }
//        return flag;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder();
//
//        int cellWidth = String.valueOf(Math.max(n, m)).length() + 1;
//
//        sb.append(" ".repeat(cellWidth));
//        for (int c = 0; c < m; c++) {
//            sb.append(String.format("%-" + cellWidth + "d", c));
//        }
//        sb.append("\n");
//
//        for (int r = 0; r < n; r++) {
//            sb.append(String.format("%-" + cellWidth + "d", r));
//            for (int c = 0; c < m; c++) {
//                if (isValidPosition(r, c)) {
//                    sb.append(String.format("%-" + cellWidth + "s", SYMBOLS.get(cells[r][c])));
//                } else {
//                    sb.append(" ".repeat(cellWidth));
//                }
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
//}


package game;

import java.util.Arrays;
import java.util.Map;

public abstract class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private Cell turn;
    private int isEmpty;
    protected final int n, m, k, p;

    private int checkRowHigh, checkColHigh, checkRowLow, checkColLow, howMoves;

    public TicTacToeBoard(int n, int m, int k, int p) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.p = p;
        isEmpty = emptyCells();
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    abstract int emptyCells();
    abstract boolean isValidPosition(int i, int j);

    @Override
    public Position getPosition() {
        return new PublicPosition(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) return Result.LOSE;

        int row = move.getRow(), col = move.getColumn();
        cells[row][col] = move.getValue();
        isEmpty--; howMoves++;

        if (checkWin(row, col)) return Result.WIN;
        if (isEmpty == 0) return Result.DRAW;

        turn = (turn == Cell.X) ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private boolean checkWin(int row, int col) {
        Cell current = cells[row][col];
        return checkLine(row, col, current, 0, 1) ||  // Horizontal
                checkLine(row, col, current, 1, 0) ||  // Vertical
                checkLine(row, col, current, 1, 1) ||  // Diagonal \
                checkLine(row, col, current, 1, -1);   // Diagonal /
    }

    private boolean checkLine(int row, int col, Cell current, int deltaRow, int deltaCol) {
        int count = 1;
        count += countCells(row, col, current, deltaRow, deltaCol);
        count += countCells(row, col, current, -deltaRow, -deltaCol);
        return count >= k;
    }

    private int countCells(int row, int col, Cell current, int deltaRow, int deltaCol) {
        int count = 0, r = row + deltaRow, c = col + deltaCol;
        while (isInBounds(r, c) && cells[r][c] == current) {
            count++; r += deltaRow; c += deltaCol;
        }
        return count;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < m;
    }

    @Override
    public boolean isValid(final Move move) {
        int row = move.getRow(), col = move.getColumn();
        if (!(isInBounds(row, col) && cells[row][col] == Cell.E && turn == getCell() && isValidPosition(row, col))) {
            return false;
        }

        if (howMoves < p) {
            if (howMoves == 0) {
                initializeBounds(row, col);
            } else {
                expandBounds();
            }
            return row >= checkRowLow && row <= checkRowHigh && col >= checkColLow && col <= checkColHigh;
        }
        return true;
    }

    private void initializeBounds(int row, int col) {
        checkColHigh = checkRowHigh = m / 2;
        checkColLow = (col % 2 == 0) ? checkColHigh - 1 : checkColHigh;
        checkRowLow = (row % 2 == 0) ? checkRowHigh - 1 : checkRowHigh;
    }

    private void expandBounds() {
        checkColHigh++; checkColLow--; checkRowHigh++; checkRowLow--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int cellWidth = String.valueOf(Math.max(n, m)).length() + 1;

        sb.append(" ".repeat(cellWidth));
        for (int c = 0; c < m; c++) sb.append(String.format("%-" + cellWidth + "d", c));
        sb.append("\n");

        for (int r = 0; r < n; r++) {
            sb.append(String.format("%-" + cellWidth + "d", r));
            for (int c = 0; c < m; c++) {
                sb.append(isValidPosition(r, c)
                        ? String.format("%-" + cellWidth + "s", SYMBOLS.get(cells[r][c]))
                        : " ".repeat(cellWidth));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
