
// Exception für nicht erlaubte Züge
public class InvalidMoveException extends Exception {

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}