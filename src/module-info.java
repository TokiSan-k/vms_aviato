module com.aviato {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.aviato.Controllers to javafx.fxml;
    exports com.aviato;
}