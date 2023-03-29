package hr.java.vjezbe.iznimke;

/**
 * Iznimka ako sifra pocinje sa znamenkom
 */
public class PogresnaSifraPredmetaException extends Exception{
    public PogresnaSifraPredmetaException() {
    }

    public PogresnaSifraPredmetaException(String message) {
        super(message);
    }

    public PogresnaSifraPredmetaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PogresnaSifraPredmetaException(Throwable cause) {
        super(cause);
    }

    public PogresnaSifraPredmetaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
