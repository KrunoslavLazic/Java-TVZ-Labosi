package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktna klasa za sve obrazovne ustanove
 */
public abstract class ObrazovnaUstanova extends Entitet implements Visokoskolska{

    private String naziv;
    private List<Profesor> profesori;
    private List<Student> studenti;
    private List<Ispit> ispiti;
    private List<Predmet> predmeti;

    /**
     * Konstruktor za klasu FakultetRacunarstva
     * @param naziv ime ucilista
     * @param profesori profesori koji predavaju
     * @param studenti studenti koji pohadaju
     * @param ispiti ispiti koji su pisani
     * @param predmeti kolegiji koji se pohadaju
     */
    public ObrazovnaUstanova(long id,String naziv, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti, List<Predmet> predmeti) {
        super(id);
        this.naziv = naziv;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
        this.predmeti=predmeti;
    }

    /**
     * apstraktna metoda koja se kasnije definira
     * @param godina godina kad je bio najbolji
     * @return student
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public List<Profesor> getProfesori() {
        return new ArrayList<>(this.profesori);
    }
    public void setProfesori(List<Profesor> profesori) {
        this.profesori=new ArrayList<>(profesori);
    }
    public List<Student> getStudenti() {
        return new ArrayList<>(this.studenti);
    }
    public void setStudenti(List<Student> studenti) {
        this.studenti = new ArrayList<>(studenti);
    }
    public List<Ispit> getIspiti() {
        return new ArrayList<>(this.ispiti);
    }
    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti=new ArrayList<>(ispiti);
    }

    public List<Predmet> getPredmeti() {
        return new ArrayList<>(predmeti);
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = new ArrayList<>(predmeti);
    }
}
