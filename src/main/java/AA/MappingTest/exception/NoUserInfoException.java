package AA.MappingTest.exception;

public class NoUserInfoException extends RuntimeException{
    public NoUserInfoException() {
        super();
    }

    public NoUserInfoException(String message) {
        super(message);
    }

    public NoUserInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoUserInfoException(Throwable cause) {
        super(cause);
    }

    protected NoUserInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
