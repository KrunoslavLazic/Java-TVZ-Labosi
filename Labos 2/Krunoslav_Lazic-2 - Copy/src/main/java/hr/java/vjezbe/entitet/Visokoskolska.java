package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public interface Visokoskolska {

    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniZRad, int obranaZRad);
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti){

        Integer ocjene=0;

        for (Ispit ispit : ispiti){
            ocjene+= ispit.getOcjena();
        }

        return BigDecimal.valueOf(ocjene/ispiti.length);
    }
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti,Student student){


        Integer brojacIspita=0;
        Integer j=0;

        for (int i=0;i<ispiti.length;i++){
            if ((ispiti[i].getStudent().getJmbag().compareTo(student.getJmbag()))==0){
                brojacIspita++;
            }
        }

        Ispit[] ispitiStudentovi=new Ispit[brojacIspita];

        for (int i=0;i<ispiti.length;i++){
            if ((ispiti[i].getStudent().getJmbag().compareTo(student.getJmbag()))==0){
                ispitiStudentovi[j]=ispiti[i];
                j++;
            }
        }

        return ispitiStudentovi;
    }
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){

        Integer counter=0;
        Ispit[] filtriraniIspiti=new Ispit[0];
        for (int i=0;i< ispiti.length;i++){
            if (ispiti[i].getOcjena()>1){
                filtriraniIspiti= Arrays.copyOf(filtriraniIspiti,(counter++));
                filtriraniIspiti[counter]=ispiti[i];
                counter++;
            }
        }
        return filtriraniIspiti;
    }


}
