package com.example.codegeneratorfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeGeneratorFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodeGeneratorFX.class.getResource("start-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome in Code Generator!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}