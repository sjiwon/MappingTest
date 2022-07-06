package AA.MappingTest.exception;

public class NoBidMyArt extends RuntimeException{
    public NoBidMyArt() {
        super();
    }

    public NoBidMyArt(String message) {
        super(message);
    }

    public NoBidMyArt(String message, Throwable cause) {
        super(message, cause);
    }

    public NoBidMyArt(Throwable cause) {
        super(cause);
    }

    protected NoBidMyArt(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
