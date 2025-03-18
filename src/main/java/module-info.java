module com.example.ibank_uml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.ibank_uml to javafx.fxml;
    exports com.example.ibank_uml;
    exports com.example.ibank_uml.controllers;
    opens com.example.ibank_uml.controllers to javafx.fxml;
    exports com.example.ibank_uml.models;
    opens com.example.ibank_uml.models to javafx.fxml;
    exports com.example.ibank_uml.utils;
    opens com.example.ibank_uml.utils to javafx.fxml;
}
