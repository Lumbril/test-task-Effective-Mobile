package effective.mobile.code.utils.jwt.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        this("Invalid token");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
