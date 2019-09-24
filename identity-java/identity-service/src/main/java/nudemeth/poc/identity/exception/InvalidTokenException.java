package nudemeth.poc.identity.exception;

public class InvalidTokenException extends SecurityException {

    private static final long serialVersionUID = 1L;

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}