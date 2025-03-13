module com.aviato {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens com.aviato to javafx.fxml;
    opens com.aviato.Controllers to javafx.fxml, javafx.base; // Add javafx.base here
    exports com.aviato;
}