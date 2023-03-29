package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    public FakultetRacunarstva(long id,String naziv, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti, List<Predmet> predmeti) {
        super(id,naziv, profesori, studenti, ispiti, predmeti);
    }

    /**
     * Metoda za racunanje studenta za rektorovu nagradu
     * @return vraca studenta koji je izabran
     */
    public Student odrediStudentaZaRektorovuNagradu(){

        BigDecimal prosjekOcjena = BigDecimal.ZERO;
        BigDecimal prosjek=BigDecimal.ZERO;
        Student studentRektorova=new Student(112l,"noname","nolastname","0", LocalDate.of(1,1,2001));

        for (int i=0;i<this.getStudenti().size();i++){
            List<Ispit> ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti().get(i)));
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
            }
            catch (NemoguceOdreditiProsjekStudentaException e){
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!",
                        this.getStudenti().get(i).getIme(), this.getStudenti().get(i).getPrezime()), e);
            }
            if (prosjek.compareTo(prosjekOcjena)>0){
                prosjekOcjena=prosjek;
                studentRektorova=this.getStudenti().get(i);
            }
            else if(prosjek.compareTo(prosjekOcjena)==0){
                String najmladjiStudenti = String.format("%s %s, %s %s",
                        studentRektorova.getIme(),
                        studentRektorova.getPrezime(),
                        this.getStudenti().get(i).getIme(),
                        this.getStudenti().get(i).getPrezime());

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
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Student student, Ocjena pismeniDRad, Ocjena obranaDRad) {

        List<Ispit> filtiriraniIspiti=filtrirajIspitePoStudentu(ispiti,student);
        int zbrojOcjenaIspita=0;
        for (Ispit ispit : filtiriraniIspiti){
            zbrojOcjenaIspita+=ispit.getOcjena().getOcjena();
        }

        double prosjekOcjenaIspita=zbrojOcjenaIspita/filtiriraniIspiti.size();

        return BigDecimal.valueOf((prosjekOcjenaIspita*3+pismeniDRad.getOcjena()+obranaDRad.getOcjena())/5);
    }

    /**
     * Metoda za odredivanje najboljeg studenta na godini
     * @param godina godina koje je taj student bio najbolji
     * @return vraca odabranog studenta
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Student studentSPeticama=new Student(40l,"default","default", "0",LocalDate.of(1,1,2001));
        int petice=0,maxBrojPetica=0,index=0;

        for (int i=0;i<this.getStudenti().size();i++){
            List<Ispit> ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti().get(i)));
            petice++;

            for (int j=0;j< ispitiStudenta.size();j++){
                if (ispitiStudenta.get(i).getOcjena().getOcjena()==5){
                    petice++;
                }
            }
            if (petice>maxBrojPetica){
                maxBrojPetica=petice;
                studentSPeticama=this.getStudenti().get(i);
                index=i;
            }
            else if(maxBrojPetica==petice){
                if (i<=index){
                    studentSPeticama=this.getStudenti().get(i);
                }
            }
        }
        return studentSPeticama;
    }
}
