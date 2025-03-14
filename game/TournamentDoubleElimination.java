package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentDoubleElimination {
    private final List<Player> playerUpperMesh;
    private final List<Player> playerDownMesh;
    private final int n, m, k, p, typePlay;

    public TournamentDoubleElimination(List<Player> playerNames, int n, int m, int k, int p, int typePlay) {
        this.playerUpperMesh = new ArrayList<>();
        this.playerDownMesh = new ArrayList<>();
        this.n = n;
        this.m = m;
        this.k = k;
        this.p = p;
        this.typePlay = typePlay;

        playerUpperMesh.addAll(playerNames);
        Collections.shuffle(playerUpperMesh);
    }

    public void start() {
        System.out.println("Начало турнира!");

        while (playerUpperMesh.size() > 1 || playerDownMesh.size() > 1 ||
                (playerUpperMesh.size() == 1 && playerDownMesh.size() == 1)) {
            if (playerUpperMesh.size() > 1) {
                playRound(playerUpperMesh, true);
                printMesh();
            }

            if (playerDownMesh.size() > 1) {
                playRound(playerDownMesh, false);
                printMesh();
            }

            if (playerUpperMesh.size() == 1 && playerDownMesh.size() == 1) {
                Player upperPlayer = playerUpperMesh.getFirst();
                Player downPlayer = playerDownMesh.getFirst();
                System.out.println("Финал! " + upperPlayer.name() + " против " + downPlayer.name());
                Game finalGame = new Game(true, upperPlayer, downPlayer);
                int result;
                do {
                    result = finalGame.play(createGameInstance());
                } while (result == 0);

                Player winner = result == 1 ? upperPlayer : downPlayer;
                System.out.println("Победитель турнира: " + winner.name());
                return;
            }
        }

        if (playerUpperMesh.size() == 1) {
            System.out.println("Победитель турнира: " + playerUpperMesh.getFirst().name());
        } else if (playerDownMesh.size() == 1) {
            System.out.println("Победитель турнира: " + playerDownMesh.getFirst().name());
        }
    }

    private void playRound(List<Player> mesh, boolean isUpper) {
        System.out.println((isUpper ? "Верхняя" : "Нижняя") + " сетка: Начало раунда");

        List<Player> nextRound = new ArrayList<>();

        for (int i = 0; i < mesh.size() - 1; i += 2) {
            Player player1 = mesh.get(i);
            Player player2 = mesh.get(i + 1);

            System.out.println("Играют: " + player1.name() + " vs " + player2.name());
            Game game = new Game(true, player1, player2);
            int result;
            do {
                result = game.play(createGameInstance());
            } while (result == 0);

            if (result == 1) {
                System.out.println("Победитель: " + player1.name());
                nextRound.add(player1);
                if (isUpper) playerDownMesh.add(player2);
            } else {
                System.out.println("Победитель: " + player2.name());
                nextRound.add(player2);
                if (isUpper) playerDownMesh.add(player1);
            }
        }

        if (mesh.size() % 2 != 0) {
            nextRound.add(mesh.getLast());
        }

        mesh.clear();
        mesh.addAll(nextRound);
    }

    private void printMesh() {
        System.out.println("\nТекущая сетка:");
        System.out.println("Верхняя сетка:");
        for (Player player : playerUpperMesh) {
            System.out.println(player.name());
        }

        System.out.println("\nНижняя сетка:");
        for (Player player : playerDownMesh) {
            System.out.println(player.name());
        }
    }

    private Board createGameInstance() {
        if (typePlay == 1) {
            return new TicTacDefault(n, m, k, p);
        } else {
            return new RhombusTicTac(n, m, k, 0);
        }
    }
}
