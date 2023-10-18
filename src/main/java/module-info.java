module com.example.codegeneratorfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codegeneratorfx to javafx.fxml;
    opens com.example.codegeneratorfx.supportClasses to javafx.base;
    exports com.example.codegeneratorfx;
}