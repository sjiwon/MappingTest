package AA.MappingTest.exception;

public class LessBidException extends RuntimeException {
    public LessBidException() {
        super();
    }

    public LessBidException(String message) {
        super(message);
    }

    public LessBidException(String message, Throwable cause) {
        super(message, cause);
    }

    public LessBidException(Throwable cause) {
        super(cause);
    }

    protected LessBidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
