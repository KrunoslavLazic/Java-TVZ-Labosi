package vjezbe.baza;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vjezbe.entitet.*;
import vjezbe.glavna.HelloApplication;
import vjezbe.iznimke.BazaPodatakaException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BazaPodataka {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);
    public static Connection spajanjeNaBazu() throws SQLException, IOException{

        Properties configuration = new Properties();

        configuration.load(new FileReader("dat/Database.properties"));

        String dbURL = configuration.getProperty("databaseURL");
        String dbUser = configuration.getProperty("databaseUser");
        String dbPass = configuration.getProperty("databasePass");

        return DriverManager.getConnection(dbURL,dbUser,dbPass);
    }

    public static List<Profesor> dohvatiProfesore(Profesor profesor) throws BazaPodatakaException {

        List<Profesor> listaProfesora = new ArrayList<>();

        try (Connection connection = spajanjeNaBazu()){

            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PROFESOR WHERE 1=1");

            if (Optional.ofNullable(profesor).isPresent()){

                    sqlUpit.append(" AND ID = " + profesor.getId());

                if (!Optional.ofNullable(profesor.getSifra()).map(String::isBlank).orElse(true)) {

                    sqlUpit.append(" AND SIFRA LIKE '%" + profesor.getSifra() + "%'");

                } else if (!Optional.ofNullable(profesor.getIme()).map(String::isBlank).orElse(true)) {

                    sqlUpit.append(" AND IME LIKE '%" + profesor.getIme() + "%'");

                } else if (!Optional.ofNullable(profesor.getPrezime()).map(String::isBlank).orElse(true)) {

                    sqlUpit.append(" AND PREZIME LIKE '%" + profesor.getPrezime() + "%'");

                } else if (!Optional.ofNullable(profesor.getTitula()).map(String::isBlank).orElse(true)) {

                    sqlUpit.append(" AND TITULA LIKE '%" + profesor.getTitula() + "%'");
                }
            }

            Statement query = connection.createStatement();

            ResultSet rs = query.executeQuery(sqlUpit.toString());

            while(rs.next()){
                Profesor newProfesor = getProfesorFromResultSet(rs);
                listaProfesora.add(newProfesor);
            }

        }
        catch (SQLException | IOException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podatka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return listaProfesora;

    }
    public static List<Student> dohvatiStudent(Student student) throws BazaPodatakaException{

        List<Student> listaStudenata = new ArrayList<>();

        try (Connection connection = spajanjeNaBazu()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM STUDENT WHERE 1=1");

            if (Optional.ofNullable(student).isPresent()){

                if (Optional.ofNullable(student).map(Student::getId).isPresent()) {

                    sqlUpit.append(" AND ID = " + student.getId());

                    if (!Optional.ofNullable(student.getIme()).map(String::isBlank).orElse(true)){
                        sqlUpit.append(" AND IME LIKE '%" + student.getIme() + "%'");
                    }
                    else if (!Optional.ofNullable(student.getPrezime()).map(String::isBlank).orElse(true)){
                        sqlUpit.append(" AND PREZIME LIKE '%" + student.getPrezime() + "%'");
                    }
                    else if (!Optional.ofNullable(student.getJmbag()).map(String::isBlank).orElse(true)){
                        sqlUpit.append(" AND JMBAG LIKE '%" + student.getJmbag() + "%'");
                    }
                    else if (Optional.ofNullable(student.getDatumRodjenja()).isPresent()) {
                        sqlUpit.append(" AND DATUM_RODJENJA = '" + student.getDatumRodjenja().format( DateTimeFormatter.ISO_DATE) + "'"); }

                }
            }

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery(sqlUpit.toString());

            while (rs.next()){
                Student newStudent = getStudentFromResultSet(rs);
                listaStudenata.add(newStudent);
            }

        }
        catch (SQLException | IOException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return listaStudenata;
    }
    public static List<Predmet> dohvatiPredmet(Predmet predmet) throws BazaPodatakaException{

        List<Predmet> listaPredmeta = new ArrayList<>();

        try (Connection connection = spajanjeNaBazu()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PREDMET WHERE 1=1");

            if (Optional.ofNullable(predmet).isPresent()){

                if (Optional.ofNullable(predmet).map(Predmet::getId).isPresent()) {

                    sqlUpit.append(" AND ID = " + predmet.getId());

                    if (!Optional.ofNullable(predmet.getSifra()).map(String::isBlank).orElse(true)){
                        sqlUpit.append(" AND SIFRA LIKE '%" + predmet.getSifra() + "%'");
                    }
                    else if (!Optional.ofNullable(predmet.getNaziv()).map(String::isBlank).orElse(true)){
                        sqlUpit.append(" AND NAZIV LIKE '%" + predmet.getNaziv() + "%'");
                    }
                    else if (Optional.of(predmet).map(Predmet::getBrojEctsBodova).isPresent()){
                        sqlUpit.append(" AND BROJ_ECTS_BODOVA = " + predmet.getBrojEctsBodova());
                    }
                    else if (Optional.of(predmet).map(Predmet::getNositelj).map(Profesor::getId).isPresent()){
                        sqlUpit.append(" AND PROFESOR_ID = " + predmet.getNositelj().getId());
                    }
                }
            }

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery(sqlUpit.toString());

            while (rs.next()){
                Predmet newPredmet = getPredmetFromResultSet(rs);
                listaPredmeta.add(newPredmet);
            }

        }
        catch (SQLException | IOException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return listaPredmeta;
    }
    public static List<Ispit> dohvatiIspit(Ispit ispit) throws BazaPodatakaException{

        List<Ispit> listaIspita = new ArrayList<>();

        try (Connection connection = spajanjeNaBazu()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PREDMET WHERE 1=1");

            if (Optional.ofNullable(ispit).isPresent()){

                if (Optional.ofNullable(ispit).map(Ispit::getId).isPresent()) {

                    sqlUpit.append(" AND ID = " + ispit.getId());

                    if (Optional.of(ispit).map(Ispit::getPredmet).map(Predmet::getId).isPresent()){
                        sqlUpit.append(" AND PREDMET_ID = " + ispit.getPredmet().getId());
                    }

                    else if (Optional.of(ispit).map(Ispit::getStudent).map(Student::getId).isPresent()){
                        sqlUpit.append(" AND STUDENT_ID = " + ispit.getStudent().getId());
                    }
                    else if (Optional.of(ispit).map(Ispit::getOcjena).map(Ocjena::getOcjena).isPresent()){
                        sqlUpit.append(" AND OCJENA = " + ispit.getOcjena().getOcjena());
                    }
                    else if (Optional.ofNullable(ispit.getDatumIVrijeme()).isPresent()) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
                        sqlUpit.append(" AND DATUM_I_VRIJEME = '" + ispit.getDatumIVrijeme().format(formatter) + "'");
                    }
                }
            }

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery(sqlUpit.toString());

            while (rs.next()){
                Ispit newIspit = getIspitFromResultSet(rs);
                listaIspita.add(newIspit);
            }

        }
        catch (SQLException | IOException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return listaIspita;
    }

    public static void spremiNovogProfesora(Profesor profesor) throws BazaPodatakaException{

        try (Connection connection = spajanjeNaBazu()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO PROFESOR(IME,PREZIME,SIFRA,TITULA) VALUES(?,?,?,?)");

            preparedStatement.setString(1,profesor.getIme());
            preparedStatement.setString(2,profesor.getPrezime());
            preparedStatement.setString(3,profesor.getSifra());
            preparedStatement.setString(4,profesor.getTitula());


            preparedStatement.executeUpdate();

        }
        catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }
    }
    public static void spremiNovogStudenta(Student student) throws BazaPodatakaException{

        try (Connection connection = spajanjeNaBazu()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO PREDMET(IME,PREZIME,JMBAG,DATUM_RODJENJA ) VALUES(?,?,?,?)");

            preparedStatement.setString(1,student.getIme());
            preparedStatement.setString(2,student.getPrezime());
            preparedStatement.setString(3,student.getJmbag());
            preparedStatement.setDate(4, Date.valueOf(student.getDatumRodjenja()));
            preparedStatement.executeUpdate();

        }
        catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

    }
    public static void spremiNoviPredmet(Predmet predmet) throws BazaPodatakaException{

        try (Connection connection = spajanjeNaBazu()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO PREDMET(SIFRA,NAZIV,BROJ_ECTS_BODOVA,PROFESOR_ID) VALUES(?,?,?,?)");

            preparedStatement.setString(1,predmet.getSifra());
            preparedStatement.setString(2,predmet.getNaziv());
            preparedStatement.setLong(3,predmet.getBrojEctsBodova());
            preparedStatement.setLong(4,predmet.getNositelj().getId());

            preparedStatement.executeUpdate();

        }
        catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

    }
    public static void spremiNoviIspit(Ispit ispit) throws BazaPodatakaException{

        try (Connection connection = spajanjeNaBazu()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO ISPIT(PREDMET_ID,STUDENT_ID ,OCJENA ,DATUM_I_VRIJEME ) VALUES(?,?,?,?)");

            preparedStatement.setLong(1,ispit.getPredmet().getId());
            preparedStatement.setLong(2,ispit.getStudent().getId());
            preparedStatement.setInt(3,ispit.getOcjena().getOcjena());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(ispit.getDatumIVrijeme()));

            preparedStatement.executeUpdate();

        }
        catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

    }

    public static List<Profesor> sviProfesori() throws BazaPodatakaException{

        List<Profesor> profesori = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()){

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM PROFESOR");

            while (rs.next()){
                Profesor newProfesor = getProfesorFromResultSet(rs);
                profesori.add(newProfesor);
            }

        }catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return profesori;
    }
    public static List<Student> sviStudenti() throws BazaPodatakaException{

        List<Student> students = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()){

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM STUDENT");

            while (rs.next()){
                Student newStudent = getStudentFromResultSet(rs);
                students.add(newStudent);
            }

        }catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return students;
    }
    public static List<Predmet> sviPredmeti() throws BazaPodatakaException{

        List<Predmet> predmeti = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()){

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM PREDMET");

            while (rs.next()){
                Predmet newPredmet = getPredmetFromResultSet(rs);
                predmeti.add(newPredmet);
            }

        }catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

        return predmeti;
    }
    public static List<Ispit> sviIspiti() throws BazaPodatakaException{

        List<Ispit> ispiti = new ArrayList<>();

        try(Connection connection = spajanjeNaBazu()){

            Statement query = connection.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM ISPIT");

            while (rs.next()){
                Ispit newIspit = getIspitFromResultSet(rs);
                ispiti.add(newIspit);
            }

        }catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }
        return ispiti;
    }


    private static Profesor getProfesorFromResultSet(ResultSet profesorResultSet) throws SQLException {

        long id = profesorResultSet.getLong("ID");
        String sifra = profesorResultSet.getString("SIFRA");
        String ime = profesorResultSet.getString("IME");
        String prezime = profesorResultSet.getString("PREZIME");
        String titula = profesorResultSet.getString("TITULA");

        return new Profesor.Builder(id)
                .withSifra(sifra)
                .withIme(ime)
                .withPrezime(prezime)
                .withTitula(titula)
                .build();
    }
    private static Student getStudentFromResultSet(ResultSet studentsResultSet) throws SQLException {

        long id = studentsResultSet.getLong("ID");
        String ime = studentsResultSet.getString("IME");
        String prezime = studentsResultSet.getString("PREZIME");
        String jmbag = studentsResultSet.getString("JMBAG");
        //LocalDate dateOfBirth = studentsResultSet.getDate("DATUM_RODJENJA").toLocalDate();
        LocalDate datumRodjenja = studentsResultSet.getTimestamp("datum_rodjenja").toInstant().atZone( ZoneId.systemDefault()).toLocalDate();

        return new Student(id, ime, prezime, jmbag, datumRodjenja);
    }
    private static Predmet getPredmetFromResultSet(ResultSet profesorResultSet) throws BazaPodatakaException {

        try {
            List<Profesor> listaProfesora = sviProfesori();
            long id = profesorResultSet.getLong("ID");
            String sifra = profesorResultSet.getString("SIFRA");
            String naziv = profesorResultSet.getString("NAZIV");
            Integer brojECTSbodova = profesorResultSet.getInt("BROJ_ECTS_BODOVA");
            long profesorID = profesorResultSet.getLong("PROFESOR_ID");

            Profesor nositelj = getEntity(listaProfesora,profesorID);

            return new Predmet(id, sifra, naziv, brojECTSbodova, nositelj);
        }
        catch (SQLException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }

    }
    private static Ispit getIspitFromResultSet(ResultSet ispitResultSet) throws BazaPodatakaException{

        try{
            List<Predmet> listaPredmeta = sviPredmeti();
            List<Student> listaStudenata = sviStudenti();

            long id = ispitResultSet.getLong("ID");
            long predmetId = ispitResultSet.getLong("PREDMET_ID ");
            long studentId = ispitResultSet.getLong("STUDENT_ID  ");
            Integer ocjena =ispitResultSet.getInt("OCJENA");
            LocalDateTime datumIVrijemeIspita = ispitResultSet.getTimestamp("DATUM_I_VRIJEME").toLocalDateTime();

            Predmet predmet = getEntity(listaPredmeta,predmetId);
            Student student = getEntity(listaStudenata,studentId);

            return new Ispit(id,predmet,student, Ocjena.ocjenaUEnum(ocjena),datumIVrijemeIspita);
        }
        catch (SQLException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }
    }

    private static <T extends Entitet> T getEntity(List<T> entities, long id) {
        return entities.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Entity with id %d wasn't found.", id)));
    }
    private static <T extends Entitet> List<T> getEntities(List<T> entities, List<Long> ids) {

        var values = new ArrayList<T>();
        for (var id : ids) {
            entities.stream().filter(e -> e.getId() == id).findFirst().ifPresent(values::add);
        }
        return values;
    }

    public static void errorMessage(String message) {
        var alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }

}
