package vjezbe.glavna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class MenuController {
    @FXML
    public void showProfesorSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profesor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    @FXML
    public void showProfesorBrisanjeScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("brisanjeProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML public void showProfesorUpdateScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    @FXML
    public void showStudentSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studenti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    @FXML
    public void showPredmetSearchScreen()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("predmeti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    @FXML
    public void showIspitSearchScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ispiti.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void showProfesorUnosScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosProfesora.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void showStudentUnosScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void showPredmetUnosScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosPredmeta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void showIspitUnosScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosIspita.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 400);
        HelloApplication.getMainStage().setTitle("Hello!");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
}

