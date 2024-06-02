/**
 * Diese Klasse repräsentiert einen König in einem Schachspiel. Sie erbt von der Klasse Piece und
 * implementiert die Bewegungsregeln und Überprüfungen für einen König.
 */
import java.util.NoSuchElementException;

public class King extends Piece {
    /**
     * Konstruktor für einen König.
     *
     * @param color Die Farbe des Königs ("Weiß" oder "Schwarz").
     */
    public King(String color) {
        this.color = color;
        this.hasMoved = 0;
    }

    /**
     * Überprüft, ob der vorgeschlagene Zug für den König gültig ist.
     *
     * @param board    Das aktuelle Spielbrett.
     * @param fromCol  Die Spalte, aus der der König gezogen werden soll.
     * @param fromRow  Die Reihe, aus der der König gezogen werden soll.
     * @param toCol    Die Zielspalte für den Zug.
     * @param toRow    Die Zielreihe für den Zug.
     * @return true, wenn der Zug gültig ist, andernfalls false.
     */
    @Override
    public Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) {
        if (board[toRow][toCol] == null || board[toRow][toCol].getColor() != board[fromRow][fromCol].getColor()) {
            Piece[][] cpboard = board;

            // König-Zug ein Feld in eine beliebeige Richtung
            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);

            if (rowDiff > 1 && colDiff > 1) {
                if (fromRow == toRow && hasMoved == 0) {
                    int a = Castling(board, fromCol, fromRow, toCol, toRow);
                    // Durchführen des Zugs
                    if (a == 0) {
                        return false;
                    }
                    if (a == 1) {
                        board[fromRow][toCol - 1] = new Rook(color);
                        board[fromRow][toCol] = this;
                        board[fromRow][7] = null;
                    }
                    if (a == 2) {
                        board[fromRow][toCol + 1] = new Rook(color);
                        board[fromRow][toCol] = this;
                        board[fromRow][7] = null;
                    }
                    hasMoved++;
                    return true;
                }
                return false;
            }
            // Checkt ob das Feld bedroht ist; Funktioniert aktuell nicht für Bauern
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece piece = board[row][col];
                    if (piece != null && !piece.getColor().equals(color) && !(piece instanceof King)) {
                        try {
                            if (piece.validateMove(cpboard, col, row, toCol, toRow)) {
                                return false; // das Feld ist bedroht
                            }
                        } catch (NoSuchElementException e) {
                            // Bauern Promotion Exception ignorieren
                        }
                    }
                }
            }
            // Check ob gegnerische König in der Nähe
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece piece = board[row][col];
                    if (piece instanceof King && !piece.getColor().equals(color)) {
                        int kingRow = row;
                        int kingCol = col;
                        if (Math.abs(toRow - kingRow) <= 1 && Math.abs(toCol - kingCol) <= 1) {
                            return false; // Könige sind zu nah aneinander
                        }
                    }
                }
            }
            hasMoved++;
            return true;
        }
        return false;
    }

    /**
     * Überprüft, ob die Rochade für den König gültig ist und führt sie gegebenenfalls aus.
     *
     * @param board    Das aktuelle Spielbrett.
     * @param fromCol  Die Spalte, aus der der König gezogen werden soll.
     * @param fromRow  Die Reihe, aus der der König gezogen werden soll.
     * @param toCol    Die Zielspalte für die Rochade.
     * @param toRow    Die Zielreihe für die Rochade.
     * @return 0, wenn die Rochade nicht möglich ist; 1, wenn die Rochade auf der Königsseite möglich ist; 2, wenn die Rochade auf der Damenseite möglich ist.
     */
    private int Castling(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) {
        // Check ob König oder Turm sich bewegt haben
        if (toCol > fromCol) { // Rocharde Königsseite
            if (board[fromRow][7] == null || board[fromRow][7].getHasMoved() != 0) {
                return 0;
            }

            // Check ob die Felder zwischen König und Turm leer sind
            for (int col = fromCol + 1; col < 7; col++) {
                if (board[fromRow][col] != null) {
                    return 0;
                }
            }

            // Checkt ob der König durch ein Feld ziehen musss bei dem er im Schach wäre
            for (int col = fromCol + 1; col <= toCol; col++) {
                if (isKingInCheck(board, col, fromRow)) {
                    return 0;
                }
            }
            return 1;
        } else { // Rocharde auf der Damenseite
            if (board[fromRow][7] == null || board[fromRow][7].getHasMoved() != 0) {
                return 0;
            }

            // Check ob die Felder zwischen König und Turm leer sind
            for (int col = 1; col < fromCol; col++) {
                if (board[fromRow][col] != null) {
                    return 0;
                }
            }
            // Checkt ob der König durch ein Feld ziehen musss bei dem er im Schach wäre
            for (int col = fromCol - 1; col >= toCol; col--) {
                if (isKingInCheck(board, col, fromRow)) {
                    return 0;
                }
            }
            return 2;
        }
    }

    /**
     * Überprüft, ob der König im Schach steht, wenn er sich auf dem angegebenen Feld befindet.
     *
     * @param board   Das aktuelle Spielbrett.
     * @param kingCol Die Zielspalte für den König.
     * @param kingRow Die Zielreihe für den König.
     * @return true, wenn der König im Schach steht, andernfalls false.
     */
    private boolean isKingInCheck(Piece[][] board, int kingCol, int kingRow) {
        // Check ob König im schach ist
        Piece[][] cpboard = board;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    try {
                        if (piece.validateMove(cpboard, col, row, kingCol, kingRow)) {
                            System.out.println("Schach");
                            return true; // der König ist im Schach
                        }
                    } catch (NoSuchElementException e) {
                        // Bauern Promotion Exception ignorieren
                    }
                }
            }
        }
        return false;
    }
     /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation des Königs zu erhalten.
     *
     * @return Eine String-Repräsentation des Königs (z.B. "wK" für einen weißen König).
     */
    @Override
    public String toString(){
        return color.charAt(0) + "K";
    }
}
