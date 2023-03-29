package hr.java.vjezbe.entitet;

/**
 * Klasa za Predmet
 */
public class Predmet {

    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Student[] studenti;
    private Integer godina;

    /**
     * Konstruktor za klasu predmet
     * @param sifra id predmeta
     * @param naziv ime predmeta
     * @param brojEctsBodova koliko nosi ECTS bodova
     * @param nositelj tko vodi predmet
     * @param studenti studenti koji pohadaju
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Student[] studenti,Integer godina) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
        this.godina=godina;
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

    public Student[] getStudenti() {
        Student[] copy=new Student[this.studenti.length];
        System.arraycopy(this.studenti,0,copy,0,copy.length);
        return copy;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = new Student[studenti.length];
        System.arraycopy(studenti,0,this.studenti,0,studenti.length);
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }
}
