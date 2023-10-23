package com.example.codegeneratorfx;

import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeGeneratorFX extends Application {
    Lottery lottery = new Lottery();
    @Override
    public void start(Stage stage) throws IOException {
        CodeGeneratorFXController codeGeneratorFXController = new CodeGeneratorFXController(lottery);
        FXMLLoader fxmlLoader = new FXMLLoader(CodeGeneratorFX.class.getResource("start-scene.fxml"));
        fxmlLoader.setController(codeGeneratorFXController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome in Code Generator!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}