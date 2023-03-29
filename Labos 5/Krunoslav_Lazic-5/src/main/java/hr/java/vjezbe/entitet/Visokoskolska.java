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

        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena().getOcjena() > 1) {
                suma += ispit.getOcjena().getOcjena();
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
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student){

        List<Ispit> ispitiStudentovi=new ArrayList<>();
        Long forPetljaStart, forPetljaEnd, lambdaStart, lambdaEnd;

        forPetljaStart = System.currentTimeMillis();
        for (Ispit ispit : ispiti) {
            if ((ispit.getStudent().getJmbag().compareTo(student.getJmbag())) == 0) {
                ispitiStudentovi.add(ispit);
            }
        }
        forPetljaEnd = System.currentTimeMillis();
        System.out.println("Vrijeme potrebno za for petlju je " + (forPetljaEnd-forPetljaStart));

        lambdaStart = System.currentTimeMillis();
        ispitiStudentovi=ispiti.stream().filter( s-> s.getStudent().getJmbag().compareTo(student.getJmbag())==0).toList();
        lambdaEnd = System.currentTimeMillis();
        System.out.println("Vrijeme potrebna za lambdu je " + (lambdaEnd-lambdaStart));


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
