package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

/**
 * Klasa za ispit
 */
public final class Ispit implements Online{

    private Predmet predmet;
    private Student student;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;

    /**
     * Konstruktor za klasu ispit
     * @param predmet predmet za ispit
     * @param student student koji pise ispit
     * @param ocjena ocjena na ispitu
     * @param datumIVrijeme datum pisanja ispita
     * @param dvorana lokacija pisanja
     */
    public Ispit(Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datumIVrijeme,Dvorana dvorana) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana=dvorana;
    }

    @Override
    public String softwareZaIspit(String imeSoftwarea) {
        return imeSoftwarea;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }


}
