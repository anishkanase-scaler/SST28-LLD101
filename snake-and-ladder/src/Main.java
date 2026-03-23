import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board dimension (n for n x n): ");
        int n = sc.nextInt();

        System.out.print("Enter number of players: ");
        int x = sc.nextInt();

        System.out.print("Enter difficulty (easy/hard): ");
        String diffInput = sc.next();
        DifficultyLevel difficulty = diffInput.equalsIgnoreCase("hard")
                ? DifficultyLevel.HARD
                : DifficultyLevel.EASY;

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            players.add(new Player("Player-" + i));
        }

        Board board = BoardGenerator.generate(n, difficulty);
        Game game = new Game(board, players);
        game.start();

        sc.close();
    }
}
