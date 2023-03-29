package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Glavna {

    private static final int broj_profesora = 2;
    private static final int broj_predmeta = 2;
    private static final int broj_studenata = 2;
    private static final int broj_ispita = 2;


    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);


        System.out.println("Unesite broj obrazovnih ustanova: ");
        Integer brojUstanova = input.nextInt();
        input.nextLine();
        ObrazovnaUstanova[] ustanove=new ObrazovnaUstanova[brojUstanova];

        for (int a=0;a<brojUstanova;a++) {

            Profesor[] profesori=new Profesor[broj_profesora];
            Predmet[] predmeti=new Predmet[broj_predmeta];
            Student[] studenti=new Student[broj_studenata];
            Ispit[] ispiti=new Ispit[broj_ispita];


            for (int i = 0; i < profesori.length; i++) {
                System.out.println("Unesite " + (i + 1) + ". profesora:");
                profesori[i] = inputProfesor(input);
            }
            //input predmeta
            for (int i = 0; i < predmeti.length; i++) {
                System.out.println("Unesite " + (i + 1) + ". predmet: ");
                predmeti[i] = inputPredmet(profesori, input);
            }
            //input studenti
            for (int i = 0; i < studenti.length; i++) {
                System.out.println("Unesite " + (i + 1) + ". studenta: ");
                studenti[i] = inputStudent(input);
            }
            //input ispita
            for (int i = 0; i < ispiti.length; i++) {
                System.out.println("Unesite " + (i + 1) + ". ispitni rok:");
                ispiti[i] = inputIspit(predmeti, studenti, input);
            }
            //ispit profesora s ocjenom 5 iz ispita
            for (int i = 0; i < ispiti.length; i++) {
                if (ispiti[i].getOcjena() == 5) {
                    System.out.println("Student " + ispiti[i].getStudent().getIme() + " " + ispiti[i].getStudent().getPrezime() +
                            " je ostvario ocjenu '" + ocjenaText(ispiti[i].getOcjena()) + "' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
                }
                //Student Jadranko Marić je ostvario ocjenu 'izvrstan' na predmetu 'Programiranje u jeziku Java'
            }

            ustanove[a]=inputObUstanove(input,profesori,studenti,ispiti,predmeti);

        }
    }

    public static ObrazovnaUstanova inputObUstanove(Scanner input, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti){

        ObrazovnaUstanova ustanova;

        System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva):");
        Integer vrstaUstnoave= input.nextInt();
        input.nextLine();
        System.out.println("Unesite naziv obrazovne ustanove");
        String imeUstanove=input.nextLine();

        if (vrstaUstnoave==1){
            ustanova=new VeleucilisteJave(imeUstanove,profesori,studenti,ispiti, predmeti);
        }
        else{
            ustanova=new FakultetRacunarstva(imeUstanove,profesori,studenti,ispiti,predmeti);
        }

        for (Student student : studenti){

            System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " +student.getPrezime());
            Integer ocjenaZavRada=input.nextInt();
            System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " +student.getPrezime());
            Integer ocjenaObranaZavRadaA=input.nextInt();

            System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime()
                    + " je " + ustanova.izracunajKonacnuOcjenuStudijaZaStudenta(ispiti,ocjenaZavRada,ocjenaObranaZavRadaA));

        }
        if (ustanova instanceof FakultetRacunarstva){

            Student studentRektor=((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu();
            System.out.println("Student koji je osvojio rektorovu nagradu je: " + studentRektor.getIme() + " " + studentRektor.getPrezime() +
                    " " + studentRektor.getJmbag());

        }
        return ustanova;

    }
    public static Profesor inputProfesor(Scanner input){
        System.out.println("Unesite šifru profesora: ");
        String sifra = input.nextLine();
        System.out.println("Unesite ime profesora: ");
        String ime = input.nextLine();
        System.out.println("Unesite prezime profesora: ");
        String prezime=input.nextLine();
        System.out.println("Unesite titulu profesora: ");
        String titula=input.nextLine();

        Profesor profesor=new Profesor.Builder()
                .withSifra(sifra)
                .withTitula(titula)
                .build(ime,prezime);
        return profesor;
    }
    public static Predmet inputPredmet(Profesor[] profesori,Scanner input){

        System.out.println("Unesite sifru predmeta: ");
        String sifra = input.nextLine();
        System.out.println("Unesite naziv predmeta: ");
        String naziv= input.nextLine();
        System.out.println("Unesite broj ECTS bodova za predmet '" + naziv +"':" );
        Integer brojECTSbodova=input.nextInt();
        System.out.println("Odaberite profesora: ");
        for (int i=0;i<profesori.length;i++){
            System.out.println((i+1)+". " + profesori[i].getIme() + " " + profesori[i].getPrezime());
        }
        System.out.println("Odabir >> ");
        Integer sifraProfesora=input.nextInt();
        System.out.println("Unesite broj studenata za predmet '" +naziv+"':");
        Integer brojStudenata=input.nextInt();

        input.nextLine();

        Profesor profesor=profesori[sifraProfesora-1];
        Student[] studenti=new Student[brojStudenata];
        Predmet predmet=new Predmet(sifra,naziv,brojECTSbodova,profesor,studenti);
        return predmet;
    }
    public static Student inputStudent(Scanner input){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        System.out.println("Unesite ime studenta: ");
        String ime = input.nextLine();
        System.out.println("Unesite prezime studenta: ");
        String prezime=input.nextLine();
        System.out.println("Unesite datum rođenja studenta " + prezime +
                            " " + ime + " u formatu (dd.MM.yyyy.):");
        String date = input.nextLine();
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("Unesite JMBAG studenta " + ime + " " + prezime+" : ");
        String jmbag=input.nextLine();

        Student student=new Student(ime,prezime,jmbag, localDate);
        return student;
    }
    public static Ispit inputIspit(Predmet[] predmeti, Student[] studenti, Scanner input){


        System.out.println("Odaberite predmet: ");
        for (Predmet predmet : predmeti){
            System.out.println(predmet.getSifra() + ". " + predmet.getNaziv());
        }
        System.out.println("Odabir >> ");
        Integer sifraPredmeta = input.nextInt();
        input.nextLine();
        System.out.println("Unesite naziv dvorane: ");
        String dvorana=input.nextLine();
        System.out.println("Unesite naziv zgrade: ");
        String zgrada=input.nextLine();
        System.out.println("Odaberite studenta: ");
        for (int i=0; i< studenti.length; i++){
            System.out.println((i+1) + ". " + studenti[i].getIme() + " " + studenti[i].getPrezime());
        }
        System.out.println("Odabir >> ");
        Integer brojStudenta = input.nextInt();
        System.out.println("Unesite ocjenu na ispitu (1-5): ");
        Integer ocjena=input.nextInt();
        input.nextLine();
        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):");
        String date = input.nextLine();
        String finalDate=date.replace("T"," ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(finalDate, formatter);

        Ispit ispit = new Ispit(predmeti[sifraPredmeta-1],studenti[brojStudenta-1],ocjena, localDateTime, new Dvorana(dvorana,zgrada));
        return ispit;
    }
    //funkcija za pretvorbu ocjene iz numeričkog u tekstualni
    public static String ocjenaText(Integer ocjena){
        return switch (ocjena){
            case 1->"nedovoljan";
            case 2->"dovoljan";
            case 3->"dobar";
            case 4->"vrlo dobar";
            case 5->"izvrstan";
            default->"bad input";
        };
    }

}
