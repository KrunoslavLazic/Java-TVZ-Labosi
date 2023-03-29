package vjezbe.glavna;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vjezbe.baza.BazaPodataka;
import vjezbe.entitet.Profesor;
import vjezbe.entitet.Student;
import vjezbe.iznimke.BazaPodatakaException;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    public List<Student> listaStudenata;

    @FXML
    private TextField id;

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private TextField jmbag;

    @FXML
    private DatePicker datum;

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student,String> idTableColumn;

    @FXML
    private TableColumn<Student,String> imeTableColumn;

    @FXML
    private TableColumn<Student,String> prezimeTableColumn;

    @FXML
    private TableColumn<Student,String> jmbagTableColumn;

    @FXML
    private TableColumn<Student,String> datumTableColumn;

    public void initialize() {

        try {
            listaStudenata = BazaPodataka.sviStudenti();
        } catch (BazaPodatakaException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            BazaPodataka.errorMessage(ex.getMessage());
        }

        idTableColumn.setCellValueFactory(c -> new SimpleStringProperty(Long.toString(c.getValue().getId())));
        imeTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIme()));
        prezimeTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrezime()));
        jmbagTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getJmbag()));
        datumTableColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDatumRodjenja().format(DateTimeFormatter.ofPattern("d.M.yyyy."))));

        ObservableList<Student> observableListStudent = FXCollections.observableList(listaStudenata);
        studentTableView.setItems(observableListStudent);
    }

        @FXML
        protected void onSearchButtonClick() {

            List<Student> filtriraniStudenti = listaStudenata.stream().filter(s -> s.getJmbag().contains(jmbag.getText()))
                    .filter(p->Long.toString(p.getId()).contains(id.getText()))
                    .filter(s -> s.getIme().contains(ime.getText()))
                    .filter(s -> s.getPrezime().contains(prezime.getText()))
                    .filter(s -> datum.getValue() == null || s.getDatumRodjenja().equals(datum.getValue()))
                    .toList();

            studentTableView.setItems(FXCollections.observableList(filtriraniStudenti));
        }



}
