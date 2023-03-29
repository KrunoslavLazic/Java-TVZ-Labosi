package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.GodinaVecaOdException;
import hr.java.vjezbe.iznimke.NeispravanFormatGodineException;
import hr.java.vjezbe.iznimke.PogresnaSifraPredmetaException;
import hr.java.vjezbe.iznimke.PostojecaSifraPredmetaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Glavna {

    private static final int broj_profesora = 2;
    private static final int broj_predmeta = 2;
    private static final int broj_studenata = 2;
    private static final int broj_ispita = 2;
    private static boolean isException;
    private static final String KRIVI_TIP_PODATKA="Krivi tip podatka!";
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);

        Integer brojUstanova=0;

        do {
            try {
                System.out.println("Unesite broj obrazovnih ustanova: ");
                brojUstanova=input.nextInt();
                input.nextLine(); //ciscenje bufera od '\n' neovisno jeli iduci input string
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);
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
                predmeti[i] = inputPredmet(profesori, i,predmeti,input);
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
            for (Ispit ispit : ispiti) {
                if (ispit.getOcjena() == 5) {
                    System.out.println("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() +
                            " je ostvario ocjenu '" + ocjenaText(ispit.getOcjena()) + "' na predmetu '" + ispit.getPredmet().getNaziv() + "'");
                }
                //Student Jadranko Marić je ostvario ocjenu 'izvrstan' na predmetu 'Programiranje u jeziku Java'
            }
            ustanove[a]=inputObUstanove(input,profesori,studenti,ispiti,predmeti);
        }
    }

    /**
     * Funkcija za input jedne ustanove kroz konzolu
     * @param input pristup konzoli
     * @param profesori profesori koji rade na ustanovi
     * @param studenti studenti koji pohadaju tu ustanovu
     * @param ispiti ispiti koji su pisani u ovoj ustanovi
     * @param predmeti kolegiji koji se nalaze na kolegiju
     * @return vraca obrazovnu Ustanovu
     */
    public static ObrazovnaUstanova inputObUstanove(Scanner input, Profesor[] profesori, Student[] studenti, Ispit[] ispiti, Predmet[] predmeti){

        ObrazovnaUstanova ustanova;
        Integer vrstaUstanove=0, ocjenaZavRada=0, ocjenaObranaZavRada=0;
        String imeUstanove;

        do{
            try{
                System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju zelite unijeti (1 - Veleuciliste Jave, 2 - Fakultet racunarstva):");
                vrstaUstanove= input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);


        System.out.println("Unesite naziv obrazovne ustanove");
        imeUstanove=input.nextLine();

        if (vrstaUstanove==1){
            ustanova=new VeleucilisteJave(imeUstanove,profesori,studenti,ispiti, predmeti);
        }
        else{
            ustanova=new FakultetRacunarstva(imeUstanove,profesori,studenti,ispiti,predmeti);
        }

        for (Student student : studenti){

            do{
                try{
                    System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " +student.getPrezime());
                    ocjenaZavRada=input.nextInt();
                    input.nextLine();
                    isException=false;
                }
                catch (InputMismatchException ex){
                    System.out.println(KRIVI_TIP_PODATKA);
                    input.nextLine();
                    isException=true;
                    logger.error(KRIVI_TIP_PODATKA,ex);
                }
            }while(isException);

            do{
                try{
                    System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " +student.getPrezime());
                    ocjenaObranaZavRada=input.nextInt();
                    input.nextLine();
                    isException=false;
                }
                catch (InputMismatchException ex){
                    System.out.println(KRIVI_TIP_PODATKA);
                    input.nextLine();
                    isException=true;
                    logger.error(KRIVI_TIP_PODATKA,ex);
                }
            }while(isException);

            System.out.println("Konacna ocjena studija studenta " + student.getIme() + " " + student.getPrezime()
                    + " je " + ustanova.izracunajKonacnuOcjenuStudijaZaStudenta(ispiti,ocjenaZavRada,ocjenaObranaZavRada));

        }

        if (ustanova instanceof FakultetRacunarstva){
            Student studentRektor=((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu();
            System.out.println("Student koji je osvojio rektorovu nagradu je: " +
                    studentRektor.getIme() + " " + studentRektor.getPrezime() + " " + studentRektor.getJmbag());
        }

        return ustanova;
    }

    /**
     * Funkcija za unos jednog profesora kroz konzolu
     * @param input konzola
     * @return vraca profesora
     */
    public static Profesor inputProfesor(Scanner input){

        System.out.println("Unesite šifru profesora: ");
        String sifra = input.nextLine();
        System.out.println("Unesite ime profesora: ");
        String ime = input.nextLine();
        System.out.println("Unesite prezime profesora: ");
        String prezime=input.nextLine();
        System.out.println("Unesite titulu profesora: ");
        String titula=input.nextLine();

        return new Profesor.Builder()
                .withSifra(sifra)
                .withTitula(titula)
                .build(ime,prezime);
    }

    /**
     * Funkcija za unos jednog predmeta kroz konzolu
     * @param profesori moguci voditelji kolegija
     * @param input pristup konzoli
     * @return vraca Predmet
     */
    public static Predmet inputPredmet(Profesor[] profesori,int x,Predmet[] predmeti,Scanner input){

        Integer brojECTSbodova=0;
        Integer sifraProfesora=0;
        Integer brojStudenata=0;
        Integer godina=0;
        String sifra=null;
        String naziv;

        do{
            try{
                System.out.println("Unesite akademsku godinu predmeta:");
                godina=input.nextInt();
                godinaProvera(godina);
                isException=false;
            }
            catch (GodinaVecaOdException gve){
                System.out.println("Godina veca od 5!");
                input.nextLine();
                isException=true;
                logger.error("Godina veca od 5!",gve);
            }
            catch (NeispravanFormatGodineException nfge){
                System.out.println("Neispravan format godine!");
                input.nextLine();
                isException=true;
                logger.error("Neispravan format godine!",nfge);
            }
        }while(isException);

        do{
            try{
                System.out.println("Unesite sifru predmeta: ");
                sifra = input.nextLine();
                sifraProvjera(sifra,predmeti,x);
                isException=false;
            }
            catch (PostojecaSifraPredmetaException pspe){
                System.out.println("Ova sifra vec postoji!");
                input.nextLine();
                isException=true;
                logger.error("Ova sifra vec postoji!",pspe);
            }
            catch (PogresnaSifraPredmetaException pspe){
                System.out.println("Sifra ne smije pocimati sa znamenkom!");
                input.nextLine();
                isException=true;
                logger.error("Sifra ne smije pocimati sa znamenkom!",pspe);
            }
        }while(isException);

        System.out.println("Unesite naziv predmeta: ");
        naziv= input.nextLine();

        do {
            try {
                System.out.println("Unesite broj ECTS bodova za predmet '" + naziv + "':");
                brojECTSbodova = input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        System.out.println("Odaberite profesora: ");
        for (int i=0;i<profesori.length;i++){
            System.out.println((i+1)+". " + profesori[i].getIme() + " " + profesori[i].getPrezime());
        }

        do{
            try{
                System.out.println("Odabir >> ");
                sifraProfesora=input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);
        do{
            try{
                System.out.println("Unesite broj studenata za predmet '" +naziv+"':");
                brojStudenata=input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        Profesor profesor=profesori[sifraProfesora-1];
        Student[] studenti=new Student[brojStudenata];

        return new Predmet(sifra,naziv,brojECTSbodova,profesor,studenti,godina);
    }

    /**
     * Funkcija za unos jednog studenta kroz konzolu
     * @param input pristup konzoli
     * @return vraca studenta
     */
    public static Student inputStudent(Scanner input){

        String ime,prezime,date,jmbag;
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        System.out.println("Unesite ime studenta: ");
        ime = input.nextLine();
        System.out.println("Unesite prezime studenta: ");
        prezime=input.nextLine();
        System.out.println("Unesite datum rođenja studenta " + prezime +
                            " " + ime + " u formatu (dd.MM.yyyy.):");
        date = input.nextLine();
        localDate = LocalDate.parse(date, formatter);
        System.out.println("Unesite JMBAG studenta " + ime + " " + prezime+" : ");
        jmbag=input.nextLine();

        return new Student(ime,prezime,jmbag, localDate);
    }

    /**
     * Funkcija za unos jednog ispita kroz konzolu
     * @param predmeti kolegij iz kojeg se pisao ispit
     * @param studenti studenti koji su pisali ispit
     * @param input pristup konzoli
     * @return vraca ispit
     */
    public static Ispit inputIspit(Predmet[] predmeti, Student[] studenti, Scanner input){

        String dvorana,zgrada,date,finalDate;
        int sifraPredmeta =0,brojStudenta=0,ocjena=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

        System.out.println("Odaberite predmet: ");
        for (Predmet predmet : predmeti){
            System.out.println(predmet.getSifra() + ". " + predmet.getNaziv());
        }

        do{
            try{
                System.out.println("Odabir >> ");
                sifraPredmeta = input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        System.out.println("Unesite naziv dvorane: ");
        dvorana=input.nextLine();
        System.out.println("Unesite naziv zgrade: ");
        zgrada=input.nextLine();
        System.out.println("Odaberite studenta: ");
        for (int i=0; i< studenti.length; i++){
            System.out.println((i+1) + ". " + studenti[i].getIme() + " " + studenti[i].getPrezime());
        }
        do{
            try{
                System.out.println("Odabir >> ");
                brojStudenta = input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        do{
            try{
                System.out.println("Unesite ocjenu na ispitu (1-5): ");
                ocjena=input.nextInt();
                input.nextLine();
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):");
        date = input.nextLine();
        finalDate=date.replace("T"," ");
        LocalDateTime localDateTime = LocalDateTime.parse(finalDate, formatter);

        return new Ispit(predmeti[sifraPredmeta-1],studenti[brojStudenta-1],ocjena, localDateTime, new Dvorana(dvorana,zgrada));
    }
    /**
     * Funkcija za pretvorbu numerickog oblika ocjene u tekstualni
     * @param ocjena ocjena koja se konvergira
     * @return vraca string ocjene
     */
    public static String ocjenaText(Integer ocjena){
        return switch (ocjena){
            case 1->"nedovoljan";
            case 2->"dovoljan";
            case 3->"dobar";
            case 4->"vrlo dobar";
            case 5->"izvrstan";
            default->"wtf this ain't a grade";
        };
    }
    /**
     * funkcija za provjeru sifre predmeta
     * @param sifra unesena sifra preko konzole
     * @param predmeti predmeti koji su vec uneseni
     * @param i brojac unesenih predmeta
     * @throws PogresnaSifraPredmetaException iznimka u slucaju ako sifra pocinje sa znamenokom
     * @throws PostojecaSifraPredmetaException iznimka u slucaju ako sifra vec postoji za neki predmet
     */
    public static void sifraProvjera(String sifra, Predmet[] predmeti,int i)throws PogresnaSifraPredmetaException, PostojecaSifraPredmetaException {

        for (int a=0;a<10;a++){
            if (sifra.startsWith(Integer.toString(a))){
                throw new PogresnaSifraPredmetaException("Sifra ne smije pocimati sa znamenkom!");
            }
        }

        for(int j=0;j<i;j++){
            if(sifra.compareTo(predmeti[j].getSifra())==0){
                throw new PostojecaSifraPredmetaException("Ova sifra vec postoji!");
            }
        }
    }
    /**
     * Funkcija za provjeru ispravnosti unesene akademske godine za predmet
     * @param godina unesena godina preko konzole
     * @throws NeispravanFormatGodineException iznmika u slucaju krivog formata godine
     * @throws GodinaVecaOdException iznimka u slucaju ako je godina veca od 5
     */
    public static void godinaProvera(Integer godina)throws NeispravanFormatGodineException, GodinaVecaOdException {

        LocalDateTime trenutanDatum=LocalDateTime.now();
        Integer trenutnaGodina=trenutanDatum.getYear();

        if (godina <0 || godina>trenutnaGodina){
            throw new NeispravanFormatGodineException("Neispravan format godine!");
        }

        if (trenutnaGodina-godina>4){
            throw new GodinaVecaOdException("Godina veca od 5!");
        }

    }
}
