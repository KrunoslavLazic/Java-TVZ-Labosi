package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class IzvanredanStudent extends Student{

    private String nazivTvrtke;

    public IzvanredanStudent(String ime, String prezime, String jmbag, LocalDate datumRodjenja, String nazivTvrtke) {
        super(ime, prezime, jmbag, datumRodjenja);
        this.nazivTvrtke = nazivTvrtke;
    }

    public String getNazivTvrtke() {
        return nazivTvrtke;
    }

    public void setNazivTvrtke(String nazivTvrtke) {
        this.nazivTvrtke = nazivTvrtke;
    }
}
