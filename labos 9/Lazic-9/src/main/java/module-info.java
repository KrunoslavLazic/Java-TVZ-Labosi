module vjezbe.glavna.lazic9 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens vjezbe.glavna to javafx.fxml;
    exports vjezbe.glavna;
}