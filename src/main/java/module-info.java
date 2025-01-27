module com.example.sistemadegerenciamentodeprodutos {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.slf4j;
    requires org.xerial.sqlitejdbc;



    opens com.example.sistemadegerenciamentodeprodutos to javafx.fxml;
    exports com.example.sistemadegerenciamentodeprodutos;
}