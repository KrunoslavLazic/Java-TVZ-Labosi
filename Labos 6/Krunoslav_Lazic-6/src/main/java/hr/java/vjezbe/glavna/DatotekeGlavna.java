package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DatotekeGlavna {

    public static final String PROFESORI_DAT = "dat\\profesori.txt";
    public static final String PREDMETI_DAT = "dat\\predmeti.txt";
    public static final String STUDENTI_DAT = "dat\\studenti.txt";
    public static final String ISPITI_DAT = "dat\\ispiti.txt";
    public static final String OBRAZOVNE_USTANOVE_DAT = "dat\\obrazovneustanove.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy.");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy. H:mm");


    public static void main(String[] args) {

    }

    public static Map<Long, Profesor> getProfesori (String imeDat){
        HashMap<Long, Profesor> profesori = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(imeDat))){
            while(scanner.hasNext()){

                long id = scanner.nextLong();

                scanner.nextLine();

                String sifra = scanner.nextLine();
                String ime = scanner.nextLine();
                String prezime = scanner.nextLine();
                String titula = scanner.nextLine();

                Profesor profesor = new Profesor.Builder()
                                        .withId(id)
                                        .withSifra(sifra)
                                        .withTitula(titula)
                                        .build(ime,prezime);
                profesori.put(profesor.getId(),profesor);
            }

        }
        catch (FileNotFoundException fnfe){
            throw new RuntimeException(String.format("File not found %s%n",imeDat));
        }
        return profesori;
    }
    public static Map<Long, Student> getStudenti (String imeDat){
        HashMap<Long, Student> studenti = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(imeDat))){
            while (scanner.hasNextLine()) {
                long id = scanner.nextLong();
                scanner.nextLine();

                String ime = scanner.nextLine();
                String prezime = scanner.nextLine();
                String jmbag = scanner.nextLine();
                LocalDate datumRodjenja = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);

                Student student = new Student(id, ime, prezime, jmbag, datumRodjenja);
                studenti.put(student.getId(), student);
            }


        }
        catch (FileNotFoundException fnfe){
            throw new RuntimeException(String.format("File not found %s%n",imeDat));
        }
        return studenti;
    }
    public static Map<Long, Predmet> getPredmeti (String imeDat, Map<Long, Profesor> profesori,Map<Long, Student> sviStudenti){
        HashMap<Long, Predmet> predmeti = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(imeDat))){
            while (scanner.hasNextLine()) {
                long id = scanner.nextLong();
                scanner.nextLine();

                String sifra = scanner.nextLine();
                String naziv = scanner.nextLine();
                Integer brojECTSa = scanner.nextInt();
                scanner.nextLine();

                Profesor profesor = profesori.get(scanner.nextLong());
                scanner.nextLine();

                var studenti = mapGetMany(sviStudenti, parseIds(scanner.nextLine()));

                Predmet predmet = new Predmet(id, sifra, naziv, brojECTSa,profesor, new HashSet<>(studenti));
                predmeti.put(predmet.getId(), predmet);
            }


        }
        catch (FileNotFoundException fnfe){
            throw new RuntimeException(String.format("File not found %s%n",imeDat));
        }
        return predmeti;
    }
    static Map<Long, Ispit> getIspiti(String filename, Map<Long, Student> studenti, Map<Long, Predmet> predmeti) {
        HashMap<Long, Ispit> ispiti = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                long id = scanner.nextLong();
                scanner.nextLine();

                Predmet predmet = predmeti.get(scanner.nextLong());
                scanner.nextLine();

                Student student = studenti.get(scanner.nextLong());
                scanner.nextLine();

                Ocjena ocjena = ocjenaUEnum(scanner.nextInt());
                scanner.nextLine();

                LocalDateTime datumIVrijeme = LocalDateTime.parse(scanner.nextLine(), DATE_TIME_FORMAT);
                String zgrada = scanner.nextLine();
                String dvorana = scanner.nextLine();

                Ispit ispit = new Ispit(id, predmet, student, ocjena, datumIVrijeme, new Dvorana(zgrada, dvorana));
                ispiti.put(ispit.getId(), ispit);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found: %s%n", filename));
        }

        return ispiti;
    }
    public static Map<Long, ObrazovnaUstanova> getObrazovnaUstanove(String filename, Map<Long, Profesor> sviProfesori, Map<Long, Predmet> sviPredmeti, Map<Long, Ispit> sviIspiti) {
        HashMap<Long, ObrazovnaUstanova> obrazovneUstanove = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                long id = scanner.nextLong();
                scanner.nextLine();

                String naziv = scanner.nextLine();
                int vrstaObrazovneUstanove = scanner.nextInt();
                scanner.nextLine();

                List<Predmet> predmeti = mapGetMany(sviPredmeti, parseIds(scanner.nextLine()));
                List<Profesor> profesori = mapGetMany(sviProfesori, parseIds(scanner.nextLine()));
                List<Ispit> ispiti = mapGetMany(sviIspiti, parseIds(scanner.nextLine()));

                ObrazovnaUstanova obrazovnaUstanova = switch (vrstaObrazovneUstanove) {
                    case 1 -> new VeleucilisteJave(id, naziv, profesori, profesori, ispiti);
                    case 2 -> new FakultetRacunarstva(id, naziv, predmeti, profesori, ispiti);
                    default -> {
                        String message = String.format("Nedozvoljena vrijednost za obrazovnu ustanovu: %d", vrstaObrazovneUstanove);
                        throw new RuntimeException(message);
                    }
                };

                obrazovneUstanove.put(obrazovnaUstanova.getId(), obrazovnaUstanova);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File not found: %s%n", filename));
        }

        return obrazovneUstanove;

    }

    public static <TKey, TValue> List<TValue> mapGetMany(Map<TKey, TValue> map, List<TKey> keys) {
        var values = new ArrayList<TValue>();

        for (var key : keys) {
            values.add(map.get(key));
        }

        return values;
    }
    public static <T> List<Long> parseIds(String ids) {
        return Arrays.stream(ids.split(" ")).map(Long::parseLong).collect(Collectors.toList());
    }

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
