package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Visokoskolska {

    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Student student, Ocjena pismeniZRad, Ocjena obranaZRad);
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException{

        Integer suma=0,brojOcjena=0;

        for (int i=0;i<ispiti.size();i++){
            if (ispiti.get(i).getOcjena().getOcjena()>1) {
                suma += ispiti.get(i).getOcjena().getOcjena();
                brojOcjena++;
            }
            else {
                throw new NemoguceOdreditiProsjekStudentaException(String.format("Student %s %s je ocjenjen negativom ocjenom iz predmeta %s (%s)!",
                        ispiti.get(i).getStudent().getIme(),
                        ispiti.get(i).getStudent().getPrezime(),
                        ispiti.get(i).getPredmet().getNaziv(),
                        ispiti.get(i).getPredmet().getSifra()));
            }
        }

        return BigDecimal.valueOf(suma/brojOcjena);
    }
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student){

        List<Ispit> ispitiStudentovi=new ArrayList<>();

        for (Ispit ispit : ispiti) {
            if ((ispit.getStudent().getJmbag().compareTo(student.getJmbag())) == 0) {
                ispitiStudentovi.add(ispit);
            }
        }

        return ispitiStudentovi;
    }
    default List<Ispit> ispitiOveGodine(List<Ispit> ispiti, Integer godina){

        List<Ispit> ispitiOveGodine = new ArrayList<>();
        for (Ispit ispit : ispiti ){
            if (ispit.getDatumIVrijeme().getYear() == godina){
                ispitiOveGodine.add(ispit);
            }
        }
        return ispitiOveGodine;
    }
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti){


        List<Ispit> filtriraniIspiti=new ArrayList<>();

        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena().getOcjena() > 1) {
                filtriraniIspiti.add(ispit);
            }
        }
        return filtriraniIspiti;
    }

}
