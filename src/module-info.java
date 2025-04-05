module com.aviato {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;

    opens com.aviato to javafx.fxml, org.hibernate.orm.core;
    opens com.aviato.Controllers to javafx.fxml, javafx.base, org.hibernate.orm.core;
    opens com.aviato.Types to org.hibernate.orm.core, javafx.base;
    exports com.aviato;
}