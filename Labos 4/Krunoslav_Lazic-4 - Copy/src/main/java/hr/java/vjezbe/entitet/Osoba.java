package hr.java.vjezbe.entitet;

/**
 * Apstraktna klasa za sve osobe
 */
public abstract class Osoba {

    private String ime;
    private String prezime;

    /**
     * Konstruktor za klasu Osoba
     * @param ime ime osobe
     * @param prezime prezime osobe
     */
    public Osoba(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
