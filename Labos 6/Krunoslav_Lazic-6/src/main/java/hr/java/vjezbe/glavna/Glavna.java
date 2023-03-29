package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Glavna {

    private static final int broj_profesora = 2;
    private static final int broj_predmeta = 3;
    private static final int broj_studenata = 2;
    private static final int broj_ispita = 2;
    private static boolean isException;
    private static final String KRIVI_TIP_PODATKA="Krivi tip podatka!";
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);
        Integer brojUstanova=0;
        Sveuciliste<ObrazovnaUstanova> sveuciliste= new Sveuciliste<>();

        do {
            try {
                System.out.println("Unesite broj obrazovnih ustanova: ");
                brojUstanova=input.nextInt();
                input.nextLine(); //čišćenje bufera od '\n' neovisno jeli idući input string
                isException=false;
            }
            catch (InputMismatchException ex){
                System.out.println(KRIVI_TIP_PODATKA);
                input.nextLine();
                isException=true;
                logger.error(KRIVI_TIP_PODATKA,ex);
            }
        }while(isException);

        for (int a=0;a<brojUstanova;a++) {

            List<Profesor> profesori=new ArrayList<>();
            List<Predmet> predmeti=new ArrayList<>();
            List<Student> studenti=new ArrayList<>();
            List<Ispit> ispiti=new ArrayList<>();
            Map<Profesor,List<Predmet> > nositeljiKolegija= new HashMap<>();
            Long forLoopStart, forLoopEnd, lambdaStart, lambdaEnd;

            for (int i = 0; i < broj_profesora; i++) {
                System.out.println("Unesite " + (i + 1) + ". profesora:");
                profesori.add(inputProfesor(input));
            }
            //input predmeta
            for (int i = 0; i < broj_predmeta; i++) {
                System.out.println("Unesite " + (i + 1) + ". predmet: ");
                Predmet predmet = inputPredmet(profesori, input);
                predmeti.add(predmet);
                Profesor nositelj = predmet.getNositelj();
                if (nositeljiKolegija.containsKey(nositelj)) {
                    nositeljiKolegija.get(nositelj).add(predmet);
                } else {
                    nositeljiKolegija.put(nositelj, new ArrayList<>() {{ add(predmet); }});
                }
            }
            //ispis mape profesora i kolegija na kojima su nositelji
            outputProfesori(nositeljiKolegija);

            //input studenti
            for (int i = 0; i < broj_studenata; i++) {
                System.out.println("Unesite " + (i + 1) + ". studenta: ");
                studenti.add(inputStudent(input));
            }

            //input ispita i studenta u set studenata na kolegiju tog ispita
            for (int i = 0; i < broj_ispita; i++) {
                System.out.println("Unesite " + (i + 1) + ". ispitni rok:");
                ispiti.add(inputIspit(predmeti, studenti, input));
            }


            forLoopStart = System.currentTimeMillis();
            for (Ispit ispit : ispiti) {
                if (ispit.getOcjena().getOcjena() == 5) {
                    System.out.println("Student " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() +
                            " je ostvario ocjenu '" + (ispit.getOcjena().getTekstOcjene()) + "' na predmetu '" + ispit.getPredmet().getNaziv() + "'");
                }
            }
            forLoopEnd = System.currentTimeMillis();
            System.out.println("Vrijeme potrebno za for petlju: " + (forLoopEnd-forLoopStart));

            lambdaStart= System.currentTimeMillis();
            ispiti.stream().filter(s -> s.getOcjena().equals(Ocjena.IZVRSTAN)).forEach(s -> System.out.println(s.getStudent().getIme() + s.getStudent().getPrezime()));
            lambdaEnd= System.currentTimeMillis();
            System.out.println("Vrijeme potrebno za lambdu: " + (lambdaEnd-lambdaStart));

            forLoopStart = System.currentTimeMillis();
            for (Predmet predmet : predmeti){
                System.out.println(" Studenti na predmetu " + predmet.getNaziv() + " su: "
                + predmet.getStudenti());
            }
            forLoopEnd = System.currentTimeMillis();
            System.out.println("Vrijeme potrebno za for petlju: " + (forLoopEnd-forLoopStart));

            lambdaStart= System.currentTimeMillis();
            predmeti.stream().map(Predmet::getStudenti).forEach(System.out::println);
            lambdaEnd= System.currentTimeMillis();
            System.out.println("Vrijeme potrebno za lambdu: " + (lambdaEnd-lambdaStart));

            sveuciliste.dodajObrazovnuUstanovu(inputObUstanove(input,profesori,studenti,ispiti,predmeti));
        }

        sveuciliste.getListaSveucilista().stream().sorted((x,y)->{
            if (x.getStudenti().size()>y.getStudenti().size()){
                return 1;
            } else if (x.getStudenti().size()<y.getStudenti().size()) {
                return -1;
            }
            else
                return x.getNaziv().compareTo(y.getNaziv());
        }).forEach(System.out::println);


    }
    /**
     * Funkcija za input jedne ustanove kroz konzolu
     * @param input pristup konzoli
     * @param profesori profesori koji rade na ustanovi
     * @param studenti studenti koji pohadaju tu ustanovu
     * @param ispiti ispiti koji su pisani u ovoj ustanovi
     * @param predmeti kolegiji koji se nalaze na kolegiju
     * @return vraca obrazovnu ustanovu
     */
    public static ObrazovnaUstanova inputObUstanove(Scanner input, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti, List<Predmet> predmeti){

        ObrazovnaUstanova ustanova;
        Integer vrstaUstanove=0, ocjenaZavRada=0, ocjenaObranaZavRada=0;
        String imeUstanove;
        Student studentZaIspis;

        do{
            try{
                System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva):");
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
            ustanova=new VeleucilisteJave(233333l,imeUstanove,profesori,studenti,ispiti, predmeti);
        }
        else{
            ustanova=new FakultetRacunarstva(11111l,imeUstanove,profesori,studenti,ispiti,predmeti);
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

            System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime()
                    + " je " + ustanova.izracunajKonacnuOcjenuStudijaZaStudenta(ispiti,student,ocjenaUEnum(ocjenaZavRada),ocjenaUEnum(ocjenaObranaZavRada)));

        }

        if (ustanova instanceof FakultetRacunarstva){
            studentZaIspis=((FakultetRacunarstva) ustanova).odrediStudentaZaRektorovuNagradu();
            System.out.println("Student koji je osvojio rektorovu nagradu je: " +
                    studentZaIspis.getIme() + " " + studentZaIspis.getPrezime() + " " + studentZaIspis.getJmbag());
        }
        else{
            studentZaIspis= ustanova.odrediNajuspjesnijegStudentaNaGodini(2022);
            System.out.println("Najbolji student 2022. godine je " + studentZaIspis.getIme()+ " " + studentZaIspis.getPrezime()
                                + "JMBAG: " + studentZaIspis.getJmbag());
        }

        return ustanova;
    }

    /**
     * Funkcija za unos jednog profesora kroz konzolu
     * @param input pristup konzoli
     * @return vraca jednog profesora
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
     * @return
     */
    public static Predmet inputPredmet(List<Profesor> profesori,Scanner input){

        Integer brojECTSbodova=0;
        Integer sifraProfesora=0;
        String sifra;
        String naziv;

        System.out.println("Unesite sifru predmeta: ");
        sifra = input.nextLine();
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
        for (int i=0;i<profesori.size();i++){
            System.out.println((i+1)+". " + profesori.get(i).getIme() + " " + profesori.get(i).getPrezime());
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

        Profesor profesor=profesori.get(sifraProfesora-1);

        return new Predmet(22l,sifra,naziv,brojECTSbodova,profesor,new HashSet<>());
    }

    /**
     * Funkcija za unos jednog studenta kroz konzolu
     * @param input pristup konzoli
     * @return vraca jednog studenta
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

        return new Student(20l,ime,prezime,jmbag, localDate);
    }

    /**
     * Funkcija za unos jednog ispita kroz konzolu
     * @param predmeti kolegij iz kojeg se pisao ispit
     * @param studenti studenti koji su pisali ispit
     * @param input pristup konzoli
     * @return vraca jedan ispit
     */
    public static Ispit inputIspit(List<Predmet> predmeti, List<Student> studenti, Scanner input){

        String dvorana, zgrada,date,finalDate;
        Integer sifraPredmeta =0,brojStudenta=0,ocjena=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

        System.out.println("Odaberite predmet: ");
        for (int i =0;i<predmeti.size();i++){
            System.out.println((i+1) + ". " + predmeti.get(i).getNaziv());
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
        for (int i=0; i< studenti.size(); i++){
            System.out.println((i+1) + ". " + studenti.get(i).getIme() + " " + studenti.get(i).getPrezime());
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

        Predmet predmet = predmeti.get(sifraPredmeta-1);
        Student student = studenti.get(brojStudenta-1);
        SortedSet<Student> set = predmet.getStudenti();
        set.add(student);
        predmet.setStudenti(set);

        return new Ispit(23l,predmeti.get(sifraPredmeta-1),studenti.get(brojStudenta-1),ocjenaUEnum(ocjena), localDateTime, new Dvorana(dvorana,zgrada));
    }

    /**
     * Funkcija za ispis profesora i kolegija na kojima su oni nositelji
     * @param nositelji mapa profesora i kolegija
     */
    public static void outputProfesori(Map<Profesor, List<Predmet>> nositelji){
        nositelji.forEach((nositelj, predmeti) -> {
            System.out.printf("Profesor %s %s predaje sljedeće predmete:%n", nositelj.getIme(), nositelj.getPrezime());
            for (int i = 0; i<predmeti.size();i++) {
                System.out.println(i+1 + ") "+ predmeti.get(i).getNaziv());
            }
        });
    }
    /**
     * Funkcija za pretvorbu numerickog oblika ocjene u tekstualni
     * @param ocjena ocjena koja se konvergira
     * @return vraca parsiranu integer ocjenu u enum
     */
    public static Ocjena ocjenaUEnum(Integer ocjena){
        return switch (ocjena){
            case 1->Ocjena.NEDOVOLJAN;
            case 2->Ocjena.DOVOLJAN;
            case 3->Ocjena.DOBAR;
            case 4->Ocjena.VRLO_DOBAR;
            case 5->Ocjena.IZVRSTAN;
            default->throw new IllegalArgumentException("Unexpected value: " + ocjena);
        };
    }


}
