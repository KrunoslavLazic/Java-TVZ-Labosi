package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * Klasa Student
 */
public class Student extends Osoba{

    private String jmbag;
    private LocalDate datumRodjenja;

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime,prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public String toString() {
        return "Student: " +
                super.getIme() + " " + super.getPrezime() +
                " " +jmbag + ' ' +
                " " + datumRodjenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }
}
