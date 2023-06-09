package lk.ijse.royal_care_pharmacy.dao.exception;

public class ViolationException extends RuntimeException{

    public ViolationException() {
    }

    public ViolationException(String message) {
        super(message);
    }

    public ViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViolationException(Throwable cause) {
        super(cause);
    }

    public ViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
