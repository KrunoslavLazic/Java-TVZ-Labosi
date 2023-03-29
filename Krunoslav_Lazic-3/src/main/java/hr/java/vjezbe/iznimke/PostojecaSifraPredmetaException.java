package hr.java.vjezbe.iznimke;

/**
 * Iznimka za gresku kada se ponavlja sifra predmeta
 */
public class PostojecaSifraPredmetaException extends Exception{

    public PostojecaSifraPredmetaException() {
    }

    public PostojecaSifraPredmetaException(String message) {
        super(message);
    }

    public PostojecaSifraPredmetaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojecaSifraPredmetaException(Throwable cause) {
        super(cause);
    }

    public PostojecaSifraPredmetaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
