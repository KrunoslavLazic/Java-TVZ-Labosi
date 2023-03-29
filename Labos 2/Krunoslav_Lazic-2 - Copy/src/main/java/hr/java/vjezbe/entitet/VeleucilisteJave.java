package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    public VeleucilisteJave(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti) {
        super(naziv, profesori, studenti, ispiti, predmeti);
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        BigDecimal prosjekOcjena = BigDecimal.ZERO;
        Student studentRektorova=new Student("noname","nolastname","0", LocalDate.of(1,1,2001));

        for (int i=0;i<this.getStudenti().length;i++){
            Ispit[] ispitiStudenta=(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti()[i]));
            BigDecimal prosjek=odrediProsjekOcjenaNaIspitima(ispitiStudenta);
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
