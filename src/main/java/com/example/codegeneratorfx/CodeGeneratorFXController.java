package com.example.codegeneratorfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeGeneratorFXController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void onButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("administration-panel.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Let's generate some codes!");
        stage.setScene(scene);
        stage.show();
    }
}