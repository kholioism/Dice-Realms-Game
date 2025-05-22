package game.exceptions;

public class InvalidMoveException extends Exception{

    public InvalidMoveException(String string) {
        super(string);
    }

    public InvalidMoveException() {
        super();
    }

}
