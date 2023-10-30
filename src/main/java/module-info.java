module com.example.codegeneratorfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codegeneratorfx.supportClasses to javafx.base;
    exports com.example.codegeneratorfx;
    opens com.example.codegeneratorfx to javafx.base, javafx.fxml;
}