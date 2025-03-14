package game;

import java.util.Random;

public record RandomPlayer(String name) implements Player {

    @Override
    public Move move(final Position position, final Cell cell) {
        int n = position.getN();
        int m = position.getM();
        while (true) {
            Random random = new Random();
            int r = random.nextInt(0, n);
            int c = random.nextInt(0, m);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public String name() {
        return name;
    }
}
