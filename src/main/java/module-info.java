module com.example.codegeneratorfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.codegeneratorfx to javafx.fxml;
    exports com.example.codegeneratorfx;
}