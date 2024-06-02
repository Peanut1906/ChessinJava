/**
 * Die Klasse Queen repräsentiert die Schachfigur Königin.
 * Sie erbt von der abstrakten Klasse Piece und implementiert die Methoden zum Überprüfen gültiger Züge und zur Darstellung der Figur.
 */
public class Queen extends Piece {
    /**
     * Konstruktor für eine neue Königin.
     *
     * @param color Die Farbe der Königin ("Weiß" oder "Schwarz").
     */
    public Queen(String color) {
        this.color = color;
        hasMoved = 0;
    }

    /**
     * Überprüft, ob der vorgeschlagene Zug für die Königin gültig ist.
     *
     * @param board   Das aktuelle Spielbrett.
     * @param fromCol Die Spalte, aus der die Königin gezogen werden soll.
     * @param fromRow Die Reihe, aus der die Königin gezogen werden soll.
     * @param toCol   Die Zielspalte für den Zug.
     * @param toRow   Die Zielreihe für den Zug.
     * @return true, wenn der Zug gültig ist, andernfalls false.
     */
    @Override
    public Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) {
        if (fromRow == toRow && fromCol == toCol) {
            return false;
        }
        try {
            if (board[toRow][toCol].getColor().equals(color)) {
                return false;
            }
        } catch (Exception e) {
            // Ignoriert die Exception, die auftritt, wenn das Zielfeld leer ist.
        }
        // Ist der Weg zum Ziel frei?
        if (fromRow == toRow) {
            // Horizontaler Zug
            int direction = fromCol < toCol ? 1 : -1;
            for (int col = fromCol + direction; col != toCol; col += direction) {
                if (board[fromRow][col] != null) {
                    return false;
                }
            }
            hasMoved++;
            return true;
        } else if (fromCol == toCol) {
            // Vertikaler Zug
            int direction = fromRow < toRow ? 1 : -1;
            for (int row = fromRow + direction; row != toRow; row += direction) {
                if (board[row][fromCol] != null) {
                    return false;
                }
            }
            return true;
        } else if (board[toRow][toCol] == null || board[toRow][toCol].getColor() != board[fromRow][fromCol].getColor()) {
            // Diagonaler Zug
            if (Math.abs(toCol - fromCol) != Math.abs(toRow - fromRow)) {
                return false;
            }
            // Checkt ob der Weg zum Zielfeld frei ist
            int directionX = (toCol - fromCol) / Math.abs(toCol - fromCol);
            int directionY = (toRow - fromRow) / Math.abs(toRow - fromRow);

            for (int i = 1; i < Math.abs(toCol - fromCol); i++) {
                int x = fromCol + i * directionX;
                int y = fromRow + i * directionY;

                if (board[y][x] != null) {
                    return false;
                }
            }
            hasMoved++;
            return true;
        }
        return false;
    }

    /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation der Königin zu erhalten.
     *
     * @return Eine String-Repräsentation der Königin (z.B. "wQ" für eine weiße Königin).
     */
    @Override
    public String toString() {
        return color.charAt(0) + "Q";
    }
}