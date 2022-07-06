package AA.MappingTest.exception;

public class NoAuctionTypeException extends RuntimeException {
    public NoAuctionTypeException() {
        super();
    }

    public NoAuctionTypeException(String message) {
        super(message);
    }

    public NoAuctionTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuctionTypeException(Throwable cause) {
        super(cause);
    }

    protected NoAuctionTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
