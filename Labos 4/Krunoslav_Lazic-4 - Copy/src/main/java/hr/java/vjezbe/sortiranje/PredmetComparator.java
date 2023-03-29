package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class PredmetComparator implements Comparator<Predmet> {

    @Override
    public int compare(Predmet p1, Predmet p2) {
        if (p1.getBrojEctsBodova().compareTo(p2.getBrojEctsBodova())>0){
            return 1;
        } else if (p1.getBrojEctsBodova().compareTo(p2.getBrojEctsBodova())<0) {
            return -1;
        }
        else {
            return 0;
        }
    }

}
