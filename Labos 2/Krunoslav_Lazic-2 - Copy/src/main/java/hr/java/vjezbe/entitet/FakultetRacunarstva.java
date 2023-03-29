package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

    public FakultetRacunarstva(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti) {
        super(naziv, profesori, studenti, ispiti, predmeti);
    }
    public Student odrediStudentaZaRektorovuNagradu(){

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
                if (studentRektorova.getDatumRodjenja().isBefore(this.getStudenti()[i].getDatumRodjenja())){
                    prosjekOcjena=prosjek;
                    studentRektorova=this.getStudenti()[i];
                }
            }
        }

        return studentRektorova;
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniDRad, int obranaDRad) {

        int zbrojOcjenaIspita=0;
        for (Ispit ispit : ispiti){
            zbrojOcjenaIspita+=ispit.getOcjena();
        }
        double prosjekOcjenaIspita=zbrojOcjenaIspita/ispiti.length;

        return BigDecimal.valueOf((prosjekOcjenaIspita*3+pismeniDRad+obranaDRad)/5);
    }

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
