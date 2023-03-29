package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    public VeleucilisteJave(String naziv, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {

        Integer ispitiOveGodine=Integer.valueOf(0);
        for (Ispit ispit : this.getIspiti()){
            if (ispit.getDatumIVrijeme().getYear()==godina){
                ispitiOveGodine++;
            }
        }
        BigDecimal[] poljeProsjeka = new BigDecimal[ispitiOveGodine];
        for (int i=0;i<this.getStudenti().length;i++){
            poljeProsjeka[i]=odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(this.getIspiti(),this.getStudenti()[i]));
        }
        Arrays.sort(poljeProsjeka,(p1,p2)->p1.compareTo(p2));

        return this.getStudenti()[poljeProsjeka.length-1];
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniZRad, int obranaZRad) {

        int zbrojOcjenaIspita=0;
        for (Ispit ispit : ispiti){
            zbrojOcjenaIspita+=ispit.getOcjena();
        }
        double prosjekOcjenaIspita=zbrojOcjenaIspita/ispiti.length;

        return BigDecimal.valueOf((prosjekOcjenaIspita*2+pismeniZRad+obranaZRad)/4);

    }

}
