package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final String name;
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final String name, final PrintStream out, final Scanner in) {
        this.name = name;
        this.out = out;
        this.in = in;
    }

    public HumanPlayer(final String name) {
        this(name, System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            try {
                out.println("Игровая доска:");
                out.println(position);
                out.println("Ход " + cell);
                out.println("Введите значение по столбцу и строке:");
                final Move move = new Move(in.nextInt(), in.nextInt(), cell);
                if (position.isValid(move)) {
                    return move;
                } else {
                    out.println("Неверный ход. Пожалуйста, попробуйте еще раз.");
                }
            } catch (InputMismatchException e) {
                out.println("Неверный ввод. Введите два целых числа для вашего хода.");
                in.nextLine();
            }
        }
    }

    @Override
    public String name() {
        return name;
    }

}
