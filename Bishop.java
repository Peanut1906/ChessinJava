public class Bishop extends Piece{
    // Konstruktor von Läufern
    public Bishop(String color){
        this.color = color;
    }
    @Override
    public Boolean validateMove(Piece[][] board, int fromCol, int fromRow, int toCol, int toRow) {
        if ((toCol == fromCol) || (toRow == fromRow)){
            return false;
        }
        if (board[toRow][toCol] == null || board[toRow][toCol].getColor() != board[fromRow][fromCol].getColor()) {
            // Checkt ob Zug diganal
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
    
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return color.charAt(0) + "B";

    }
}
