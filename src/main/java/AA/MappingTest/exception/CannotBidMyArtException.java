package AA.MappingTest.exception;

public class CannotBidMyArtException extends RuntimeException{
    public CannotBidMyArtException() {
        super();
    }

    public CannotBidMyArtException(String message) {
        super(message);
    }

    public CannotBidMyArtException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotBidMyArtException(Throwable cause) {
        super(cause);
    }

    protected CannotBidMyArtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
