import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Diese Klasse repräsentiert einen Bauern in einem Schachspiel. Sie erbt von der Klasse Piece und
 * implementiert die Bewegungsregeln für einen Bauern.
 */
public class Pawn extends Piece {
    /**
     * Konstruktor für einen Bauern.
     *
     * @param color Die Farbe des Bauern ("Weiß" oder "Schwarz").
     */
    public Pawn(String color) {
        this.color = color;
        hasMoved = 0;
    }

    /**
     * Überprüft, ob der vorgeschlagene Zug für den Bauern gültig ist.
     *
     * @param board   Das aktuelle Spielbrett.
     * @param fromCol Die Spalte, aus der der Bauer gezogen werden soll.
     * @param fromRow Die Reihe, aus der der Bauer gezogen werden soll.
     * @param toCol   Die Zielspalte für den Zug.
     * @param toRow   Die Zielreihe für den Zug.
     * @return true, wenn der Zug gültig ist, andernfalls false.
     * @throws NoSuchElementException Wenn der Benutzer ungültige Eingaben bei der Bauernumwandlung macht.
     */
    @Override
    public Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) throws NoSuchElementException {
        if (color.equals("white")) {
            if (toRow < fromRow) {
                return false; // weiße Bauern können sich nicht Rückwärts bewegen
            }
        } else {
            if (toRow > fromRow) {
                return false; // schwarze Bauern können sich nicht Vorwärts (bzw. Rückwärts) bewegen
            }
        }

        if (fromCol == toCol) {
            // Züge in denen keine Figur geschmissen wird
            if (color.equals("white")) {
                if (board[toRow][toCol] != null) {
                    return false; // Zielfeld ist nicht leer
                }
            } else {
                if (board[toRow][toCol] != null) {
                    return false; // Zielfeld ist nicht leer
                }
            }

            int rowDiff = Math.abs(toRow - fromRow);
            if (rowDiff > 2) {
                return false; // Bauern können nicht mehr als 2 Felder vor in einem Zug
            } else if (rowDiff == 2) {
                if (hasMoved != 0) {
                    return false; // der Bauer wurde bereits bewegt
                }

                if (color.equals("white")) {
                    for (int i = fromRow + 1; i < toRow; i++) {
                        if (board[i][toCol] != null) {
                            return false; // Weg ist versperrt für weiß
                        }
                    }
                } else {
                    for (int i = fromRow - 1; i > toRow; i--) {
                        if (board[i][toCol] != null) {
                            return false; // Weg ist versperrt für schwarz
                        }
                    }
                }
            }
        } else {
            // Schmeissen bzw. en passant
            if (Math.abs(toCol - fromCol) != 1 || Math.abs(toRow - fromRow) != 1) {
                return false;
            }

            if (board[toRow][toCol] == null) {
                // Check von en passant
                if (color.equals("white") && fromRow == 4 && toRow == 5 &&
                    board[4][toCol] instanceof Pawn && ((Pawn) board[4][toCol]).getHasMoved() == 1) {
                    hasMoved++;
                    board[4][toCol] = null; // schmeißt den en passant geschmissenen Bauern; aktualisiert das Brett
                    return true;
                } else if (color.equals("black") && fromRow == 3 && toRow == 2 &&
                           board[3][toCol] instanceof Pawn && ((Pawn) board[3][toCol]).getHasMoved() == 1) {
                    hasMoved++;
                    board[3][toCol] = null; // schmeißt den en passant geschmissenen Bauern; aktualisiert das Brett
                    return true;
                } else {
                    return false; // Keine Figur zum Schmeissen da
                }
            } else if (board[toRow][toCol].getColor().equals(color)) {
                return false; // Eigene Figuren können nicht geschmissen werden
            }
        }

        // Bauern promotion
        if ((color.equals("white") && toRow == 0) || (color.equals("black") && toRow == 7)) {
            Scanner promotionScanner = new Scanner(System.in);
            System.out.println("Wähle eine Figur für die Bauern-Promotion aus (Q, R, B, N): ");

            while (true) {
                if (promotionScanner.hasNext()) {
                    String promotionPiece = promotionScanner.nextLine().toUpperCase();
                    switch (promotionPiece) {
                        case "Q":
                            board[toRow][toCol] = new Queen(color);
                            break;
                        case "R":
                            board[toRow][toCol] = new Rook(color);
                            break;
                        case "B":
                            board[toRow][toCol] = new Bishop(color);
                            break;
                        case "N":
                            board[toRow][toCol] = new Knight(color);
                            break;
                        default:
                            System.out.println("Ungültige Figur. Der Bauer wird zur Königin promoviert.");
                            board[toRow][toCol] = new Queen(color);
                    }
                    promotionScanner.close();
                    hasMoved++;
                    return true;
                } else {
                    try {
                        Thread.sleep(100); // kurze Pause
                    } catch (InterruptedException e) {
                        // Ignoriert geworfene Exceptions
                    }
                }
            }
        } else {
            hasMoved++;
            return true;
        }
    }

    /**
     * Überschreibt die toString()-Methode, um eine String-Repräsentation des Bauern zu erhalten.
     *
     * @return Eine String-Repräsentation des Bauern (z.B. "wp" für einen weißen Bauern).
     */
    @Override
    public String toString() {
        return color.charAt(0) + "p";
    }

    /**
     * Gibt an, ob der Bauer bereits bewegt wurde.
     *
     * @return 1, wenn der Bauer bereits bewegt wurde, andernfalls 0.
     */
    public int getHasMoved() {
        return hasMoved;
    }
}