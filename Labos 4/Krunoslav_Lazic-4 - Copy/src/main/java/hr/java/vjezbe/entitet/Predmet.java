package hr.java.vjezbe.entitet;

import java.util.*;

/**
 * Klasa za Predmet
 */
public class Predmet {

    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private SortedSet<Student> studenti;

    private Semestar semestar;

    /**
     * Konstruktor za klasu predmet
     * @param sifra id predmeta
     * @param naziv ime predmeta
     * @param brojEctsBodova koliko nosi ECTS bodova
     * @param nositelj tko vodi predmet
     * @param studenti studenti koji pohadaju
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, SortedSet<Student> studenti, Semestar semestar) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
        this.semestar = semestar;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public SortedSet<Student> getStudenti() {

        return new TreeSet<>(this.studenti);
    }

    public void setStudenti(SortedSet<Student> studenti) {
        this.studenti = new TreeSet<>(studenti);
    }

    public Semestar getSemestar() {
        return semestar;
    }

    public void setSemestar(Semestar semestar) {
        this.semestar = semestar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Predmet predmet)) return false;

        if (!Objects.equals(sifra, predmet.sifra)) return false;
        if (!Objects.equals(naziv, predmet.naziv)) return false;
        if (!Objects.equals(brojEctsBodova, predmet.brojEctsBodova))
            return false;
        if (!Objects.equals(nositelj, predmet.nositelj)) return false;
        return Objects.equals(studenti, predmet.studenti);
    }

    @Override
    public int hashCode() {
        int result = sifra != null ? sifra.hashCode() : 0;
        result = 31 * result + (naziv != null ? naziv.hashCode() : 0);
        result = 31 * result + (brojEctsBodova != null ? brojEctsBodova.hashCode() : 0);
        result = 31 * result + (nositelj != null ? nositelj.hashCode() : 0);
        result = 31 * result + (studenti != null ? studenti.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "sifra='" + sifra + '\'' +
                ", naziv='" + naziv + '\'' +
                ", brojEctsBodova=" + brojEctsBodova +
                ", nositelj=" + nositelj +
                ", studenti=" + studenti +
                ", semestar=" + semestar +
                '}';
    }
}
