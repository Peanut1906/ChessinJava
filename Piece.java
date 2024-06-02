/**
 * Diese abstrakte Klasse repräsentiert eine Schachfigur im Spiel.
 * Sie enthält Methoden und Attribute, die von allen konkreten Schachfiguren geerbt werden.
 */
public abstract class Piece {
    /**
     * Die Farbe der Figur ("Weiß" oder "Schwarz").
     */
    protected String color;
    
    /**
     * Gibt an, ob die Figur bereits bewegt wurde oder nicht.
     * 0 bedeutet, dass die Figur noch nicht bewegt wurde.
     * >0 bedeutet, dass die Figur bereits bewegt wurde.
     */
    protected int hasMoved;

    /**
     * Überprüft, ob der vorgeschlagene Zug für diese Figur gültig ist.
     *
     * @param board   Das aktuelle Spielbrett.
     * @param fromCol Die Spalte, aus der die Figur gezogen werden soll.
     * @param fromRow Die Reihe, aus der die Figur gezogen werden soll.
     * @param toCol   Die Zielspalte für den Zug.
     * @param toRow   Die Zielreihe für den Zug.
     * @return true, wenn der Zug gültig ist, andernfalls false.
     */
    public abstract Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow);

    /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation der Figur zu erhalten.
     *
     * @return Eine String-Repräsentation der Figur.
     */
    public abstract String toString();

    /**
     * Gibt die Farbe der Figur zurück.
     *
     * @return Die Farbe der Figur ("Weiß" oder "Schwarz").
     */
    public String getColor() {
        return color;
    }

    /**
     * Gibt an, ob die Figur bereits bewegt wurde oder nicht.
     *
     * @return 0, wenn die Figur noch nicht bewegt wurde, andernfalls 1.
     */
    public int getHasMoved() {
        return hasMoved;
    }
}