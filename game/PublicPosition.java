package game;

public class PublicPosition implements Position {
    private final TicTacToeBoard board;

    public PublicPosition(TicTacToeBoard board) {
        this.board = board;
    }

    public int getN() {return board.getN();}

    public int getM() {return board.getM();}

    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    public String toString() {
        return board.toString();
    }
}
