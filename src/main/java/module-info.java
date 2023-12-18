module com.example.codegeneratorfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codegeneratorfx.supportClasses to javafx.base;
    exports com.example.codegeneratorfx;
    opens com.example.codegeneratorfx to javafx.base, javafx.fxml;
    exports com.example.codegeneratorfx.controllers;
    opens com.example.codegeneratorfx.controllers to javafx.base, javafx.fxml;
}