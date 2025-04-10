module com.aviato {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    // Database (Hibernate/JPA)
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires kernel;
    requires layout;
    requires com.oracle.database.jdbc;


    // Open packages for reflection (JavaFX FXML + Hibernate)
    opens com.aviato to javafx.fxml, org.hibernate.orm.core;
    opens com.aviato.Controllers to javafx.fxml, javafx.base, org.hibernate.orm.core;
    opens com.aviato.Types to org.hibernate.orm.core, javafx.base;

    // Export main package
    exports com.aviato;
}