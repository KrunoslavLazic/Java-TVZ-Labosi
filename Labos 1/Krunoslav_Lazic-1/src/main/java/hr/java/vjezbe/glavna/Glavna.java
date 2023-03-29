package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Glavna {

    private static final int broj_profesora = 2;
    private static final int broj_predmeta = 3;
    private static final int broj_studenata = 3;
    private static final int broj_ispita = 1;

    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);
        Profesor[] profesori=new Profesor[broj_profesora];
        Predmet[] predmeti=new Predmet[broj_predmeta];
        Student[] studenti=new Student[broj_studenata];
        Ispit[] ispiti=new Ispit[broj_ispita];

        //input profesora
        for (int i=0;i<profesori.length;i++){
            System.out.println("Unesite " + (i+1) + ". profesora:");
            profesori[i]=inputProfesor(input);
        }
        //input predmeta
        for (int i=0;i<predmeti.length;i++){
            System.out.println("Unesite " + (i+1) + ". predmet: ");
            predmeti[i]=inputPredmet(profesori,input);
        }
        //input studenti
        for (int i=0;i<studenti.length;i++){
            System.out.println("Unesite " + (i+1) + ". studenta: ");
            studenti[i]=inputStudent(input);
        }
        //input ispita
        for (int i=0;i<ispiti.length;i++){
            System.out.println("Unesite " + (i+1) + ". ispitni rok:");
            ispiti[i]=inputIspit(predmeti, studenti, input);
        }
        //ispis studenta s ocjenom 5 iz ispita
        for (Ispit ispit : ispiti) {
            if (ispit.getOcjena() == 5) {
                System.out.println("Student " + ispit.getStudent() +" je ostvario ocjenu '"
                        + ocjenaText(ispit.getOcjena()) + "' na predmetu '" + ispit.getPredmet() + "'");
            }
        }
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

        return new Profesor(sifra,ime,prezime,titula);
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
        int sifraProfesora=input.nextInt();
        System.out.println("Unesite broj studenata za predmet '" +naziv+"':");
        int brojStudenata=input.nextInt();
        input.nextLine();

        Profesor profesor=profesori[sifraProfesora-1];
        Student[] studenti=new Student[brojStudenata];

        return new Predmet(sifra,naziv,brojECTSbodova,profesor,studenti);
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

        return new Student(ime,prezime,jmbag, localDate);
    }
    public static Ispit inputIspit(Predmet[] predmeti, Student[] studenti, Scanner input){


        System.out.println("Odaberite predmet: ");
        for (Predmet predmet : predmeti){
            System.out.println(predmet.getSifra() + ". " + predmet.getNaziv());
        }
        System.out.println("Odabir >> ");
        int sifraPredmeta = input.nextInt();

        System.out.println("Odaberite studenta: ");
        for (int i=0; i< studenti.length; i++){
            System.out.println((i+1) + ". " + studenti[i].getIme() + " " + studenti[i].getPrezime());
        }
        System.out.println("Odabir >> ");
        int brojStudenta = input.nextInt();
        System.out.println("Unesite ocjenu na ispitu (1-5): ");
        Integer ocjena=input.nextInt();
        input.nextLine();

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):");
        String date = input.nextLine();
        String finalDate=date.replace("T"," ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(finalDate, formatter);

        return new Ispit(predmeti[sifraPredmeta-1],studenti[brojStudenta-1],ocjena, localDateTime);
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
