package hr.java.vjezbe.entitet;

public abstract class ObrazovnaUstanova implements Visokoskolska{

    private String naziv;
    private Profesor[] profesori;
    private Student[] studenti;
    private Ispit[] ispiti;
    private Predmet[] predmeti;

    public ObrazovnaUstanova(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti) {
        this.naziv = naziv;
        this.profesori = profesori;
        this.studenti = studenti;
        this.ispiti = ispiti;
        this.predmeti=predmeti;
    }

    public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer godina);

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Profesor[] getProfesori() {
        Profesor[] copy=new Profesor[this.profesori.length];
        System.arraycopy(this.profesori,0,copy,0,copy.length);
        return copy;
    }
    public void setProfesori(Profesor[] profesori) {
        this.profesori=new Profesor[profesori.length];
        System.arraycopy(profesori,0,this.profesori,0,profesori.length);
    }
    public Student[] getStudenti() {
        Student[] copy=new Student[this.studenti.length];
        System.arraycopy(this.studenti,0,copy,0,copy.length);
        return copy;
    }
    public void setStudenti(Student[] studenti) {
        this.studenti = new Student[studenti.length];
        System.arraycopy(studenti,0,this.studenti,0,studenti.length);
    }
    public Ispit[] getIspiti() {
        Ispit[] copy= new Ispit[this.ispiti.length];
        System.arraycopy(this.ispiti,0,copy,0,copy.length);
        return copy;
    }
    public void setIspiti(Ispit[] ispiti) {
        this.ispiti=new Ispit[ispiti.length];
        System.arraycopy(ispiti,0,this.ispiti,0,ispiti.length);
    }


}
