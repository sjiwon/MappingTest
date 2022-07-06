package AA.MappingTest.exception;

public class MoreBidPrice extends RuntimeException {
    public MoreBidPrice() {
        super();
    }

    public MoreBidPrice(String message) {
        super(message);
    }

    public MoreBidPrice(String message, Throwable cause) {
        super(message, cause);
    }

    public MoreBidPrice(Throwable cause) {
        super(cause);
    }

    protected MoreBidPrice(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
