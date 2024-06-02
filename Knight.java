/**
 * Diese Klasse repräsentiert einen Springer in einem Schachspiel. Sie erbt von der Klasse Piece und
 * implementiert die Bewegungsregeln für einen Springer.
 */
public class Knight extends Piece {
    /**
     * Konstruktor für einen Springer.
     *
     * @param color Die Farbe des Springers ("Weiß" oder "Schwarz").
     */
    public Knight(String color) {
        this.color = color;
        hasMoved = 0;
    }

    /**
     * Überprüft, ob der vorgeschlagene Zug für den Springer gültig ist.
     *
     * @param board    Das aktuelle Spielbrett.
     * @param fromCol  Die Spalte, aus der der Springer gezogen werden soll.
     * @param fromRow  Die Reihe, aus der der Springer gezogen werden soll.
     * @param toCol    Die Zielspalte für den Zug.
     * @param toRow    Die Zielreihe für den Zug.
     * @return true, wenn der Zug gültig ist, andernfalls false.
     */
    public Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) {
        if (board[toRow][toCol] == null || board[toRow][toCol].getColor() != board[fromRow][fromCol].getColor()) {

            int rowDiff = Math.abs(toRow - fromRow);
            int colDiff = Math.abs(toCol - fromCol);

            // Überprüft, ob der Zug einem gültigen Springerzug entspricht
            if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation des Springers zu erhalten.
     *
     * @return Eine String-Repräsentation des Springers (z.B. "wN" für einen weißen Springer).
     */
    @Override
    public String toString() {
        return color.charAt(0) + "N";
    }
}