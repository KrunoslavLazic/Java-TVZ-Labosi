package vjezbe.glavna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vjezbe.baza.BazaPodataka;
import vjezbe.entitet.Entitet;
import vjezbe.entitet.Profesor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import vjezbe.iznimke.BazaPodatakaException;
import java.util.List;
import java.util.OptionalLong;

public class UpdateProfesoraController {

    private List<Profesor> listaUnesenihProfesora;
    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @FXML
    private TextField id;
    @FXML
    private TextField sifra;

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private TextField titula;

    @FXML
    public void saveProfesor() {
        StringBuilder errorMessages = new StringBuilder();

        String enteredSifra = sifra.getText();
        String enteredIme = ime.getText();
        String enteredPrezime = prezime.getText();
        String enteredTitula = titula.getText();


        if(enteredSifra.isEmpty()){
            errorMessages.append("Sifra je obavezan podatak!\n");
        }
        if(enteredIme.isEmpty()){
            errorMessages.append("Ime je obavezan podatak!\n");
        }
        if(enteredPrezime.isEmpty()){
            errorMessages.append("Prezime je obavezan podatak!\n");
        }
        if(enteredTitula.isEmpty()){
            errorMessages.append("Titula je obavezan podatak!\n");
        }

        try {
            listaUnesenihProfesora = BazaPodataka.sviProfesori();
        }catch (BazaPodatakaException ex){
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            logger.error(poruka);
            BazaPodataka.errorMessage(ex.getMessage());
        }

        if(errorMessages.isEmpty()){

            OptionalLong maksimalniId = listaUnesenihProfesora.stream()
                    .mapToLong(Entitet::getId).max();

            Profesor profesor = new Profesor(maksimalniId.getAsLong()+1,enteredSifra,enteredIme,enteredPrezime,enteredTitula);


            try {
                BazaPodataka.spremiNovogProfesora(profesor);
            }catch (BazaPodatakaException ex){
                String poruka = "Došlo je do pogreške u radu s bazom podataka";
                logger.error(poruka);
                BazaPodataka.errorMessage(ex.getMessage());
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjesno spremanje podataka");
            alert.setHeaderText("Spremanje podataka o novom profesoru");
            alert.setContentText("Podaci o novom profesoru su ospješno dodani");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogresan unos podataka");
            alert.setHeaderText("Molimo ispravite sljedeće pogreške:");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }
}

