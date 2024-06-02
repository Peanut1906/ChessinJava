import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Repräsentiert das Schachbrett.
 */
public class Board {
    /**
     * Das Schachbrett.
     */
    private static Piece[][] board = new Piece[8][8];

    /**
     * Initialisiert das Schachbrett mit den Startpositionen der Figuren.
     */
    public Board() {
        // Schwarze Bauern
        for (int x = 0; x < 8; x++) {
            board[6][x] = new Pawn("black");
        }

        // Weiße Bauern
        for (int x = 0; x < 8; x++) {
            board[1][x] = new Pawn("white");
        }

        // Türme
        board[7][0] = new Rook("black");
        board[7][7] = new Rook("black");
        board[0][0] = new Rook("white");
        board[0][7] = new Rook("white");

        // Pferde/Springer
        board[7][1] = new Knight("black");
        board[7][6] = new Knight("black");
        board[0][1] = new Knight("white");
        board[0][6] = new Knight("white");

        // Läufer
        board[7][2] = new Bishop("black");
        board[7][5] = new Bishop("black");
        board[0][2] = new Bishop("white");
        board[0][5] = new Bishop("white");

        // Damen
        board[7][3] = new Queen("black");
        board[0][3] = new Queen("white");

        // Könige
        board[7][4] = new King("black");
        board[0][4] = new King("white");
    }

    /**
     * Gibt die Figur an der angegebenen Position zurück.
     *
     * @param row Die Zeile der Figur.
     * @param col Die Spalte der Figur.
     * @return Die Figur an der angegebenen Position.
     */
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    /**
     * Setzt die Figur an der angegebenen Position.
     *
     * @param row Die Zeile der Figur.
     * @param col Die Spalte der Figur.
     * @param piece Die Figur, die gesetzt werden soll.
     */
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    /**
     * Bewegt eine Figur von einer Position zu einer anderen.
     *
     * @param fromCol Die Spalte der Ausgangsposition.
     * @param fromRow Die Zeile der Ausgangsposition.
     * @param toCol Die Spalte der Zielposition.
     * @param toRow Die Zeile der Zielposition.
     * @param currentTurn Die Farbe des aktuellen Spielers.
     * @throws InvalidMoveException Wenn der Zug ungültig ist.
     */
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow, String currentTurn) throws InvalidMoveException {
        Piece piece = getPiece(fromRow, fromCol);
        // Check nach korrekter Eingabe
        if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7 || toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            throw new InvalidMoveException("Ungültige Zugkoordinaten...");
        }
        // Check ob sich auf dem gewählen Feld eine Figur befindet
        if (piece == null) {
            throw new InvalidMoveException("Nur existierende Figuren können bewegt werden...");
        }
        if (!piece.getColor().equals(currentTurn)) {
            throw new InvalidMoveException("Du bist nicht dran...");
        }

        // Checkt ob der Zug erlaubt ist
        try {
            Boolean isValidMove = piece.validateMove(board, fromCol, fromRow, toCol, toRow);
            if (!isValidMove) {
                throw new InvalidMoveException("Die Figur kann den Zug nicht ausführen.");
            }

            // Bauern Promotion
            if (piece.getColor().equals(currentTurn) && piece instanceof Pawn && (toRow == 0 || toRow == 7)) {
                // Bauern promotion ist in der validateMove methode vom Bauern
                setPiece(fromRow, fromCol, null);
            } else if (piece instanceof King && Math.abs(toRow - fromRow) > 1){
                // Rocharde ist in der validateMove methode vom König gehandhabt
            } else {
                // Bewegt die Figuren an die entsprechende Stelle
                setPiece(fromRow, fromCol, null);
                setPiece(toRow, toCol, piece);
            }

            currentTurn = currentTurn.equals("white") ? "black" : "white";
        } catch (NoSuchElementException e) {
            // Fängt Exeption von Bauern Promotion
        }
    }

    /**
     * Überprüft, ob ein bestimmtes Feld leer ist.
     *
     * @param row Die Zeile des Feldes.
     * @param col Die Spalte des Feldes.
     * @return True, wenn das Feld leer ist, sonst false.
     */
    public boolean isEmpty(int row, int col) {
        return getPiece(row, col) == null;
    }

    /**
     * Gibt das Schachbrett aus.
     */
    public void printBoard() {
        // Print der Spalten
        System.out.print("  ");
        for (int col = 0; col < 8; col++) {
            System.out.print((char) ('a' + col) + "  ");
        }
        System.out.println();

        // Print von reihen und Figuren
        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece == null) {
                    if ((row + col) % 2 == 0) { // Leere Schwarze Felder
                        System.out.print("##");
                    } else {
                        System.out.print("  "); // Leere Weiße Felder
                    }
                } else {
                    System.out.print(piece.toString());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Gibt das Schachbrett aus der Perspektive von Schwarz aus.
     */
    public void printBoardReversed() {
        // Print Spalten
        System.out.print("  ");
        for (int col = 0; col < 8; col++) {
            System.out.print((char) ('h' - col) + "  ");
        }
        System.out.println();

        // Print reihen und Figuren
        for (int row = 0; row < 8; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 7; col >= 0; col--) {
                Piece piece = getPiece(row, col);
                if (piece == null) {
                    if ((row + col) % 2 == 0) { // Leere Schwarze Felder
                        System.out.print("##");
                    } else {
                        System.out.print("  "); // Leere Weiße Felder
                    }
                } else {
                    System.out.print(piece.toString());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Überprüft, ob der König einer Farbe im Schach steht.
     *
     * @param color Die Farbe des Königs.
     * @return True, wenn der König im Schach steht, sonst false.
     */
    // Methode um zu überprüfen, ob der König einer Farbe im Schach steht
    public boolean isInCheck(String color) {
        // Finde die Position des Königs
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && piece instanceof King && piece.getColor().equals(color)) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        // Überprüfe, ob der König von einer gegnerischen Figur angegriffen wird
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    if (piece.validateMove(board, col, row, kingCol, kingRow)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // Methode um zu überprüfen, ob der König einer Farbe schachmatt ist
    public boolean isCheckmate(String color) {
        // Überprüfe, ob der König im Schach steht
        if (!isInCheck(color)) {
            return false;
        } 
        Piece [][] cpboard = board;

        // Überprüfe, ob es einen möglichen Zug gibt, um den Schach zu beenden
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null && piece.getColor().equals(color)) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            piece.validateMove(cpboard, col, row, toCol, toRow);
                            if (isInCheck(color)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // Methode um zu überprüfen, ob das Spiel in einem Patt steht
    public boolean isStalemate() {
        // Überprüfe, ob der Spieler am Zug keine Züge mehr hat
        String currentPlayerColor = Main.getCurrentTurn() == 0 ? "white" : "black";
        Piece [][] cpboard = board;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(row, col);
                if (piece != null && piece.getColor().equals(currentPlayerColor)) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            piece.validateMove(cpboard, col, row, toCol, toRow);
                            if (!isInCheck(currentPlayerColor)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}