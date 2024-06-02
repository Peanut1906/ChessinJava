/**
 * Die Klasse Rook repräsentiert die Schachfigur Turm.
 * Sie erbt von der abstrakten Klasse Piece und implementiert die Methoden zum Überprüfen gültiger Züge und zur Darstellung der Figur.
 */
public class Rook extends Piece {
    /**
     * Konstruktor für einen neuen Turm.
     *
     * @param color Die Farbe des Turms ("Weiß" oder "Schwarz").
     */
    public Rook(String color) {
        this.color = color;
        hasMoved = 0;
    }

    /**
     * Überprüft, ob der vorgeschlagene Zug für den Turm gültig ist.
     *
     * @param board   Das aktuelle Spielbrett.
     * @param fromCol Die Spalte, aus der der Turm gezogen werden soll.
     * @param fromRow Die Reihe, aus der der Turm gezogen werden soll.
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
        } else if (fromCol == toCol) {
            // Vertikaler Zug
            int direction = fromRow < toRow ? 1 : -1;
            for (int row = fromRow + direction; row != toRow; row += direction) {
                if (board[row][fromCol] != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        hasMoved++;
        return true;
    }

    /**
     * Gibt an, ob der Turm bereits bewegt wurde.
     *
     * @return 1, wenn der Turm bereits bewegt wurde, andernfalls 0.
     */
    public int getHasMoved() {
        return hasMoved;
    }

    /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation des Turms zu erhalten.
     *
     * @return Eine String-Repräsentation des Turms (z.B. "wR" für einen weißen Turm).
     */
    @Override
    public String toString() {
        return color.charAt(0) + "R";
    }
}