package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.Arrays;

public interface Visokoskolska {

    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int pismeniZRad, int obranaZRad);
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException{

        Integer suma=0,brojOcjena=0;

        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() > 1) {
                suma += ispit.getOcjena();
                brojOcjena++;
            } else {
                throw new NemoguceOdreditiProsjekStudentaException(String.format("Student %s %s je ocjenjen negativom ocjenom iz predmeta %s (%s)!",
                        ispit.getStudent().getIme(),
                        ispit.getStudent().getPrezime(),
                        ispit.getPredmet().getNaziv(),
                        ispit.getPredmet().getSifra()));
            }
        }

        return BigDecimal.valueOf(suma/brojOcjena);
    }
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti,Student student){

        Integer brojacIspita=0;
        Integer counter=0;

        for (Ispit value : ispiti) {
            if ((value.getStudent().getJmbag().compareTo(student.getJmbag())) == 0) {
                brojacIspita++;
            }
        }

        Ispit[] ispitiStudentovi=new Ispit[brojacIspita];

        for (Ispit ispit : ispiti) {
            if ((ispit.getStudent().getJmbag().compareTo(student.getJmbag())) == 0) {
                ispitiStudentovi[counter] = ispit;
                counter++;
            }
        }

        return ispitiStudentovi;
    }
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){

        Integer counter=0;
        Ispit[] filtriraniIspiti=new Ispit[0];
        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() > 1) {
                filtriraniIspiti = Arrays.copyOf(filtriraniIspiti, (counter++));
                filtriraniIspiti[counter] = ispit;
                counter++;
            }
        }
        return filtriraniIspiti;
    }

}
