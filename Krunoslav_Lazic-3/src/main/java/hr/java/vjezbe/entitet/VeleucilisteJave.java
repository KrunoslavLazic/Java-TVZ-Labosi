package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    public VeleucilisteJave(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti) {
        super(naziv, profesori, studenti, ispiti, predmeti);
    }

    /**
     * Metoda za odredivanje najboljeg studenta na godini
     * @param godina godina koje je taj student bio najbolji
     * @return vraca odabranog studenta
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        BigDecimal prosjekOcjena = BigDecimal.ZERO;
        BigDecimal prosjek=BigDecimal.ZERO;
        Student studentRektorova=new Student("noname","nolastname","0", LocalDate.of(1,1,2001));

        for (int i=0;i<this.getStudenti().length;i++){
            Ispit[] ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti()[i]));

            try {
                prosjek = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
            }
            catch (NemoguceOdreditiProsjekStudentaException ex){
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!",
                        this.getStudenti()[i].getIme(), this.getStudenti()[i].getPrezime()), ex);
            }

            if (prosjek.compareTo(prosjekOcjena)>0){
                prosjekOcjena=prosjek;
                studentRektorova=this.getStudenti()[i];
            }
            else if(prosjek.compareTo(prosjekOcjena)==0){
                prosjekOcjena=prosjek;
                studentRektorova=this.getStudenti()[i];
            }
        }

        return studentRektorova;
    }
    /**
     * Metoda za racunanje konacne studentove ocjene
     * @param ispiti ispiti koje je student pisao
     * @param pismeniZRad ocjena pismenog dijela Diplomskog rada
     * @param obranaZRad ocjena obrane Diplomskog rada
     * @return vraca ocjenu na fakultetu studenta
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniZRad, int obranaZRad) {

        int zbrojOcjenaIspita=0;
        for (Ispit ispit : ispiti){
            zbrojOcjenaIspita+=ispit.getOcjena();
        }
        double prosjekOcjenaIspit=zbrojOcjenaIspita/ispiti.length;

        return BigDecimal.valueOf((prosjekOcjenaIspit*2+pismeniZRad+obranaZRad)/4);

    }

}
