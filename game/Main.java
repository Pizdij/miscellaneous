package game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //NOTE: survive ctrl+d - partially fixed
        //note: close me
        Scanner scanner = new Scanner(System.in);
        int gameMode = getGameMode(scanner);

        if (gameMode == 1) {
            playOneOnOneGame(scanner);
        } else {
            playTournament(scanner);
        }
    }

    private static int getGameMode(Scanner scanner) {
        int gameMode;
        System.out.println("Выберите тип игры:");
        System.out.println("1. Игра 1 на 1");
        System.out.println("2. Турнир");

        while (true) {
            try {
                System.out.print("Введите тип игры (1 или 2): ");
                gameMode = scanner.nextInt();
                if (gameMode == 1 || gameMode == 2) {
                    break;
                } else {
                    System.out.println("Введите корректное значение типа игры (1 или 2).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                scanner.nextLine();
            }
        }
        return gameMode;
    }

    private static void playOneOnOneGame(Scanner scanner) {
        int typePlay = getBoardType(scanner);
        int[] boardParams = getBoardParameters(scanner, typePlay);

        Player player1 = new HumanPlayer("Игрок1:");
        Player player2 = new HumanPlayer("Игрок2:");
        Game game = new Game(true, player1, player2);

        int result;
        if (typePlay == 1) {
            result = game.play(new TicTacDefault(boardParams[0], boardParams[1], boardParams[2], boardParams[3]));
        } else {
            result = game.play(new RhombusTicTac(boardParams[0], boardParams[1], boardParams[2], 0));
        }
        System.out.println("Результат игры 1 на 1: " + result);
    }

    private static void playTournament(Scanner scanner) {
        System.out.println("Вы выбрали режим Турнира!");

        int playerCount = getPlayerCount(scanner);
        List<Player> players = new ArrayList<>();
        scanner.nextLine();

        System.out.println("Введите участников турнира:");
        for (int i = 1; i <= playerCount; i++) {
            String name;
            while (true) {
                try {
                    System.out.print("Введите имя для игрока " + i + ": ");
                    name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Имя не может быть пустым. Попробуйте снова.");
                        continue;
                    }
                    if (isNameTaken(players, name)) {
                        System.out.println("Имя уже используется другим игроком. Попробуйте другое.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода! Убедитесь, что ввели корректное имя.");
                    scanner.nextLine();
                }
            }

            int playerType = getPlayerType(scanner, name);
            if (playerType == 1) {
                players.add(new HumanPlayer(name));
            } else if (playerType == 2) {
                players.add(new RandomPlayer(name));
            }
        }

        int typePlay = getBoardType(scanner);
        int[] boardParams = getBoardParameters(scanner, typePlay);

        TournamentDoubleElimination tournament = new TournamentDoubleElimination(players, boardParams[0], boardParams[1], boardParams[2], boardParams[3], typePlay);
        tournament.start();
        System.out.println("Турнир завершен!");
    }

    private static int getPlayerType(Scanner scanner, String name) {
        int playerType;
        while (true) {
            try {
                System.out.println("Выберите тип игрока для " + name + ":");
                System.out.println("1. Человек");
                System.out.println("2. Бот");
                System.out.print("Введите номер типа (1 или 2): ");
                playerType = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (playerType == 1 || playerType == 2) {
                    break;
                } else {
                    System.out.println("Введите корректное значение (1 или 2).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                scanner.nextLine();
            }
        }
        return playerType;
    }

    private static boolean isNameTaken(List<Player> players, String name) {
        for (Player player : players) {
            if (player.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }


    private static int getBoardType(Scanner scanner) {
        int typePlay;
        System.out.println("Введите доску для игры");
        System.out.println("Доски на выбор:");
        System.out.println("1. N, M, K крестики нолики. Введите 1");
        System.out.println("2. Ромбовидные крестики нолики. Введите 2. Если вы выбираете ромб, то лишаетесь возможности " +
                "сыграть с правилом дебютного регламента");

        while (true) {
            try {
                System.out.print("Выберите режим (1 или 2): ");
                typePlay = scanner.nextInt();
                if (typePlay == 1 || typePlay == 2) {
                    break;
                } else {
                    System.out.println("Введите верное значение режима игры (1 или 2).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                scanner.nextLine();
            }
        }
        return typePlay;
    }

    private static int[] getBoardParameters(Scanner scanner, int typePlay) {
        int n = 0, m = 0, k = 0, p = 0;
        boolean isValidInput = false;

        if (typePlay == 1) {
            while (!isValidInput) {
                try {
                    System.out.print("""
                            Введите четыре числовых значения через пробел (N - высота прямоугольника
                            M - ширина прямоугольника
                            K - количество в ряд выставленных позиций для победы
                            P - количество первых p ходов по дебютному регламенту):\s""");
                    n = scanner.nextInt();
                    m = scanner.nextInt();
                    k = scanner.nextInt();
                    p = scanner.nextInt();
                    isValidInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                    scanner.nextLine();
                }
            }
        } else {
            while (!isValidInput) {
                try {
                    System.out.print("""
                            Введите два числовых значения через пробел (N - ширина и высота ромба
                            K - количество в ряд выставленных позиций для победы):\s""");
                    n = scanner.nextInt();
                    m = n;
                    k = scanner.nextInt();
                    isValidInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                    scanner.nextLine();
                }
            }
        }

        return new int[]{n, m, k, p};
    }

    private static int getPlayerCount(Scanner scanner) {
        int playerCount;
        while (true) {
            try {
                System.out.print("Введите количество участников турнира: ");
                playerCount = scanner.nextInt();
                if (playerCount > 1) {
                    break;
                } else {
                    System.out.println("Количество участников должно быть больше 1.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода! Убедитесь, что ввели только числа.");
                scanner.nextLine();
            }
        }
        return playerCount;
    }
}
