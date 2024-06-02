import java.util.Scanner;

/**
 * Die Hauptklasse des Schachspiels.
 */
public class Main {
    /**
     * Der aktuelle Zug. 0 für Weiß, 1 für Schwarz.
     */
    static int currentTurn = 0; // 0 für weiß, 1 für schwarz
    /**
     * Das Schachbrett.
     */
    private static Board board;
    /**
     * Der Scanner für Benutzereingaben.
     */
    private static Scanner scanner;

    /**
     * Die Hauptmethode des Programms.
     *
     * @param args Die Kommandozeilenargumente.
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Willkommen zu Schach!");
        System.out.println("Möchtest du im Befehlszeilen-Modus spielen? Dann tippe: \"CLI\"\nMöchtest du auf einer Graphischen Oberfläche spielen? Dann tippe: \"GUI\"");
        String choice = scanner.nextLine().toUpperCase();

        if (choice.equals("CLI")) {
            playInCLI();
        } else if (choice.equals("GUI")) {
            playInGUI();
        } else {
            System.out.println("Ungültige Auswahl. Verlassen des Spiels…");
        }
    }

    /**
     * Spielt das Schachspiel im Befehlszeilen-Modus.
     */
    private static void playInCLI() {
        board = new Board();
        while (true) {
            System.out.println((currentTurn == 0 ? "Weiss" : "Schwarz") + " ist dran.");
            board.printBoard();

            System.out.print("Gib deinen Zug ein (Bsp.: \"e2e4\"): ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int fromCol = move.charAt(0) - 97;
                int fromRow = move.charAt(1) - 49;
                int toCol = move.charAt(2) - 97;
                int toRow = move.charAt(3) - 49;

                String playerColor = currentTurn == 0 ? "white" : "black";
                board.movePiece(fromCol, fromRow, toCol, toRow, playerColor);
                currentTurn = 1 - currentTurn;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
            if (board.isCheckmate(currentTurn == 0 ? "black" : "white")) {
                System.out.println("Checkmate! " + (currentTurn == 0 ? "White" : "Black") + " wins!");
                break;
            } else if (board.isStalemate()) {
                System.out.println("Stalemate!");
                break;
            }
            
        }
    }

    /**
     * Spielt das Schachspiel im GUI-Modus.
     */
    private static void playInGUI() {
        board = new Board();
        Table table = new Table(board);
        table.boardPanel.addMouseListener(new BoardPanelMouseListener(table.boardPanel));
    }

    /**
     * Gibt den aktuellen Zug zurück.
     *
     * @return Der aktuelle Zug.
     */
    public static int getCurrentTurn() {
        return currentTurn;
    }
}