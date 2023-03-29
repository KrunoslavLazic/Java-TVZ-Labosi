package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Klasa za Fakultet Racunarstva
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    /**
     * Konstruktor za klasu FakultetRacunarstva
     * @param naziv ime ucilista
     * @param profesori profesori koji predavaju
     * @param studenti studenti koji pohadaju
     * @param ispiti ispiti koji su pisani
     * @param predmeti kolegiji koji se pohadaju
     */
    public FakultetRacunarstva(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti) {
        super(naziv, profesori, studenti, ispiti, predmeti);
    }

    /**
     * Metoda za racunanje studenta za rektorovu nagradu
     * @return vraca studenta koji je izabran
     */
    public Student odrediStudentaZaRektorovuNagradu(){

        BigDecimal prosjekOcjena = BigDecimal.ZERO;
        BigDecimal prosjek=BigDecimal.ZERO;
        Student studentRektorova=new Student("noname","nolastname","0", LocalDate.of(1,1,2001));

        for (int i=0;i<this.getStudenti().length;i++){
            Ispit[] ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti()[i]));
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
            }
            catch (NemoguceOdreditiProsjekStudentaException e){
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!",
                        this.getStudenti()[i].getIme(), this.getStudenti()[i].getPrezime()), e);
            }
            if (prosjek.compareTo(prosjekOcjena)>0){
                prosjekOcjena=prosjek;
                studentRektorova=this.getStudenti()[i];
            }
            else if(prosjek.compareTo(prosjekOcjena)==0){
                String najmladjiStudenti = String.format("%s %s, %s %s",
                        studentRektorova.getIme(),
                        studentRektorova.getPrezime(),
                        this.getStudenti()[i].getIme(),
                        this.getStudenti()[i].getPrezime());

                System.out.println("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);
                logger.error("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);

                throw new PostojiViseNajmladjihStudenataException(najmladjiStudenti);

            }
        }

        return studentRektorova;
    }

    /**
     * Metoda za racunanje konacne studentove ocjene
     * @param ispiti ispiti koje je student pisao
     * @param pismeniDRad ocjena pismenog dijela Diplomskog rada
     * @param obranaDRad ocjena obrane Diplomskog rada
     * @return vraca ocjenu na fakultetu studenta
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniDRad, int obranaDRad) {

        int zbrojOcjenaIspita=0;
        for (Ispit ispit : ispiti){
            zbrojOcjenaIspita+=ispit.getOcjena();
        }
        double prosjekOcjenaIspita=zbrojOcjenaIspita/ispiti.length;

        return BigDecimal.valueOf((prosjekOcjenaIspita*3+pismeniDRad+obranaDRad)/5);
    }

    /**
     * Metoda za odredivanje najboljeg studenta na godini
     * @param godina godina koje je taj student bio najbolji
     * @return vraca odabranog studenta
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Student studentSPeticama=new Student("default","default", "0",LocalDate.of(1,1,2001));
        int petice=0,maxBrojPetica=0,index=0;

        for (int i=0;i<this.getStudenti().length;i++){
            Ispit[] ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti()[i]));
            petice++;

            for (int j=0;j< ispitiStudenta.length;j++){
                if (ispitiStudenta[i].getOcjena()==5){
                    petice++;
                }
            }
            if (petice>maxBrojPetica){
                maxBrojPetica=petice;
                studentSPeticama=this.getStudenti()[i];
                index=i;
            }
            else if(maxBrojPetica==petice){
                if (i<=index){
                    studentSPeticama=this.getStudenti()[i];
                }
            }
        }
        return studentSPeticama;
    }
}
