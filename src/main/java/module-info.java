module com.example.test1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.test1 to javafx.fxml;
    exports com.example.test1;
    exports com.example.test1.Model;
    opens com.example.test1.Model to javafx.fxml;
}