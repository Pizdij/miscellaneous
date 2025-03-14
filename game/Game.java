package game;

public class Game {
    private final boolean log;
    private final Player player1, player2;

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        while (true) {
            final int result1 = move(board, player1, 1);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, player2, 2);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        try {
            Move move;
            do {
                move = player.move(board.getPosition(), board.getCell());
            } while (!board.getPosition().isValid(move));
            final Result result = board.makeMove(move);
            if (result == Result.WIN) {
                log("Игрок " + no + " выйграл");
                return no;
            } else if (result == Result.LOSE) {
                log("Игрок " + no + " проиграл");
                return 3 - no;
            } else if (result == Result.DRAW) {
                log("Ничья");
                return 0;
            } else {
                return -1;
            }
            //note: need preciser error type
        } catch (Exception e) {
            log("Игрок " + no + " проиграл");
            return 3 - no;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
