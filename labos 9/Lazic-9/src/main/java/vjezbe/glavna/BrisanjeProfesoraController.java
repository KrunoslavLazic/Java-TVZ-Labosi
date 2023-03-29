package vjezbe.glavna;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vjezbe.baza.BazaPodataka;
import vjezbe.entitet.Profesor;
import vjezbe.iznimke.BazaPodatakaException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BrisanjeProfesoraController {

    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    private List<Profesor> listaProfesora;
    private Long idOdabranogProfesora;
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

        try{
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
    public void onObrisiButtonClick(){

        profesorTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !profesorTableView.getSelectionModel().isEmpty()) {
                Profesor selectedRow = profesorTableView.getSelectionModel().getSelectedItem();
                idOdabranogProfesora=selectedRow.getId();
                System.out.println(idOdabranogProfesora);
            }
        });

        try {
            obrisiProfesora(idOdabranogProfesora);
        }catch (BazaPodatakaException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            BazaPodataka.errorMessage(ex.getMessage());
        }
    }

    @FXML public void onRefreshButtonClick(){

        try{
            listaProfesora = BazaPodataka.sviProfesori();
        }catch (BazaPodatakaException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            BazaPodataka.errorMessage(ex.getMessage());
        }

        ObservableList<Profesor> observableListProfesor = FXCollections.observableList(listaProfesora);
        profesorTableView.setItems(observableListProfesor);

    }

    public static void obrisiProfesora(Long id) throws BazaPodatakaException{

        try (Connection connection = BazaPodataka.spajanjeNaBazu()){

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PROFESOR WHERE ID = ?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException | IOException ex){
            String poruka ="Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            throw new BazaPodatakaException(poruka,ex);
        }
    }


}
