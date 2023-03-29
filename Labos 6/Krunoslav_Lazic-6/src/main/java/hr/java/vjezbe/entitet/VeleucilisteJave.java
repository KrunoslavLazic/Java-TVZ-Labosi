package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

/**
 * Klasa sa Veleuciliste java
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    /**
     * Konstruktor za klasu FakultetRacunarstva
     * @param naziv ime ucilista
     * @param profesori profesori koji predavaju
     * @param studenti studenti koji pohadaju
     * @param ispiti ispiti koji su pisani
     * @param predmeti kolegiji koji se pohadaju
     */
    public VeleucilisteJave(long id,String naziv, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti, List<Predmet> predmeti) {
        super(id,naziv, profesori, studenti, ispiti, predmeti);
    }

    /**
     * Metoda za odredivanje najboljeg studenta na godini
     * @param godina godina koje je taj student bio najbolji
     * @return vraca odabranog studenta
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Student najboljiStudent=null;
        BigDecimal najboljiProsjek=BigDecimal.ZERO;
        HashSet<Student> studenti = new HashSet<>();
        List<Ispit> ovogodisnjiIspiti= ispitiOveGodine(this.getIspiti(),godina);

        for (Ispit ispit : ovogodisnjiIspiti){
           studenti.add(ispit.getStudent());
        }
        for (Student student : studenti){
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(ovogodisnjiIspiti,student));
            }
            catch (NemoguceOdreditiProsjekStudentaException ex){
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!",
                        student.getIme(), student.getPrezime()), ex);
                continue;
            }

            if (prosjek.compareTo(najboljiProsjek)>=0){
                najboljiStudent=student;
                najboljiProsjek=prosjek;
            }

        }

        return najboljiStudent;
    }
    /**
     * Metoda za racunanje konacne studentove ocjene
     * @param ispiti ispiti koje je student pisao
     * @param pismeniZRad ocjena pismenog dijela Diplomskog rada
     * @param obranaZRad ocjena obrane Diplomskog rada
     * @return vraca ocjenu na fakultetu studenta
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti,Student student, Ocjena pismeniZRad, Ocjena obranaZRad) {

        List<Ispit> filtiriraniIspiti=filtrirajIspitePoStudentu(ispiti,student);
        int zbrojOcjenaIspita=0;
        for (Ispit ispit : filtiriraniIspiti){
            zbrojOcjenaIspita+=ispit.getOcjena().getOcjena();
        }
        double prosjekOcjenaIspit=zbrojOcjenaIspita/filtiriraniIspiti.size();

        return BigDecimal.valueOf((prosjekOcjenaIspit*2+pismeniZRad.getOcjena()+obranaZRad.getOcjena())/4);

    }

}
