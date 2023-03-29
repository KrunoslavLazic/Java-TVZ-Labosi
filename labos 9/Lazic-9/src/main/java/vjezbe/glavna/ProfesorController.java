package vjezbe.glavna;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vjezbe.baza.BazaPodataka;
import vjezbe.entitet.Profesor;
import vjezbe.iznimke.BazaPodatakaException;

import java.util.List;

public class ProfesorController {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    public List<Profesor> listaProfesora;

    @FXML
    private TextField id;

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private TextField sifra;

    @FXML
    private TextField titula;

    @FXML
    private TableView<Profesor> profesorTableView;

    @FXML
    private TableColumn<Profesor, String> idTableColumn;

    @FXML
    private TableColumn<Profesor, String> imeTableColumn;

    @FXML
    private TableColumn<Profesor, String> prezimeTableColumn;

    @FXML
    private TableColumn<Profesor, String> sifraTableColumn;

    @FXML
    private TableColumn<Profesor, String> titulaTableColumn;

    @FXML
    public void initialize(){

        try {
            listaProfesora = BazaPodataka.sviProfesori();
        }catch (BazaPodatakaException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            BazaPodataka.errorMessage(ex.getMessage());
        }

        idTableColumn.setCellValueFactory(c->new SimpleStringProperty(Long.toString(c.getValue().getId())));
        imeTableColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getIme()));
        prezimeTableColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getPrezime()));
        sifraTableColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getSifra()));
        titulaTableColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getTitula()));

        ObservableList<Profesor> observableListProfesor = FXCollections.observableList(listaProfesora);
        profesorTableView.setItems(observableListProfesor);
    }

    @FXML
    protected void onSearchButtonClick() {

        List<Profesor> filtriraniProfesori = listaProfesora.stream()
                .filter(p->Long.toString(p.getId()).contains(id.getText()))
                .filter(p -> p.getSifra().contains(sifra.getText()))
                .filter(p -> p.getIme().contains(ime.getText()))
                .filter(p -> p.getPrezime().contains(prezime.getText()))
                .filter(p -> p.getTitula().contains(titula.getText()))
                .toList();

        profesorTableView.setItems(FXCollections.observableList(filtriraniProfesori));
    }




}
