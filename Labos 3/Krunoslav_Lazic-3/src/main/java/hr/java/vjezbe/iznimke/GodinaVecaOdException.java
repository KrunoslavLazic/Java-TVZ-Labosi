package hr.java.vjezbe.iznimke;

/**
 * Iznimka za godinu vecu od 5
 */
public class GodinaVecaOdException extends Exception {
    public GodinaVecaOdException() {
    }

    public GodinaVecaOdException(String message) {
        super(message);
    }

    public GodinaVecaOdException(String message, Throwable cause) {
        super(message, cause);
    }

    public GodinaVecaOdException(Throwable cause) {
        super(cause);
    }

    public GodinaVecaOdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
