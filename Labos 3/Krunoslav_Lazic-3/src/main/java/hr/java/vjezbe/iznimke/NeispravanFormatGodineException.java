package hr.java.vjezbe.iznimke;

/**
 * Iznimka za neispravan format godine
 */
public class NeispravanFormatGodineException extends Exception{
    public NeispravanFormatGodineException() {
    }

    public NeispravanFormatGodineException(String message) {
        super(message);
    }

    public NeispravanFormatGodineException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeispravanFormatGodineException(Throwable cause) {
        super(cause);
    }

    public NeispravanFormatGodineException(String message, Throwable cause,
                                           boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
